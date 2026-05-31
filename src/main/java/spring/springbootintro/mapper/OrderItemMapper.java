package spring.springbootintro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.springbootintro.dto.OrderItemDto;
import spring.springbootintro.model.OrderItem;

@Mapper(config = spring.springbootintro.config.MapperConfig.class)
public interface OrderItemMapper {

    @Mapping(source = "book.id", target = "bookId")
    OrderItemDto toDto(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    OrderItem toModel(OrderItemDto orderItemDto);

}
