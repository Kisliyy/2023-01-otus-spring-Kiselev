package ru.otus.book_storage.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.book_storage.domain.author.dao.AuthorDao;
import ru.otus.book_storage.domain.author.model.Author;
import ru.otus.book_storage.domain.book.dao.BookDao;
import ru.otus.book_storage.domain.book.model.Book;
import ru.otus.book_storage.domain.genre.dao.GenreDao;
import ru.otus.book_storage.domain.genre.model.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public void save(Book saveBook) {
        checkAuthorAndGenre(saveBook);
        bookDao.save(saveBook);
    }

    @Override
    public List<Book> getAllBook() {
        return bookDao.getAll();
    }

    @Override
    public void deleteById(Long id) {
        bookDao.deleteById(id);
    }

    @Override
    public Book getById(Long id) {
        return bookDao.getById(id);
    }

    @Override
    public void updateBook(Book book) {
        checkAuthorAndGenre(book);
        bookDao.update(book);
    }

    private void checkAuthorAndGenre(Book book) {
        Author author = book.getAuthor();
        Author authorFromDb = authorDao.findByFirstNameAndLastName(author.getFirstName(), author.getLastName());
        if (authorFromDb != null) {
            book.setAuthor(authorFromDb);
        } else {
            long authorId = authorDao.save(author);
            author.setId(authorId);
        }
        Genre genre = book.getGenre();
        Genre genreFromDb = genreDao.getByGenre(genre.getGenre());
        if (genreFromDb != null) {
            book.setGenre(genreFromDb);
        } else {
            long genreId = genreDao.save(genre);
            genre.setId(genreId);
        }
    }
}
