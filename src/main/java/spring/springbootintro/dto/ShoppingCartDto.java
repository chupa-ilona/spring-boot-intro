package spring.springbootintro.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartDto {
    private String userId;
    private List<CartItemDto> cartItems;
}
