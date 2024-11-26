package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.order.*;
import bookstore.javabrightbrains.entity.*;
import bookstore.javabrightbrains.exception.*;
import bookstore.javabrightbrains.repository.*;
import bookstore.javabrightbrains.utils.MappingUtils;
import bookstore.javabrightbrains.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Validated
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MappingUtils mappingUtils;

    @Autowired
    private JwtSecurityService jwtSecurityService;


    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {

        List<CartItem> cartItems = getValidatedCartItems(orderRequestDto.getCartId());

        List<OrderItemDto> orderItems = mappingUtils.convertCartItemsToOrderItems(cartItems);
        BigDecimal totalPrice = mappingUtils.calculateTotalPrice(orderItems);

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
        return mappingUtils.mapToOrderResponseDto(order);
    }

    public List<OrderShortResponseDto> getOrdersByUserId(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new IdNotFoundException(MessagesException.USER_NOT_FOUND);
        }
        jwtSecurityService.validateUserAccess(userId);
        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);

        return orders.stream().map(order -> mappingUtils.mapToOrderShortResponseDto(order)).toList();
    }

    public OrderShortResponseDto cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IdNotFoundException(MessagesException.ORDER_NOT_FOUND));
        if (!"Pending".equalsIgnoreCase(order.getStatus()) && !"Created".equalsIgnoreCase(order.getStatus())) {
            throw new OrderCancellationNotAllowedException(MessagesException.ORDER_CANNOT_BE_CANCELED_INVALID_STATUS);
        }

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);

        return mappingUtils.mapToOrderShortResponseDto(order);
    }

    private List<CartItem> getValidatedCartItems(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IdNotFoundException(MessagesException.CART_ITEM_NOT_FOUND));

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
        order.setStatus("Processing");
        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        return orderRepository.save(order);
    }

    public List<PurchaseHistoryDto> getPurchaseHistory(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IdNotFoundException(MessagesException.USER_NOT_FOUND);
        }
        jwtSecurityService.validateUserAccess(userId);
        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
        if (orders.isEmpty()) {
            throw new IdNotFoundException(MessagesException.ORDER_NOT_FOUND);
        }

        List<OrderItem> allOrderItems = orders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .toList();

        return allOrderItems.stream()
                .map(mappingUtils::toPurchaseHistoryDto)
                .toList();
    }

}
