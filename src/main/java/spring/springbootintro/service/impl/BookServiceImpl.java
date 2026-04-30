package spring.springbootintro.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.springbootintro.dto.BookDto;
import spring.springbootintro.dto.CreateBookRequestDto;
import spring.springbootintro.mapper.BookMapper;
import spring.springbootintro.model.Book;
import spring.springbootintro.repository.BookRepository;
import spring.springbootintro.service.BookService;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    public Book save(CreateBookRequestDto requestDto) {
        return this.bookRepository.save(bookMapper.toModel(requestDto));
    }

    @Override
    public List<BookDto> findAll() {
        return this.bookRepository.findAll().stream().map(bookMapper::toDto).toList();
    }
}
