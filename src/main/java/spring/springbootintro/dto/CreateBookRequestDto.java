package spring.springbootintro.dto;

import lombok.Data;

@Data
public class CreateBookRequestDto {
    private String title;
    private String author;
    private String isbn;
    private String coverImage;
    private String description;
    private String price;

}
