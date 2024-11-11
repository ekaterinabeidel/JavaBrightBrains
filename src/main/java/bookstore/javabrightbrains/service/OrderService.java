package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.book.BookOrderShortResponseDto;
import bookstore.javabrightbrains.dto.order.OrderItemDto;
import bookstore.javabrightbrains.dto.order.OrderRequestDto;
import bookstore.javabrightbrains.dto.order.OrderResponseDto;
import bookstore.javabrightbrains.dto.order.OrderShortResponseDto;
import bookstore.javabrightbrains.entity.*;
import bookstore.javabrightbrains.exception.*;
import bookstore.javabrightbrains.repository.*;
import bookstore.javabrightbrains.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Validated
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;


    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {

        List<CartItem> cartItems = getValidatedCartItems(orderRequestDto.getCartId());

        List<OrderItemDto> orderItems = convertCartItemsToOrderItems(cartItems);
        BigDecimal totalPrice = calculateTotalPrice(orderItems);

        Order order = createAndSaveOrder(orderRequestDto);

        cartRepository.deleteById(orderRequestDto.getCartId());

        return new OrderResponseDto(
                order.getId(),
                orderItems,
                order.getCreatedAt(),
                order.getDeliveryAddress(),
                order.getContactPhone(),
                order.getDeliveryMethod(),
                order.getStatus(),
                totalPrice
        );
    }

    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(MessagesException.ORDER_NOT_FOUND));

        return mapToOrderResponseDto(order);
    }

    public List<OrderShortResponseDto> getOrdersByUserId(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new IdNotFoundException(MessagesException.USER_NOT_FOUND);
        }

        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);

        return orders.stream().map(this::mapToOrderShortResponseDto).toList();
    }


    public OrderShortResponseDto cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IdNotFoundException(MessagesException.ORDER_NOT_FOUND));
        if (!"Pending".equalsIgnoreCase(order.getStatus()) && !"Created".equalsIgnoreCase(order.getStatus())) {
            throw new OrderCancellationNotAllowedException(MessagesException.ORDER_CANNOT_BE_CANCELLED_INVALID_STATUS);
        }

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);

        return mapToOrderShortResponseDto(order);
    }

    private List<CartItem> getValidatedCartItems(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IdNotFoundException(MessagesException.CART_ITEM_NOT_FOUND));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        if (cartItems.isEmpty()) {
            throw new IdNotFoundException(MessagesException.CART_ITEM_NOT_FOUND);
        }
        return cartItems;
    }

    private List<OrderItemDto> convertCartItemsToOrderItems(List<CartItem> cartItems) {
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

    private BigDecimal calculateTotalPrice(List<OrderItemDto> orderItemsDto) {

        return orderItemsDto.stream()
                .map(item -> BigDecimal.valueOf(item.getPriceAtPurchase()).multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Order createAndSaveOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setDeliveryAddress(orderRequestDto.getDeliveryAddress());
        order.setContactPhone(orderRequestDto.getContactPhone());
        order.setDeliveryMethod(orderRequestDto.getDeliveryMethod());
        order.setStatus("Processing");
        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        return orderRepository.save(order);
    }

    private OrderResponseDto mapToOrderResponseDto(Order order) {
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
    private OrderShortResponseDto mapToOrderShortResponseDto(Order order) {
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

    private List<OrderItemDto> mapToOrderItemsDto(List<OrderItem> orderItems) {

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
}
