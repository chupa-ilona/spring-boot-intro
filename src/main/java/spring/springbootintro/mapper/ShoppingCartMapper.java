package spring.springbootintro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.springbootintro.dto.ShoppingCartDto;
import spring.springbootintro.model.ShoppingCart;

@Mapper(config = spring.springbootintro.config.MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {

    @Mapping(source = "user.id", target = "userId")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
