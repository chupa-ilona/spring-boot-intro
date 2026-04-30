package spring.springbootintro.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.springbootintro.dto.BookDto;
import spring.springbootintro.dto.CreateBookRequestDto;
import spring.springbootintro.service.BookService;

@RequiredArgsConstructor
@RestController()
public class BookController {
    private final BookService bookService;

    @GetMapping("/books")
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/books/{id}")
    public BookDto getBookById(Long id) {
        return bookService.findAll().stream()
                .filter(book -> book.getId().equals(id))
                .findFirst().orElse(null);
    }

    @PostMapping("books")
    public BookDto createBook(@RequestBody CreateBookRequestDto bookDto) {
        return bookService.save(bookDto);
    }
}
