package ru.otus.book_storage.dao.comment;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.book_storage.models.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String>, CommentCustomRepository {
    List<Comment> findByBookId(String bookId);

}
