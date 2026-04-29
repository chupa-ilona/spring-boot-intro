package spring.springbootintro.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.springbootintro.model.Book;
import spring.springbootintro.repository.BookRepository;
import spring.springbootintro.service.BookService;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public Book save(Book book) {
        bookRepository.save(book);
        return book;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }
}
