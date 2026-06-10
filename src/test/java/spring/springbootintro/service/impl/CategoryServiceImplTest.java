package spring.springbootintro.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.springbootintro.dto.CategoryDto;
import spring.springbootintro.exception.EntityNotFoundException;
import spring.springbootintro.mapper.CategoryMapper;
import spring.springbootintro.model.Category;
import spring.springbootintro.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Verify getById() method works with valid ID")
    void findById_WithValidId_ShouldReturnValidCategoryDto() {
        // GIVEN
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Fantasy");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(categoryId);
        categoryDto.setName("Fantasy");


        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        // WHEN
        CategoryDto actual = categoryService.findById(categoryId);

        // --- THEN
        assertNotNull(actual);
        assertEquals(categoryDto.getName(), actual.getName());
        assertEquals(categoryDto.getId(), actual.getId());

        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    @DisplayName("Verify getById() method throws exception with invalid ID")
    void findById_WithInvalidId_ShouldThrowException() {

        //GIVEN
        Long categoryId = 100L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        //WHEN
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.findById(categoryId)
        );

        String expectedMessage = "Can not find category with id: "+ categoryId;
        String actualMessage = exception.getMessage();

        //THEN

        assertEquals(expectedMessage, actualMessage);
        verify(categoryMapper, times(0)).toDto(org.mockito.Mockito.any());
    }
}