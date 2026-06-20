package spring.springbootintro.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import spring.springbootintro.dto.CategoryDto;
import spring.springbootintro.dto.CreateCategoryRequestDto;
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

        // THEN
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
        verify(categoryMapper, times(0)).toDto(any());
    }

    @Test
    @DisplayName("Verify save() method works correctly")
    void save_ValidCategoryDto_ShouldReturnCategoryDto() {
        // GIVEN
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("New Category");

        Category category = new Category();
        category.setName("New Category");

        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("New Category");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("New Category");

        when(categoryMapper.toModel(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        when(categoryMapper.toDto(savedCategory)).thenReturn(categoryDto);

        // WHEN
        CategoryDto actual = categoryService.save(requestDto);

        // THEN
        assertNotNull(actual);
        assertEquals(categoryDto.getId(), actual.getId());
        assertEquals(categoryDto.getName(), actual.getName());

        verify(categoryMapper, times(1)).toModel(requestDto);
        verify(categoryRepository, times(1)).save(category);
        verify(categoryMapper, times(1)).toDto(savedCategory);
    }

    @Test
    @DisplayName("Verify findAll() method works")
    void findAll_ValidPageable_ShouldReturnPageOfCategoryDtos() {
        // GIVEN
        Pageable pageable = PageRequest.of(0, 10);
        Category category = new Category();
        category.setId(1L);
        category.setName("Fantasy");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Fantasy");

        Page<Category> categoryPage = new PageImpl<>(java.util.List.of(category), pageable, 1);

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        // WHEN
        Page<CategoryDto> actual = categoryService.findAll(pageable);

        // THEN
        assertEquals(1, actual.getContent().size());
        assertEquals(categoryDto.getName(), actual.getContent().get(0).getName());

        verify(categoryRepository, times(1)).findAll(pageable);
        verify(categoryMapper, times(1)).toDto(category);
    }

    @Test
    @DisplayName("Verify update() method works correctly with valid ID")
    void update_WithValidId_ShouldReturnUpdatedCategoryDto() {
        // GIVEN
        Long categoryId = 1L;
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Updated Fantasy");

        Category category = new Category();
        category.setId(categoryId);
        category.setName("Old Fantasy");

        Category updatedCategory = new Category();
        updatedCategory.setId(categoryId);
        updatedCategory.setName("Updated Fantasy");

        CategoryDto expectedDto = new CategoryDto();
        expectedDto.setId(categoryId);
        expectedDto.setName("Updated Fantasy");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(updatedCategory);
        when(categoryMapper.toDto(updatedCategory)).thenReturn(expectedDto);

        // WHEN
        CategoryDto actual = categoryService.update(categoryId, requestDto);

        // THEN
        assertNotNull(actual);
        assertEquals(expectedDto.getId(), actual.getId());
        assertEquals(expectedDto.getName(), actual.getName());

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryMapper, times(1)).updateCategoryFromDto(requestDto, category);
        verify(categoryRepository, times(1)).save(category);
        verify(categoryMapper, times(1)).toDto(updatedCategory);
    }

    @Test
    @DisplayName("Verify update() throws exception with invalid ID")
    void update_WithInvalidId_ShouldThrowException() {
        // GIVEN
        Long categoryId = 100L;
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Updated Fantasy");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // WHEN
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.update(categoryId, requestDto)
        );

        // THEN
        String expectedMessage = "Can not find category with id: " + categoryId;
        assertEquals(expectedMessage, exception.getMessage());

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Verify delete() method works correctly with valid ID")
    void delete_WithValidId_ShouldCallRepositoryDelete() {
        // GIVEN
        Long categoryId = 1L;
        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        // WHEN
        categoryService.delete(categoryId);

        // THEN
        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    @DisplayName("Verify delete() throws exception with invalid ID")
    void delete_WithInvalidId_ShouldThrowException() {
        // GIVEN
        Long categoryId = 100L;
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        // WHEN
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.delete(categoryId)
        );

        // THEN
        String expectedMessage = "Can't find category by id: " + categoryId;
        assertEquals(expectedMessage, exception.getMessage());

        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, times(0)).deleteById(any());
    }
}