package bookstore.javabrightbrains.utils;

import bookstore.javabrightbrains.dto.auth.RegisterRequestDto;
import bookstore.javabrightbrains.dto.auth.RegisterResponseDto;
import bookstore.javabrightbrains.dto.book.BookOrderShortResponseDto;
import bookstore.javabrightbrains.dto.book.BookRequestDto;
import bookstore.javabrightbrains.dto.book.BookResponseDto;
import bookstore.javabrightbrains.dto.book.BookShortResponseDto;
import bookstore.javabrightbrains.dto.cart.CartItemResponseDto;
import bookstore.javabrightbrains.dto.cart.CartItemRequestDto;
import bookstore.javabrightbrains.dto.cart.CartResponseDto;
import bookstore.javabrightbrains.dto.category.CategoryDto;
import bookstore.javabrightbrains.dto.order.OrderItemDto;
import bookstore.javabrightbrains.dto.order.OrderResponseDto;
import bookstore.javabrightbrains.dto.order.OrderShortResponseDto;
import bookstore.javabrightbrains.dto.user.UserDto;
import bookstore.javabrightbrains.dto.order.PurchaseHistoryDto;
import bookstore.javabrightbrains.entity.*;
import bookstore.javabrightbrains.enums.Role;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class MappingUtils {

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

    public static UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setSurname(user.getSurname());
        userDto.setName(user.getName());
        userDto.setPhone(user.getPhone());
        return userDto;
    }

    public static void updateUserEntityFromUserDto(UserDto userDto, User user) {
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
    public static CategoryDto convertCategoryEntityToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public static CartItem toCartItem(CartItemRequestDto cartItemRequestDto, Cart cart, Book book) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setBook(book);
        cartItem.setQuantity(cartItemRequestDto.getQuantity());
        cartItem.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        cartItem.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return cartItem;
    }

    public static CartResponseDto toCartResponseDto(Cart cart, List<CartItem> cartItems) {
        List<CartItemResponseDto> cartItemsDto = cartItems.stream()
                .map(MappingUtils::toCartItemResponseDto)
                .collect(Collectors.toList());

        return new CartResponseDto(cart.getId(), cartItemsDto);
    }

    public static CartItemResponseDto toCartItemResponseDto(CartItem cartItem) {

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



    public static List<OrderItemDto> mapToOrderItemsDto(List<OrderItem> orderItems) {

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

    public static OrderResponseDto mapToOrderResponseDto(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
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

    public static OrderShortResponseDto mapToOrderShortResponseDto(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
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

    public static BigDecimal calculateTotalPrice(List<OrderItemDto> orderItemsDto) {

        return orderItemsDto.stream()
                .map(item -> BigDecimal.valueOf(item.getPriceAtPurchase())
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static PurchaseHistoryDto toPurchaseHistoryDto(OrderItem orderItem) {
        PurchaseHistoryDto dto = new PurchaseHistoryDto();
        dto.setTitle(orderItem.getBook().getTitle());
        dto.setCreatedAt(orderItem.getOrder().getCreatedAt());
        dto.setImageLink(orderItem.getBook().getImageLink());
        dto.setPrice(orderItem.getPriceAtPurchase());
        dto.setOrderId(orderItem.getOrder().getId());
        return dto;
    }

    public static Book convertToBookEntity(BookRequestDto bookRequestDto, Category category) {
        Book book = new Book();
        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setDescription(bookRequestDto.getDescription());
        book.setPrice(bookRequestDto.getPrice());
        book.setDiscount(bookRequestDto.getDiscount());

        if (bookRequestDto.getDiscount() == 0) {
            book.setPriceDiscount(bookRequestDto.getPrice());
        } else {
            book.setPriceDiscount(getPriceWithDiscount(bookRequestDto.getPrice(), bookRequestDto.getDiscount()));
        }

        book.setTotalStock(bookRequestDto.getTotalStock());
        book.setImageLink(bookRequestDto.getImageLink());

        Long categoryId = bookRequestDto.getCategoryId();
        if (categoryId != null) {

            book.setCategory(category);
        }
        return book;
    }

    public static BookRequestDto convertToBookRequestDto(Book book) {
        return new BookRequestDto(
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                book.getPrice(),
                book.getDiscount(),
                book.getCategory().getId(),
                book.getTotalStock(),
                book.getImageLink()
        );
    }


    public static BigDecimal getPriceWithDiscount(BigDecimal price, int discount) {
        BigDecimal discountAmount = price
                .multiply(BigDecimal.valueOf(discount))
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
        return price.subtract(discountAmount);
    }

    public static BookResponseDto convertToBookResponseDto(Book book) {
        BookResponseDto bookDto = new BookResponseDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setDescription(book.getDescription());
        bookDto.setPrice(book.getPrice());
        bookDto.setDiscount(book.getDiscount());
        bookDto.setTotalStock(book.getTotalStock());
        bookDto.setImageLink(book.getImageLink());
        bookDto.setPriceDiscount(book.getPriceDiscount());

        Category category = book.getCategory();

        if (category != null) {
            bookDto.setCategoryId(category.getId());
        }

        return bookDto;
    }

    public static BookShortResponseDto convertToBookShortResponseDto(Book book) {
        BookShortResponseDto bookDto = new BookShortResponseDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(bookDto.getAuthor());
        bookDto.setPrice(book.getPrice());
        bookDto.setDiscount(book.getDiscount());
        bookDto.setTotalStock(book.getTotalStock());
        bookDto.setImageLink(book.getImageLink());
        bookDto.setPriceDiscount(book.getPriceDiscount());

        Category category = book.getCategory();
        if (category != null) {
            bookDto.setCategoryId(category.getId());
        }

        return bookDto;
    }

    public static Book updateBookFromDto(Book book, BookRequestDto bookDto, Category category) {
        if (bookDto.getTitle() != null) book.setTitle(bookDto.getTitle());
        if (bookDto.getAuthor() != null) book.setAuthor(bookDto.getAuthor());
        if (bookDto.getDescription() != null) book.setDescription(bookDto.getDescription());
        if (bookDto.getPrice() != null) book.setPrice(bookDto.getPrice());

        book.setDiscount(bookDto.getDiscount());
        if (bookDto.getDiscount() > 0) {
            book.setPriceDiscount(getPriceWithDiscount(bookDto.getPrice(), bookDto.getDiscount()));
        } else {
            book.setPriceDiscount(bookDto.getPrice());
        }

        if (bookDto.getTotalStock() != 0) book.setTotalStock(bookDto.getTotalStock());
        if (bookDto.getImageLink() != null) book.setImageLink(bookDto.getImageLink());

        Long categoryId = bookDto.getCategoryId();
        if (categoryId != null) {
            book.setCategory(category);
        }
        return book;
    }

}