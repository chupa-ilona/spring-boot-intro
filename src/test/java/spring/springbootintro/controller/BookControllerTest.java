package spring.springbootintro.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import spring.springbootintro.dto.CreateBookRequestDto;

import java.math.BigDecimal;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    // CREATE BOOK ENDPOINT
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Create a new book (Positive)")
    @Sql(scripts = "classpath:database/categories/add-category.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/categories/books/remove-book-category-dependency.sql",
            "classpath:database/categories/books/remove-books.sql",
            "classpath:database/categories/remove-categories.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createBook_ValidRequestDto_Success() throws Exception {
        // Given
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("New API Book");
        requestDto.setAuthor("API Author");
        requestDto.setPrice(BigDecimal.valueOf(250));
        requestDto.setIsbn("978-3-16-148410-0");
        requestDto.setCategoryIds(Set.of(1L));

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        // When & Then
        mockMvc.perform(post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(requestDto.getTitle()))
                .andExpect(jsonPath("$.author").value(requestDto.getAuthor()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Create a new book with missing fields (Negative)")
    void createBook_InvalidRequestDto_ReturnsBadRequest() throws Exception {
        // Given
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setPrice(BigDecimal.valueOf(250));

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        // When & Then
        mockMvc.perform(post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // GET ALL BOOKS
    @Test
    @WithMockUser(username = "user")
    @DisplayName("Get all books (Positive)")
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
    void getAll_GivenBooksInCatalog_ReturnsAllBooks() throws Exception {
        // When & Then
        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    // GET BOOK BY ID
    @Test
    @WithMockUser(username = "user")
    @DisplayName("Get book by valid ID (Positive)")
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
    void getBookById_ValidId_ReturnsBookDto() throws Exception {
        // When & Then
        mockMvc.perform(get("/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "user")
    @DisplayName("Get book by invalid ID (Negative)")
    void getBookById_InvalidId_ReturnsNotFound() throws Exception {
        // When & Then
        mockMvc.perform(get("/books/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    // UPDATE
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Update existing book (Positive)")
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
    void updateBook_ValidRequestDto_ReturnsUpdatedBook() throws Exception {
        // Given
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Updated Title");
        requestDto.setAuthor("Updated Author");
        requestDto.setPrice(BigDecimal.valueOf(300));
        requestDto.setIsbn("111-222-333");
        requestDto.setCategoryIds(Set.of(1L));

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        // When & Then
        mockMvc.perform(put("/books/1")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(requestDto.getTitle()))
                .andExpect(jsonPath("$.author").value(requestDto.getAuthor()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Update book with non-existing ID (Negative)")
    void updateBook_InvalidId_ReturnsNotFound() throws Exception {
        // Given
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Updated Title");
        requestDto.setAuthor("Updated Author");
        requestDto.setPrice(BigDecimal.valueOf(300));
        requestDto.setIsbn("111-222-333");
        requestDto.setCategoryIds(Set.of(1L));

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        // When & Then
        mockMvc.perform(put("/books/100")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    // DELETE
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Delete book by valid ID (Positive)")
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
    void deleteBook_ValidId_Success() throws Exception {
        // When & Then
        mockMvc.perform(delete("/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // Очікуємо 204 No Content
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Delete book with non-existing ID (Negative)")
    void deleteBook_InvalidId_ReturnsNotFound() throws Exception {
        // When & Then
        mockMvc.perform(delete("/books/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
