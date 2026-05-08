package spring.springbootintro.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequestDto {
    private String title;
    private String author;
    private String isbn;
    private String coverImage;
    private String description;
    private BigDecimal price;

}
