package ru.otus.book_storage.service.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.book_storage.dao.book.BookDao;
import ru.otus.book_storage.exceptions.NotFoundException;
import ru.otus.book_storage.models.Author;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.models.Genre;
import ru.otus.book_storage.service.author.AuthorService;
import ru.otus.book_storage.service.genre.GenreService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public Book save(Book saveBook) {
        checkAuthorAndGenre(saveBook);
        return bookDao.save(saveBook);
    }

    @Override
    public List<Book> getAllBook() {
        return bookDao.findAll();
    }

    @Override
    public void deleteById(Long id) {
        bookDao.deleteById(id);
    }

    @Override
    public Book getById(Long id) {
        return bookDao
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found by id: " + id));
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        Optional<Book> findBook = bookDao.findById(book.getId());
        if (findBook.isPresent()) {
            checkAuthorAndGenre(book);
            bookDao.save(book);
        }
    }

    private void checkAuthorAndGenre(Book book) {
        Author author = book.getAuthor();
        Author findAuthor = authorService.findById(author.getId());
        book.setAuthor(findAuthor);

        Genre genre = book.getGenre();
        Genre findGenre = genreService.getById(genre.getId());
        book.setGenre(findGenre);
    }


}
