package spring.springbootintro.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import spring.springbootintro.dto.BookDtoWithoutCategoryIds;
import spring.springbootintro.dto.CategoryDto;
import spring.springbootintro.dto.CreateCategoryRequestDto;
import spring.springbootintro.service.BookService;
import spring.springbootintro.service.CategoryService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final BookService bookService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Valid CreateCategoryRequestDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @GetMapping
    public List<CategoryDto> getAllCategories(Pageable pageable) {
        return categoryService.findAll(pageable).toList();
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CategoryDto updateCategory(@PathVariable Long id,
                                      @RequestBody @Valid CreateCategoryRequestDto categoryDto) {
        return categoryService.update(id, categoryDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @GetMapping("/{id}/books")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(@PathVariable Long id) {
        return bookService.findAllByCategoriesId(id);
    }
}
