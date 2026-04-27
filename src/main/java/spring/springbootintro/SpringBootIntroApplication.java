package spring.springbootintro;

import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import spring.springbootintro.model.Book;
import spring.springbootintro.service.BookService;

@SpringBootApplication
public class SpringBootIntroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootIntroApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(BookService bookService) {
        return args -> {
            Book book = new Book();
            book.setTitle("1984");
            book.setAuthor("George Orwell");
            book.setIsbn("978-0451524935");
            book.setPrice(new BigDecimal("15.99"));
            book.setDescription("Dystopian novel");

            bookService.save(book);

            System.out.println("Books in DB:");
            System.out.println(bookService.findAll());
        };
    }
}
