package spring.springbootintro.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import spring.springbootintro.model.Book;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Find all books by valid category ID")
    @Sql(scripts = {
            "classpath:database/categories/add-category.sql",
            "classpath:database/categories/books/add-book.sql",
            "classpath:database/categories/books/add-book-category-dependency.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/categories/books/remove-book-category-dependency.sql",
            "classpath:database/categories/books/remove-books.sql",
            "classpath:database/categories/remove-categories.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategoriesId_GivenValidCategoryId_ReturnsListOfBooks() {
        // Given
        Long categoryId = 1L;

        // When
        List<Book> actualBooks = bookRepository.findAllByCategoriesId(categoryId);

        // Then
        assertEquals(1, actualBooks.size());
        assertEquals("Test Book", actualBooks.get(0).getTitle());
    }
}
