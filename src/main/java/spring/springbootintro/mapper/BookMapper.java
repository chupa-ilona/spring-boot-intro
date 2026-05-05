package spring.springbootintro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.springbootintro.dto.BookDto;
import spring.springbootintro.dto.CreateBookRequestDto;
import spring.springbootintro.model.Book;

@Mapper(config = spring.springbootintro.config.MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Book toModel(CreateBookRequestDto createBookRequestDto);
}
