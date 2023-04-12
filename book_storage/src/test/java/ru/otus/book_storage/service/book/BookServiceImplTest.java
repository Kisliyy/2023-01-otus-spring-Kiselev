package ru.otus.book_storage.service.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.book_storage.dao.book.BookRepository;
import ru.otus.book_storage.dto.UpdateBookDto;
import ru.otus.book_storage.exceptions.NotFoundException;
import ru.otus.book_storage.models.Author;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.models.Genre;
import ru.otus.book_storage.service.author.AuthorService;
import ru.otus.book_storage.service.genre.GenreService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest(classes = BookServiceImpl.class)
class BookServiceImplTest {
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private GenreService genreService;
    @MockBean
    private AuthorService authorService;
    @Autowired
    private BookService bookService;

    private final String title = "test title";
    private final long authorId = 1L;
    private final long bookId = 1L;
    private final long genreId = 10L;
    private final String firstName = "first_name";
    private final String lastName = "last_name";
    private final String bookGenre = "genre";
    private Author existAuthor;
    private Genre existGenre;
    private Book existBook;


    @BeforeEach
    void init() {
        existAuthor = Author.builder().id(authorId).firstName(firstName).lastName(lastName).build();
        existGenre = Genre.builder().id(genreId).name(bookGenre).build();
        existBook = Book.builder().id(bookId).title(title).author(existAuthor).genre(existGenre).build();
    }


    @Test
    @DisplayName("Saving a book with an unknown author id will return a NotFoundException")
    void saveBookWithUnknownIdAuthorReturnNotFoundExceptionTest() {
        Long unknownIdAuthor = 20L;
        Long unknownIdGenre = 20L;
        Author author = Author
                .builder()
                .id(unknownIdAuthor)
                .build();

        Genre genre = Genre
                .builder()
                .id(unknownIdGenre)
                .build();

        Book savedBook = Book
                .builder()
                .genre(genre)
                .author(author)
                .title(title).build();

        when(authorService.findById(anyLong())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> bookService.save(savedBook));

        verify(authorService, times(1)).findById(anyLong());
        verify(genreService, times(0)).getById(anyLong());
        verify(bookRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Saving a book with an unknown genre id will return a NotFoundException")
    void saveBookWithUnknownIdGenreReturnNotFoundExceptionTest() {
        Author author = Author.builder().id(authorId).build();

        Author findAuthor = existAuthor;

        Genre genre = Genre.builder().id(anyLong()).build();

        Book savedBook = Book.builder().genre(genre).author(author).title(title).build();

        when(authorService.findById(authorId)).thenReturn(findAuthor);
        when(genreService.getById(anyLong())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> bookService.save(savedBook));

        verify(authorService, times(1)).findById(anyLong());
        verify(genreService, times(1)).getById(anyLong());
        verify(bookRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Successful book saving")
    void saveBookSuccessfulTest() {
        Author author = Author
                .builder()
                .id(authorId)
                .build();

        Author findAuthor = existAuthor;

        Genre genre = Genre
                .builder()
                .id(genreId)
                .build();

        Genre findGenre = existGenre;

        Book book = Book
                .builder()
                .genre(genre)
                .author(author)
                .title(title)
                .build();

        Book savedBook = Book
                .builder()
                .genre(findGenre)
                .author(findAuthor)
                .title(title)
                .build();

        Book persistBook = Book
                .builder()
                .id(bookId)
                .genre(findGenre)
                .author(findAuthor)
                .title(title)
                .build();

        when(authorService.findById(authorId)).thenReturn(findAuthor);
        when(genreService.getById(genreId)).thenReturn(findGenre);
        when(bookRepository.save(savedBook)).thenReturn(persistBook);

        Book persistBookExample = bookService.save(book);

        assertEquals(persistBook, persistBookExample);

        verify(authorService, times(1)).findById(authorId);
        verify(genreService, times(1)).getById(genreId);
        verify(bookRepository, times(1)).save(savedBook);
    }

    @Test
    @DisplayName("It will not return an empty list of books")
    void getAllBooksNotEmptyListTest() {
        List<Book> books = List.of(new Book(), new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(books);
        List<Book> allBook = bookService.getAllBook();
        verify(bookRepository, times(1)).findAll();
        assertEquals(3, allBook.size());
    }

    @Test
    @DisplayName("Delete book by id")
    void deleteBookByIdTest() {
        long bookId = 1L;
        doNothing().when(bookRepository).deleteById(bookId);
        bookService.deleteById(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    @DisplayName("Find book by id successful")
    void findBookByIdSuccessfulTest() {
        long bookId = 1L;
        Book existBook = Book.builder().id(bookId).author(existAuthor).genre(existGenre).title(title).build();


        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existBook));
        Optional<Book> byId = bookRepository.findById(bookId);
        assertFalse(byId.isEmpty());
        Book findBook = byId.get();
        verify(bookRepository, times(1)).findById(bookId);
        assertEquals(existBook, findBook);
    }

    @Test
    @DisplayName("Find book by id return exception")
    void findBookByIdReturnNotFoundExceptionTest() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> bookService.getById(anyLong()));
        verify(bookRepository, times(1)).findById(anyLong());
    }


    @Test
    @DisplayName("Update book with new id author and genre")
    void updateBookWithNewAuthorAndGenre() {
        String newTitle = "newTitle";

        Long updateGenreId = 10000L;
        String updateBookGenre = "updateBookGenre";

        Long updateAuthorId = 10000L;
        String updateFirstNameAuthor = "updateFirstNameAuthor";
        String updateLastNameAuthor = "updateLastNameAuthor";


        Genre updateGenre = Genre
                .builder()
                .id(updateGenreId)
                .build();

        Genre findUpdateGenre = Genre
                .builder()
                .id(updateGenreId)
                .name(updateBookGenre)
                .build();

        Author updateAuthor = Author
                .builder()
                .id(updateAuthorId)
                .build();

        Author findUpdateAuthor = Author
                .builder()
                .id(updateAuthorId)
                .firstName(updateFirstNameAuthor)
                .lastName(updateLastNameAuthor)
                .build();

        UpdateBookDto updatedBookDto =
                new UpdateBookDto(bookId, newTitle, updateAuthorId, updateGenreId);


        Book savedUpdatedBook = Book
                .builder()
                .id(bookId)
                .title(newTitle)
                .author(findUpdateAuthor)
                .genre(findUpdateGenre)
                .build();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existBook));
        when(authorService.findById(updateAuthorId)).thenReturn(findUpdateAuthor);
        when(genreService.getById(updateGenreId)).thenReturn(findUpdateGenre);
        when(bookRepository.save(savedUpdatedBook)).thenReturn(savedUpdatedBook);

        bookService.updateBook(updatedBookDto);

        verify(bookRepository, times(1)).findById(bookId);
        verify(authorService, times(1)).findById(updateAuthorId);

        verify(genreService, times(1)).getById(updateGenreId);
        verify(bookRepository, times(1)).save(savedUpdatedBook);
    }
}