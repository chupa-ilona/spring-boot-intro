package spring.springbootintro.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCartItemRequestDto {
    @NotNull
    private Long bookId;

    @NotBlank
    private String bookTitle;

    @NotNull
    @Min(1)
    private int quantity;
}
