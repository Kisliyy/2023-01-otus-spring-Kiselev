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

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    @Transactional
    public Book save(Book saveBook) {
        checkAuthorAndGenre(saveBook);
        return bookDao.save(saveBook);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBook() {
        return bookDao.getAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        bookDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getById(Long id) {
        return bookDao
                .getById(id)
                .orElseThrow(() -> new NotFoundException("Book not found by id: " + id));
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        checkAuthorAndGenre(book);
        bookDao.update(book);
    }

    private void checkAuthorAndGenre(Book book) {
        Author author = book.getAuthor();
        Author authorFromDb = authorService.findByFirstNameAndLastName(author.getFirstName(), author.getLastName());
        if (authorFromDb == null) {
            authorFromDb = authorService.save(author);
        }
        book.setAuthor(authorFromDb);
        Genre genre = book.getGenre();
        Genre genreFromDb = genreService.getByGenre(genre.getGenre());
        if (genreFromDb == null) {
            genreFromDb = genreService.save(genre);
        }
        book.setGenre(genreFromDb);
    }
}
