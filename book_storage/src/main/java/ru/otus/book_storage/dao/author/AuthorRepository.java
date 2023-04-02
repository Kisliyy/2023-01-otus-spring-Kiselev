package ru.otus.book_storage.dao.author;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.book_storage.models.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
