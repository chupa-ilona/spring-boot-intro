package spring.springbootintro.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import spring.springbootintro.model.OrderItem;
import spring.springbootintro.model.Status;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private Set<OrderItem> orderItems;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private Status status;

}
