package spring.springbootintro.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import spring.springbootintro.model.Book;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Container
    static MySQLContainer<?> database = new MySQLContainer<>("mysql:8.0.33")
            .withDatabaseName("spring_test")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
    }

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Verify save() and findById() methods work correctly")
    void save_ValidBook_ShouldReturnBook() {
        // GIVEN
        Book book = new Book();
        book.setTitle("Good book");
        book.setAuthor("Good author");
        book.setPrice(BigDecimal.valueOf(800));
        book.setIsbn("123-0132350884");

        // WHEN
        Book savedBook = bookRepository.save(book);
        Optional<Book> retrievedBook = bookRepository.findById(savedBook.getId());

        // THEN
        assertTrue(retrievedBook.isPresent());

    }
}