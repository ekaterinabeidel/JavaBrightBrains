package bookstore.javabrightbrains.utils;

import bookstore.javabrightbrains.dto.auth.RegisterRequestDto;
import bookstore.javabrightbrains.dto.auth.RegisterResponseDto;
import bookstore.javabrightbrains.dto.book.BookOrderShortResponseDto;
import bookstore.javabrightbrains.dto.book.BookShortResponseDto;
import bookstore.javabrightbrains.dto.cart.CartItemResponseDto;
import bookstore.javabrightbrains.dto.cart.CartItemRequestDto;
import bookstore.javabrightbrains.dto.cart.CartResponseDto;
import bookstore.javabrightbrains.dto.order.OrderItemDto;
import bookstore.javabrightbrains.dto.order.OrderResponseDto;
import bookstore.javabrightbrains.dto.order.OrderShortResponseDto;
import bookstore.javabrightbrains.dto.user.UserDto;
import bookstore.javabrightbrains.dto.order.PurchaseHistoryDto;
import bookstore.javabrightbrains.entity.*;
import bookstore.javabrightbrains.enums.Role;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.exception.NotEnoughBooksInStockException;
import bookstore.javabrightbrains.repository.BookRepository;
import bookstore.javabrightbrains.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MappingUtils {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public static User convertRegisterRequestDtoToEntity(RegisterRequestDto registerRequestDto) {
        User user = new User();
        user.setName(registerRequestDto.getName());
        user.setSurname(registerRequestDto.getSurname());
        user.setEmail(registerRequestDto.getEmail());
        user.setPassword(registerRequestDto.getPassword());
        user.setRole(Role.USER);
        return user;
    }

    public static RegisterResponseDto convertEntityUserToRegisterResponseDto(User user) {
        return new RegisterResponseDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
    }

    public UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setSurname(user.getSurname());
        userDto.setName(user.getName());
        userDto.setPhone(user.getPhone());
        return userDto;
    }

    public void updateUserEntityFromUserDto(UserDto userDto, User user) {
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getSurname() != null) {
            user.setSurname(userDto.getSurname());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getPhone() != null) {
            user.setPhone(userDto.getPhone());
        }

        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    }

    public CartItem toCartItem(CartItemRequestDto cartItemRequestDto, Cart cart, Book book) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setBook(book);
        cartItem.setQuantity(cartItemRequestDto.getQuantity());
        cartItem.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        cartItem.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return cartItem;
    }

    public CartResponseDto toCartResponseDto(Cart cart, List<CartItem> cartItems) {
        List<CartItemResponseDto> cartItemDtos = cartItems.stream()
                .map(this::toCartItemResponseDto)
                .collect(Collectors.toList());

        return new CartResponseDto(cart.getId(), cartItemDtos);
    }

    public CartItemResponseDto toCartItemResponseDto(CartItem cartItem) {

        BookShortResponseDto bookShortResponseDto = new BookShortResponseDto(
                cartItem.getBook().getId(),
                cartItem.getBook().getTitle(),
                cartItem.getBook().getAuthor(),
                cartItem.getBook().getPrice(),
                cartItem.getBook().getDiscount(),
                cartItem.getBook().getCategory().getId(),
                cartItem.getBook().getTotalStock(),
                cartItem.getBook().getImageLink(),
                cartItem.getBook().getPrice()
                        .subtract(cartItem.getBook().getPrice()
                                .multiply(BigDecimal.valueOf(cartItem.getBook().getDiscount() / 100.0)))

        );

        return new CartItemResponseDto(
                cartItem.getId(),
                bookShortResponseDto,
                cartItem.getQuantity()
        );
    }

    public List<OrderItemDto> convertCartItemsToOrderItems(List<CartItem> cartItems) {
        List<OrderItemDto> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            Book book = cartItem.getBook();
            int quantity = cartItem.getQuantity();

            if (book.getTotalStock() < quantity) {
                throw new NotEnoughBooksInStockException(MessagesException.NOT_ENOUGH_BOOKS_IN_STOCK);
            }

            book.setTotalStock(book.getTotalStock() - quantity);
            bookRepository.save(book);

            double priceAtPurchase = book.getPrice().doubleValue();
            orderItems.add(new OrderItemDto(
                    cartItem.getId(),
                    new BookOrderShortResponseDto(book.getId(), book.getTitle(), book.getAuthor()),
                    quantity,
                    priceAtPurchase
            ));
        }

        return orderItems;
    }

    public List<OrderItemDto> mapToOrderItemsDto(List<OrderItem> orderItems) {

        return orderItems.stream()
                .map(orderItem -> new OrderItemDto(
                        orderItem.getId(),
                        new BookOrderShortResponseDto(
                                orderItem.getBook().getId(),
                                orderItem.getBook().getTitle(),
                                orderItem.getBook().getAuthor()
                        ),
                        orderItem.getQuantity(),
                        orderItem.getPriceAtPurchase()
                ))
                .toList();
    }

    public OrderResponseDto mapToOrderResponseDto(Order order) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
        List<OrderItemDto> orderItemsDto = mapToOrderItemsDto(orderItems);

        BigDecimal totalPrice = calculateTotalPrice(orderItemsDto);

        return new OrderResponseDto(
                order.getId(),
                orderItemsDto,
                order.getCreatedAt(),
                order.getDeliveryAddress(),
                order.getContactPhone(),
                order.getDeliveryMethod(),
                order.getStatus(),
                totalPrice
        );
    }

    public OrderShortResponseDto mapToOrderShortResponseDto(Order order) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
        List<OrderItemDto> orderItemsDto = mapToOrderItemsDto(orderItems);

        BigDecimal totalPrice = calculateTotalPrice(orderItemsDto);

        return new OrderShortResponseDto(
                order.getId(),
                orderItemsDto,
                order.getCreatedAt(),
                order.getStatus(),
                totalPrice
        );
    }

    public BigDecimal calculateTotalPrice(List<OrderItemDto> orderItemsDto) {

        return orderItemsDto.stream()
                .map(item -> BigDecimal.valueOf(item.getPriceAtPurchase()).multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public PurchaseHistoryDto toPurchaseHistoryDto(OrderItem orderItem) {
        PurchaseHistoryDto dto = new PurchaseHistoryDto();
        dto.setTitle(orderItem.getBook().getTitle());
        dto.setCreatedAt(orderItem.getOrder().getCreatedAt());
        dto.setImageLink(orderItem.getBook().getImageLink());
        dto.setPrice(orderItem.getPriceAtPurchase());
        dto.setOrderId(orderItem.getOrder().getId());
        return dto;
    }

}

