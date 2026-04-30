package spring.springbootintro.service;

import java.util.List;

import spring.springbootintro.dto.BookDto;
import spring.springbootintro.dto.CreateBookRequestDto;
import spring.springbootintro.model.Book;

public interface BookService {

    Book save(CreateBookRequestDto createBookRequestDto);

    List<BookDto> findAll();
}
