package ru.otus.book_storage.dao.book;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.book_storage.models.Book;

public interface BookRepository extends MongoRepository<Book, String> {

}
