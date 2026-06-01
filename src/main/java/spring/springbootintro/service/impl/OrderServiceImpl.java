package spring.springbootintro.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.springbootintro.dto.CreateOrderRequestDto;
import spring.springbootintro.dto.OrderDto;
import spring.springbootintro.dto.OrderItemDto;
import spring.springbootintro.dto.UpdateOrderStatusRequestDto;
import spring.springbootintro.exception.EntityNotFoundException;
import spring.springbootintro.exception.OrderProcessingException;
import spring.springbootintro.mapper.OrderItemMapper;
import spring.springbootintro.mapper.OrderMapper;
import spring.springbootintro.model.CartItem;
import spring.springbootintro.model.Order;
import spring.springbootintro.model.OrderItem;
import spring.springbootintro.model.ShoppingCart;
import spring.springbootintro.model.Status;
import spring.springbootintro.repository.OrderRepository;
import spring.springbootintro.repository.ShoppingCartRepository;
import spring.springbootintro.service.OrderService;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public Page<OrderDto> getOrderHistory(Long userId, Pageable pageable) {
        return orderRepository.findAllByUserId(userId, pageable)
                .map(orderMapper::toDto);
    }

    @Override
    @Transactional
    public OrderDto createOrder(Long userId, CreateOrderRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find"
                        + " shopping cart for user with id: " + userId));

        if (shoppingCart.getCartItems().isEmpty()) {
            throw new OrderProcessingException("Can't place order with empty shopping cart");
        }

        Order order = new Order();
        order.setUser(shoppingCart.getUser());
        order.setStatus(Status.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.getShippingAddress());

        Set<OrderItem> orderItems = new HashSet<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : shoppingCart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getBook().getPrice());

            orderItems.add(orderItem);

            BigDecimal itemTotal = orderItem.getPrice()
                    .multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            total = total.add(itemTotal);
        }

        order.setOrderItems(orderItems);
        order.setTotal(total);

        Order savedOrder = orderRepository.save(order);
        shoppingCart.getCartItems().clear();

        return orderMapper.toDto(savedOrder);
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatus(Long orderId, UpdateOrderStatusRequestDto orderDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find order with id: "
                        + orderId));

        order.setStatus(orderDto.getStatus());
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderItemDto> getOrderItems(Long userId, Long orderId) {
        Order order = getOrderForUser(userId, orderId);
        return order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getOrderItem(Long userId, Long orderId, Long itemId) {
        Order order = getOrderForUser(userId, orderId);
        return order.getOrderItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .map(orderItemMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Can't find item with id: "
                        + itemId + " in order: " + orderId));
    }

    private Order getOrderForUser(Long userId, Long orderId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find order with id: "
                        + orderId));
        return order;
    }
}
