package spring.springbootintro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.springbootintro.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
