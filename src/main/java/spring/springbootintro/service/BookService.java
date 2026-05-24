package spring.springbootintro.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.springbootintro.dto.BookDto;
import spring.springbootintro.dto.BookDtoWithoutCategoryIds;
import spring.springbootintro.dto.CreateBookRequestDto;

public interface BookService {

    BookDto save(CreateBookRequestDto createBookRequestDto);

    Page<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto update(Long id, CreateBookRequestDto createBookRequestDto);

    void delete(Long id);

    List<BookDtoWithoutCategoryIds> findAllByCategoriesId(Long id);

}
