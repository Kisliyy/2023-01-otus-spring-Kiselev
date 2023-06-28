package ru.otus.book_storage.service.book;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.book_storage.dao.book.BookRepository;
import ru.otus.book_storage.dto.UpdateBookDto;
import ru.otus.book_storage.exceptions.NotFoundException;
import ru.otus.book_storage.models.Author;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.models.Genre;
import ru.otus.book_storage.service.author.AuthorService;
import ru.otus.book_storage.service.genre.GenreService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    @CircuitBreaker(name = "Service_circuitbreaker", fallbackMethod = "fallbackSave")
    public Book save(Book saveBook) {
        checkAuthorAndGenre(saveBook);
        return bookRepository.save(saveBook);
    }

    @Override
    @CircuitBreaker(name = "Service_circuitbreaker", fallbackMethod = "fallbackGetAllBook")
    @Retry(name = "Service_circuitbreaker")
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Override
    @CircuitBreaker(name = "Service_circuitbreaker")
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @CircuitBreaker(name = "Service_circuitbreaker")
    public Book getById(Long id) {
        return bookRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found by id: " + id));
    }

    @Override
    @Transactional
    @CircuitBreaker(name = "Service_circuitbreaker")
    public void updateBook(UpdateBookDto updateBookDto) {
        Book updateBook = getById(updateBookDto.getId());
        updateBook.setTitle(updateBookDto.getTitle());
        updateBook.setGenre(new Genre(updateBookDto.getGenreId()));
        updateBook.setAuthor(new Author(updateBookDto.getAuthorId()));
        checkAuthorAndGenre(updateBook);
        bookRepository.save(updateBook);
    }

    private void checkAuthorAndGenre(Book book) {
        Author author = book.getAuthor();
        Author findAuthor = authorService.findById(author.getId());
        book.setAuthor(findAuthor);

        Genre genre = book.getGenre();
        Genre findGenre = genreService.getById(genre.getId());
        book.setGenre(findGenre);
    }

    private Book fallbackSave(Book saveBook, CallNotPermittedException ex) {
        log.error(ex.getMessage());
        return saveBook;
    }

    private List<Book> fallbackGetAllBook(CallNotPermittedException ex) {
        log.error(ex.getMessage());
        return Collections.emptyList();
    }


}
