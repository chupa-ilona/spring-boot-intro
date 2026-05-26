package spring.springbootintro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import spring.springbootintro.dto.CartItemDto;
import spring.springbootintro.dto.CreateCartItemRequestDto;
import spring.springbootintro.model.CartItem;

@Mapper(config = spring.springbootintro.config.MapperConfig.class)
public interface CartItemMapper {

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    CartItem toModel(CreateCartItemRequestDto requestDto);

    void updateCartItemFromDto(CreateCartItemRequestDto cartItemDto,
                               @MappingTarget CartItem cartItem);

}
