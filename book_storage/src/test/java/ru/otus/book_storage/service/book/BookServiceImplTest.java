package ru.otus.book_storage.service.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.book_storage.dao.book.BookDao;
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
    private BookDao bookDao;
    @MockBean
    private GenreService genreService;
    @MockBean
    private AuthorService authorService;
    @Autowired
    private BookService bookService;

    private final String title = "test title";
    private final long authorId = 1;
    private final long genreId = 10;
    private final String firstName = "first_name";
    private final String lastName = "last_name";
    private final String bookGenre = "genre";
    private Author author;
    private Genre genre;
    private Book newBook;


    @BeforeEach
    void init() {
        author = Author.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        genre = Genre.builder()
                .genre(bookGenre)
                .build();

        newBook = Book.builder()
                .title(title)
                .author(author)
                .genre(genre)
                .build();
    }


    @Test
    @DisplayName("Saving a book with a new author and genre")
    void saveBookWithNewAuthorAndGenreTest() {
        Author savedAuthor = Author
                .builder()
                .id(authorId)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        Genre savedGenre = Genre
                .builder()
                .id(genreId)
                .genre(bookGenre)
                .build();

        Book savedBook = Book
                .builder()
                .genre(savedGenre)
                .author(savedAuthor)
                .title(title)
                .build();

        when(authorService.findByFirstNameAndLastName(firstName, lastName)).thenReturn(null);
        when(authorService.save(author)).thenReturn(savedAuthor);

        when(genreService.getByGenre(bookGenre)).thenReturn(null);
        when(genreService.save(genre)).thenReturn(savedGenre);

        bookService.save(newBook);

        verify(authorService, times(1)).findByFirstNameAndLastName(firstName, lastName);
        verify(authorService, times(1)).save(author);

        verify(genreService, times(1)).getByGenre(bookGenre);
        verify(genreService, times(1)).save(genre);

        verify(bookDao, times(1)).save(savedBook);
    }

    @Test
    @DisplayName("Saving a book with an existing author and genre")
    void saveBookWithExistingAuthorAndGenreTest() {
        Author existAuthor = Author
                .builder()
                .id(authorId)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        Genre existGenre = Genre
                .builder()
                .id(genreId)
                .genre(bookGenre)
                .build();

        Book savedBook = Book
                .builder()
                .genre(existGenre)
                .author(existAuthor)
                .title(title)
                .build();

        when(authorService.findByFirstNameAndLastName(firstName, lastName)).thenReturn(existAuthor);
        when(genreService.getByGenre(bookGenre)).thenReturn(existGenre);

        bookService.save(newBook);

        verify(authorService, times(1)).findByFirstNameAndLastName(firstName, lastName);
        verify(authorService, times(0)).save(author);

        verify(genreService, times(1)).getByGenre(bookGenre);
        verify(genreService, times(0)).save(genre);

        verify(bookDao, times(1)).save(savedBook);
    }

    @Test
    @DisplayName("It will not return an empty list of books")
    void getAllBooksNotEmptyListTest() {
        List<Book> books = List.of(
                new Book(),
                new Book(),
                new Book()
        );
        when(bookDao.getAll()).thenReturn(books);
        List<Book> allBook = bookService.getAllBook();
        verify(bookDao, times(1)).getAll();
        assertEquals(3, allBook.size());
    }

    @Test
    @DisplayName("Delete book by id")
    void deleteBookByIdTest() {
        long bookId = 1L;
        doNothing().when(bookDao).deleteById(bookId);
        bookService.deleteById(bookId);
        verify(bookDao, times(1)).deleteById(bookId);
    }

    @Test
    @DisplayName("Find book by id successful")
    void findBookByIdSuccessfulTest() {
        long bookId = 1L;
        Book existBook = Book
                .builder()
                .id(bookId)
                .author(author)
                .genre(genre)
                .title(title)
                .build();


        when(bookDao.getById(bookId)).thenReturn(Optional.of(existBook));
        Optional<Book> byId = bookDao.getById(bookId);
        assertFalse(byId.isEmpty());
        Book findBook = byId.get();
        verify(bookDao, times(1)).getById(bookId);
        assertEquals(existBook, findBook);
    }

    @Test
    @DisplayName("Find book by id return exception")
    void findBookByIdReturnNotFoundExceptionTest() {
        when(bookDao.getById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> bookService.getById(anyLong()));
        verify(bookDao, times(1)).getById(anyLong());
    }


    @Test
    @DisplayName("Update book with new update author and genre")
    void updateBookWithNewAuthorAndGenre() {
        Book updateBook = newBook;

        Author updateAuthor = Author
                .builder()
                .id(authorId)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        Genre updateGenre = Genre
                .builder()
                .id(genreId)
                .genre(bookGenre)
                .build();

        Book updateSaveBook = Book
                .builder()
                .genre(updateGenre)
                .author(updateAuthor)
                .title(title)
                .build();

        when(authorService.findByFirstNameAndLastName(firstName, lastName)).thenReturn(null);
        when(authorService.save(author)).thenReturn(updateAuthor);

        when(genreService.getByGenre(bookGenre)).thenReturn(null);
        when(genreService.save(genre)).thenReturn(updateGenre);

        bookService.updateBook(updateBook);

        verify(authorService, times(1)).findByFirstNameAndLastName(firstName, lastName);
        verify(authorService, times(1)).save(author);

        verify(genreService, times(1)).getByGenre(bookGenre);
        verify(genreService, times(1)).save(genre);

        verify(bookDao, times(1)).update(updateSaveBook);
    }

    @Test
    @DisplayName("Update a book with an existing author and genre")
    void updateBookWithExistingAuthorAndGenreTest() {
        Book updateBook = newBook;

        Author updateExistAuthor = Author
                .builder()
                .id(authorId)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        Genre updateExistGenre = Genre
                .builder()
                .id(genreId)
                .genre(bookGenre)
                .build();

        Book savedUpdateBook = Book
                .builder()
                .genre(updateExistGenre)
                .author(updateExistAuthor)
                .title(title)
                .build();

        when(authorService.findByFirstNameAndLastName(firstName, lastName)).thenReturn(updateExistAuthor);
        when(genreService.getByGenre(bookGenre)).thenReturn(updateExistGenre);

        bookService.save(updateBook);

        verify(authorService, times(1)).findByFirstNameAndLastName(firstName, lastName);
        verify(authorService, times(0)).save(author);

        verify(genreService, times(1)).getByGenre(bookGenre);
        verify(genreService, times(0)).save(genre);

        verify(bookDao, times(1)).save(savedUpdateBook);
    }

}