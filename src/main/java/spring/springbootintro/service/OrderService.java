package spring.springbootintro.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.springbootintro.dto.CreateOrderRequestDto;
import spring.springbootintro.dto.OrderDto;
import spring.springbootintro.dto.OrderItemDto;
import spring.springbootintro.dto.UpdateOrderStatusRequestDto;

public interface OrderService {

    Page<OrderDto> getOrderHistory(Long userId, Pageable pageable);

    OrderDto createOrder(Long userId, CreateOrderRequestDto requestDto);

    OrderDto updateOrderStatus(Long orderId, UpdateOrderStatusRequestDto orderDto);

    List<OrderItemDto> getOrderItems(Long userId, Long orderId);

    OrderItemDto getOrderItem(Long userId, Long orderId, Long itemId);

}
