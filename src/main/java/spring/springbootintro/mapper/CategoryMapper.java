package spring.springbootintro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import spring.springbootintro.dto.CategoryDto;
import spring.springbootintro.dto.CreateCategoryRequestDto;
import spring.springbootintro.model.Category;

@Mapper(config = spring.springbootintro.config.MapperConfig.class)
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Category toModel(CreateCategoryRequestDto categoryDto);

    void updateCategoryFromDto(CreateCategoryRequestDto categoryDto,
                               @MappingTarget Category category);

}
