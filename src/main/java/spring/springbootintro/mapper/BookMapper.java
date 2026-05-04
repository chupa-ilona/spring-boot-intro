package spring.springbootintro.mapper;

import org.mapstruct.Mapper;
import spring.springbootintro.dto.BookDto;
import spring.springbootintro.dto.CreateBookRequestDto;
import spring.springbootintro.model.Book;

@Mapper(config = spring.springbootintro.config.MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto createBookRequestDto);
}
