package bookstore.javabrightbrains.service.impl;

import bookstore.javabrightbrains.dto.book.BookOrderShortResponseDto;
import bookstore.javabrightbrains.dto.order.*;
import bookstore.javabrightbrains.entity.*;
import bookstore.javabrightbrains.enums.OrderStatus;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.exception.NotEnoughBooksInStockException;
import bookstore.javabrightbrains.exception.OrderCancellationNotAllowedException;
import bookstore.javabrightbrains.repository.OrderRepository;
import bookstore.javabrightbrains.service.*;
import bookstore.javabrightbrains.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private AppUserService userService;

    @Autowired
    private JwtSecurityService jwtSecurityService;

    @Autowired
    private BookService bookService;


    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {

        List<CartItem> cartItems = getValidatedCartItems(orderRequestDto.getCartId());

        List<OrderItemDto> orderItems = convertCartItemsToOrderItems(cartItems);
        BigDecimal totalPrice = MappingUtils.calculateTotalPrice(orderItems);

        Order order = createAndSaveOrder(orderRequestDto);

        cartService.deleteCartById(orderRequestDto.getCartId());

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

        return MappingUtils.mapToOrderResponseDto(order);
    }

    public List<OrderShortResponseDto> getOrdersByUserId(Long userId) {

        userService.getUserById(userId);
        jwtSecurityService.validateUserAccess(userId);
        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);

        return orders.stream().map(MappingUtils::mapToOrderShortResponseDto).toList();
    }

    public OrderShortResponseDto cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IdNotFoundException(MessagesException.ORDER_NOT_FOUND));
        if (!"Pending".equalsIgnoreCase(order.getStatus().getStatus()) &&
                !"Created".equalsIgnoreCase(order.getStatus().getStatus())) {
            throw new OrderCancellationNotAllowedException(MessagesException.ORDER_CANNOT_BE_CANCELED_INVALID_STATUS);
        }

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);

        return MappingUtils.mapToOrderShortResponseDto(order);
    }

    private List<CartItem> getValidatedCartItems(Long cartId) {
        Cart cart = cartService.getCartById(cartId);

        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new IdNotFoundException(MessagesException.CART_ITEM_NOT_FOUND);
        }
        return cartItems;
    }

    private Order createAndSaveOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setDeliveryAddress(orderRequestDto.getDeliveryAddress());
        order.setContactPhone(orderRequestDto.getContactPhone());
        order.setDeliveryMethod(orderRequestDto.getDeliveryMethod());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        return orderRepository.save(order);
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
            bookService.update(book.getId(), MappingUtils.convertToBookRequestDto(book));


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

    public List<PurchaseHistoryDto> getPurchaseHistory(Long userId) {
        userService.getUserById(userId);
        jwtSecurityService.validateUserAccess(userId);
        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
        if (orders.isEmpty()) {
            throw new IdNotFoundException(MessagesException.ORDER_NOT_FOUND);
        }

        List<OrderItem> allOrderItems = orders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .toList();

        return allOrderItems.stream()
                .map(MappingUtils::toPurchaseHistoryDto)
                .toList();
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public void saveAllOrders(List<Order> orders) {
        orderRepository.saveAll(orders);
    }
}
