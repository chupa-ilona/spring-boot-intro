package spring.springbootintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import spring.springbootintro.dto.CreateOrderRequestDto;
import spring.springbootintro.dto.OrderDto;
import spring.springbootintro.dto.OrderItemDto;
import spring.springbootintro.dto.UpdateOrderStatusRequestDto;
import spring.springbootintro.model.User;
import spring.springbootintro.service.OrderService;

@Tag(name = "Order management", description = "Endpoints for managing user orders")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Create an order",
            description = "Creates a new order from the user's current"
                    + " shopping cart and clears the cart.")
    public OrderDto createOrder(Authentication authentication,
                               @RequestBody @Valid CreateOrderRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return orderService.createOrder(user.getId(), requestDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get order history",
            description = "Retrieves the order history for the currently authenticated user.")
    public Page<OrderDto> getOrderHistory(Authentication authentication, Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderHistory(user.getId(), pageable);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update order status",
            description = "Updates the status of an existing order. Available only for admins.")
    public OrderDto updateOrderStatus(@PathVariable Long id,
                                      @RequestBody @Valid UpdateOrderStatusRequestDto requestDto) {
        return orderService.updateOrderStatus(id, requestDto);
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get all items from an order",
            description = "Retrieves all OrderItems for a specific order belonging to the user.")
    public List<OrderItemDto> getOrderItems(Authentication authentication,
                                            @PathVariable Long orderId) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderItems(user.getId(), orderId);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get a specific item from an order",
            description = "Retrieves a specific OrderItem by its ID within a specific order.")
    public OrderItemDto getOrderItem(Authentication authentication,
                                     @PathVariable Long orderId,
                                     @PathVariable Long itemId) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderItem(user.getId(), orderId, itemId);
    }
}
