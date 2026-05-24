package spring.springbootintro.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    private String isbn;
    private String coverImage;
    private String description;

    @NotNull
    @Min(0)
    private BigDecimal price;

    @NotEmpty
    private Set<Long> categoryIds;

}
