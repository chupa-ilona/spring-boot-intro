package spring.springbootintro.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
@Setter
public class BookDto {
    private String title;
    private String author;
    private String isbn;
    private String coverImage;
    private String description;
    private BigDecimal price;
}
