package spring.springbootintro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.springbootintro.dto.OrderDto;
import spring.springbootintro.model.Order;

@Mapper(config = spring.springbootintro.config.MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    OrderDto toDto(Order order);

}
