package ru.otus.book_storage.dao.comment;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.book_storage.models.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
}
