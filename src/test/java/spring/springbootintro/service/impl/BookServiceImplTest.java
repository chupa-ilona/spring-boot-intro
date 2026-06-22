package spring.springbootintro.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import spring.springbootintro.dto.BookDto;
import spring.springbootintro.dto.BookDtoWithoutCategoryIds;
import spring.springbootintro.dto.CreateBookRequestDto;
import spring.springbootintro.exception.EntityNotFoundException;
import spring.springbootintro.mapper.BookMapper;
import spring.springbootintro.model.Book;
import spring.springbootintro.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Verify getById() method works with valid ID")
    void findById_WithValidId_ShouldReturnValidBookDto() {
        // GIVEN
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setPrice(BigDecimal.valueOf(500));

        BookDto bookDto = new BookDto();
        bookDto.setId(bookId);
        bookDto.setTitle("Harry Potter");
        bookDto.setAuthor("J.K. Rowling");
        bookDto.setPrice(BigDecimal.valueOf(500));

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        // WHEN
        BookDto actual = bookService.findById(bookId);

        // THEN
        assertNotNull(actual);
        assertEquals(bookDto.getTitle(), actual.getTitle());
        assertEquals(bookDto.getAuthor(), actual.getAuthor());

        verify(bookRepository).findById(bookId);
        verify(bookMapper, times(1)).toDto(book);
    }

    @Test
    @DisplayName("Verify getById() method throws exception with invalid ID")
    void getById_WithInvalidId_ShouldThrowException() {
        //GIVEN
        Long bookId = 100L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // WHEN
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.findById(bookId)
        );

        String expectedMessage = "Can not find book with id: " + bookId;
        String actualMessage = exception.getMessage();

        // THEN
        assertEquals(expectedMessage, actualMessage);

        verify(bookMapper, times(0)).toDto(any());
    }

    @Test
    @DisplayName("Verify save() method works correctly")
    void save_ValidCreateBookRequestDto_ShouldReturnBookDto() {
        // --- GIVEN ---
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("The Great Gatsby");
        requestDto.setAuthor("F. Scott Fitzgerald");
        requestDto.setPrice(BigDecimal.valueOf(400));

        Book book = new Book();
        book.setTitle("The Great Gatsby");
        book.setAuthor("F. Scott Fitzgerald");
        book.setPrice(BigDecimal.valueOf(400));

        Book savedBook = new Book();
        savedBook.setId(2L);
        savedBook.setTitle("The Great Gatsby");
        savedBook.setAuthor("F. Scott Fitzgerald");
        savedBook.setPrice(BigDecimal.valueOf(400));

        BookDto bookDto = new BookDto();
        bookDto.setTitle("The Great Gatsby");
        bookDto.setAuthor("F. Scott Fitzgerald");
        bookDto.setPrice(BigDecimal.valueOf(400));

        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.toDto(savedBook)).thenReturn(bookDto);

        // --- WHEN ---
        BookDto actual = bookService.save(requestDto);

        // --- THEN ---
        assertNotNull(actual);
        assertEquals(bookDto.getId(), actual.getId());
        assertEquals(bookDto.getTitle(), actual.getTitle());

        verify(bookMapper).toModel(requestDto);
        verify(bookRepository).save(book);
        verify(bookMapper).toDto(savedBook);
    }

    @Test
    @DisplayName("Verify findAll() method works")
    void findAll_ValidPageable_ShouldReturnPageOfBookDtos() {
        // GIVEN
        Pageable pageable = PageRequest.of(0, 10);
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Test Book");

        Page<Book> bookPage = new PageImpl<>(java.util.List.of(book), pageable, 1);

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        // WHEN
        Page<BookDto> actual = bookService.findAll(pageable);

        // THEN
        assertEquals(1, actual.getContent().size());
        assertEquals(bookDto.getTitle(), actual.getContent().get(0).getTitle());

        verify(bookRepository).findAll(pageable);
        verify(bookMapper).toDto(book);
    }

    @Test
    @DisplayName("Verify update() method works correctly with valid ID")
    void update_WithValidId_ShouldReturnUpdatedBookDto() {
        // GIVEN
        Long bookId = 1L;
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Updated Title");

        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Old Title");

        Book updatedBook = new Book();
        updatedBook.setId(bookId);
        updatedBook.setTitle("Updated Title");

        BookDto expectedDto = new BookDto();
        expectedDto.setId(bookId);
        expectedDto.setTitle("Updated Title");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(updatedBook);
        when(bookMapper.toDto(updatedBook)).thenReturn(expectedDto);

        // WHEN
        BookDto actual = bookService.update(bookId, requestDto);

        // THEN
        assertNotNull(actual);
        assertEquals(expectedDto.getId(), actual.getId());
        assertEquals(expectedDto.getTitle(), actual.getTitle());

        verify(bookRepository).findById(bookId);
        verify(bookMapper).updateBookFromDto(requestDto, book);
        verify(bookRepository).save(book);
        verify(bookMapper).toDto(updatedBook);
    }

    @Test
    @DisplayName("Verify update() throws exception with invalid ID")
    void update_WithInvalidId_ShouldThrowException() {
        // GIVEN
        Long bookId = 100L;
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Updated Title");

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // WHEN
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.update(bookId, requestDto)
        );

        // THEN
        String expectedMessage = "Can not find book with id: " + bookId;
        assertEquals(expectedMessage, exception.getMessage());

        verify(bookRepository).findById(bookId);
        verify(bookRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Verify delete() method works correctly with valid ID")
    void delete_WithValidId_ShouldCallRepositoryDelete() {
        // GIVEN
        Long bookId = 1L;
        when(bookRepository.existsById(bookId)).thenReturn(true);

        // WHEN
        bookService.delete(bookId);

        // THEN
        verify(bookRepository).existsById(bookId);
        verify(bookRepository).deleteById(bookId);
    }

    @Test
    @DisplayName("Verify delete() throws exception with invalid ID")
    void delete_WithInvalidId_ShouldThrowException() {
        // GIVEN
        Long bookId = 100L;
        when(bookRepository.existsById(bookId)).thenReturn(false);

        // WHEN
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.delete(bookId)
        );

        // THEN
        String expectedMessage = "Can't find book by id: " + bookId;
        assertEquals(expectedMessage, exception.getMessage());

        verify(bookRepository).existsById(bookId);
        verify(bookRepository, times(0)).deleteById(any());
    }

    @Test
    @DisplayName("Verify findAllByCategoriesId() method works")
    void findAllByCategoriesId_ValidId_ShouldReturnListOfBookDtosWithoutCategoryIds() {
        // GIVEN
        Long categoryId = 1L;
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        BookDtoWithoutCategoryIds bookDto = new BookDtoWithoutCategoryIds();
        bookDto.setId(1L);
        bookDto.setTitle("Test Book");

        when(bookRepository.findAllByCategoriesId(categoryId)).thenReturn(List.of(book));
        when(bookMapper.toDtoWithoutCategories(book)).thenReturn(bookDto);

        // WHEN
        List<BookDtoWithoutCategoryIds> actual = bookService.findAllByCategoriesId(categoryId);

        // THEN
        assertEquals(1, actual.size());
        assertEquals(bookDto.getTitle(), actual.get(0).getTitle());

        verify(bookRepository).findAllByCategoriesId(categoryId);
        verify(bookMapper).toDtoWithoutCategories(book);
    }
}
