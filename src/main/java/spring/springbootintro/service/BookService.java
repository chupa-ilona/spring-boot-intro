package spring.springbootintro.service;

import java.util.List;
import spring.springbootintro.model.Book;

public interface BookService {

    Book save(Book book);

    List<Book> findAll();
}
