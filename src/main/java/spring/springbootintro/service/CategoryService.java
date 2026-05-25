package spring.springbootintro.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.springbootintro.dto.CategoryDto;
import spring.springbootintro.dto.CreateCategoryRequestDto;

public interface CategoryService {

    CategoryDto save(CreateCategoryRequestDto categoryDto);

    Page<CategoryDto> findAll(Pageable pageable);

    CategoryDto findById(Long id);

    CategoryDto update(Long id, CreateCategoryRequestDto categoryDto);

    void delete(Long id);

}
