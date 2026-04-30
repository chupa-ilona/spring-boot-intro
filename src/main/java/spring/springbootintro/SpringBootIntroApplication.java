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

}
