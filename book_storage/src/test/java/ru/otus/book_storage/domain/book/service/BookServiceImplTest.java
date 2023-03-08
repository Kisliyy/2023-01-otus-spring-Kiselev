package ru.otus.book_storage.domain.book.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.book_storage.domain.author.dao.AuthorDao;
import ru.otus.book_storage.domain.author.model.Author;
import ru.otus.book_storage.domain.book.dao.BookDao;
import ru.otus.book_storage.domain.book.model.Book;
import ru.otus.book_storage.domain.genre.dao.GenreDao;
import ru.otus.book_storage.domain.genre.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BookServiceImpl.class)
class BookServiceImplTest {

    @MockBean
    private BookDao bookDao;
    @MockBean
    private GenreDao genreDao;
    @MockBean
    private AuthorDao authorDao;
    @Autowired
    private BookServiceImpl bookService;

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

        when(authorDao.findByFirstNameAndLastName(firstName, lastName)).thenReturn(null);
        when(authorDao.save(author)).thenReturn(authorId);

        when(genreDao.getByGenre(bookGenre)).thenReturn(null);
        when(genreDao.save(genre)).thenReturn(genreId);

        bookService.save(newBook);

        verify(authorDao, times(1)).findByFirstNameAndLastName(firstName, lastName);
        verify(authorDao, times(1)).save(author);

        verify(genreDao, times(1)).getByGenre(bookGenre);
        verify(genreDao, times(1)).save(genre);

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

        when(authorDao.findByFirstNameAndLastName(firstName, lastName)).thenReturn(existAuthor);
        when(genreDao.getByGenre(bookGenre)).thenReturn(existGenre);

        bookService.save(newBook);

        verify(authorDao, times(1)).findByFirstNameAndLastName(firstName, lastName);
        verify(authorDao, times(0)).save(author);

        verify(genreDao, times(1)).getByGenre(bookGenre);
        verify(genreDao, times(0)).save(genre);

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

        when(bookDao.getById(bookId)).thenReturn(existBook);
        Book findBook = bookDao.getById(bookId);
        verify(bookDao, times(1)).getById(bookId);
        assertEquals(existBook, findBook);
    }

    @Test
    @DisplayName("Find book by id return null")
    void findBookByIdReturnNull() {
        long bookId = 1L;

        when(bookDao.getById(bookId)).thenReturn(null);
        Book findBook = bookDao.getById(bookId);
        verify(bookDao, times(1)).getById(bookId);
        assertNull(findBook);
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

        when(authorDao.findByFirstNameAndLastName(firstName, lastName)).thenReturn(null);
        when(authorDao.save(author)).thenReturn(authorId);

        when(genreDao.getByGenre(bookGenre)).thenReturn(null);
        when(genreDao.save(genre)).thenReturn(genreId);

        bookService.updateBook(updateBook);

        verify(authorDao, times(1)).findByFirstNameAndLastName(firstName, lastName);
        verify(authorDao, times(1)).save(author);

        verify(genreDao, times(1)).getByGenre(bookGenre);
        verify(genreDao, times(1)).save(genre);

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

        when(authorDao.findByFirstNameAndLastName(firstName, lastName)).thenReturn(updateExistAuthor);
        when(genreDao.getByGenre(bookGenre)).thenReturn(updateExistGenre);

        bookService.save(updateBook);

        verify(authorDao, times(1)).findByFirstNameAndLastName(firstName, lastName);
        verify(authorDao, times(0)).save(author);

        verify(genreDao, times(1)).getByGenre(bookGenre);
        verify(genreDao, times(0)).save(genre);

        verify(bookDao, times(1)).save(savedUpdateBook);
    }
}