package spring.springbootintro.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import spring.springbootintro.model.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Find category by valid ID")
    @Sql(scripts = {
            "classpath:database/categories/remove-categories.sql",
            "classpath:database/categories/add-category.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/categories/remove-categories.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findById_GivenValidId_ReturnsCategory() {
        // Given
        Long categoryId = 1L;

        // When
        Optional<Category> actualCategory = categoryRepository.findById(categoryId);

        // Then
        assertTrue(actualCategory.isPresent(), "Category should be found in the database");
        assertEquals(1L, actualCategory.get().getId());
        assertEquals("Test Category", actualCategory.get().getName());
    }
}