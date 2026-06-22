package spring.springbootintro.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import spring.springbootintro.dto.CreateCategoryRequestDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {

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

    // CREATE CATEGORY ENDPOINT
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Create a new category (Positive)")
    @Sql(scripts = "classpath:database/categories/remove-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createCategory_ValidRequestDto_Success() throws Exception {
        // Given
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("New API Category");
        requestDto.setDescription("Category description for tests");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        // When & Then
        mockMvc.perform(post("/categories")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(requestDto.getName()))
                .andExpect(jsonPath("$.description").value(requestDto.getDescription()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Create a new category with missing name (Negative)")
    void createCategory_InvalidRequestDto_ReturnsBadRequest() throws Exception {
        // Given
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setDescription("Missing name field");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        // When & Then
        mockMvc.perform(post("/categories")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // GET ALL CATEGORIES ENDPOINT
    @Test
    @WithMockUser(username = "user")
    @DisplayName("Get all categories (Positive)")
    @Sql(scripts = {
            "classpath:database/categories/remove-categories.sql",
            "classpath:database/categories/add-category.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/categories/remove-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAll_GivenCategoriesInCatalog_ReturnsAllCategories() throws Exception {
        // When & Then
        mockMvc.perform(get("/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].name")
                        .value("Test Category"));
    }

    // GET CATEGORY BY ID
    @Test
    @WithMockUser(username = "user")
    @DisplayName("Get category by valid ID (Positive)")
    @Sql(scripts = {
            "classpath:database/categories/remove-categories.sql",
            "classpath:database/categories/add-category.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/categories/remove-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getCategoryById_ValidId_ReturnsCategoryDto() throws Exception {
        // When & Then
        mockMvc.perform(get("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Category"));
    }

    @Test
    @WithMockUser(username = "user")
    @DisplayName("Get category by invalid ID (Negative)")
    void getCategoryById_InvalidId_ReturnsNotFound() throws Exception {
        // When & Then
        mockMvc.perform(get("/categories/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    // UPDATE CATEGORY
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Update existing category (Positive)")
    @Sql(scripts = {
            "classpath:database/categories/remove-categories.sql",
            "classpath:database/categories/add-category.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/categories/remove-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateCategory_ValidRequestDto_ReturnsUpdatedCategory() throws Exception {
        // Given
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Updated Category Name");
        requestDto.setDescription("Updated Description");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        // When & Then
        mockMvc.perform(put("/categories/1")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(requestDto.getName()))
                .andExpect(jsonPath("$.description").value(requestDto.getDescription()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Update category with invalid ID (Negative)")
    void updateCategory_InvalidId_ReturnsNotFound() throws Exception {
        // Given
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Updated Category Name");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        // When & Then
        mockMvc.perform(put("/categories/100")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    // DELETE CATEGORY
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Delete category by valid ID (Positive)")
    @Sql(scripts = {
            "classpath:database/categories/remove-categories.sql",
            "classpath:database/categories/add-category.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/categories/remove-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteCategory_ValidId_Success() throws Exception {
        // When & Then
        mockMvc.perform(delete("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Delete category by invalid ID (Negative)")
    void deleteCategory_InvalidId_ReturnsNotFound() throws Exception {
        // When & Then
        mockMvc.perform(delete("/categories/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    // GET BOOKS BY CATEGORY ENDPOINT
    @Test
    @WithMockUser(username = "user")
    @DisplayName("Get books by valid category ID (Positive)")
    @Sql(scripts = {
            "classpath:database/categories/remove-categories.sql",
            "classpath:database/categories/add-category.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/categories/remove-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getBooksByCategoryId_ValidId_ReturnsBooksList() throws Exception {
        // When & Then
        mockMvc.perform(get("/categories/1/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}