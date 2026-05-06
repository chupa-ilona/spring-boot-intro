package spring.springbootintro.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BookDto {
    private String title;
    private String author;
    private String isbn;
    private String coverImage;
    private String description;
    private BigDecimal price;
}
