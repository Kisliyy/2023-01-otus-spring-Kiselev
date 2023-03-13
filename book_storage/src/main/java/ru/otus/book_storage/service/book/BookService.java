package ru.otus.book_storage.service.book;

import ru.otus.book_storage.models.Book;

import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> getAllBook();

    void deleteById(Long id);

    Book getById(Long id);

    void updateBook(Book book);
}
