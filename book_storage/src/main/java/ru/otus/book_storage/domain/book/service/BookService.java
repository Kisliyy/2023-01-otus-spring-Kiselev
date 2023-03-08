package ru.otus.book_storage.domain.book.service;

import ru.otus.book_storage.domain.book.model.Book;

import java.util.List;

public interface BookService {
    void save(Book book);

    List<Book> getAllBook();

    void deleteById(Long id);

    Book getById(Long id);

    void updateBook(Book book);
}
