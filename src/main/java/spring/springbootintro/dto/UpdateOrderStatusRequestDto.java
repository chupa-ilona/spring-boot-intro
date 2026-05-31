package spring.springbootintro.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import spring.springbootintro.model.Status;

@Getter
@Setter
public class UpdateOrderStatusRequestDto {
    @NotBlank
    private Status status;
}
