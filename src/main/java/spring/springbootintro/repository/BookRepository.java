package spring.springbootintro.repository;

import java.util.List;
import spring.springbootintro.model.Book;

public interface BookRepository {

    Book save(Book book);

    List<Book> findAll();
}
