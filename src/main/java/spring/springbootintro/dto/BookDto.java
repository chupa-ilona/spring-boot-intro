package spring.springbootintro.dto;

import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String coverImage;
    private String description;
    private BigDecimal price;
    private Set<Long> categoryIds;
}
