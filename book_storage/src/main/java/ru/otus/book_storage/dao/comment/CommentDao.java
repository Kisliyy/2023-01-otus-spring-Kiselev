package ru.otus.book_storage.dao.comment;

import ru.otus.book_storage.models.Comment;

import java.util.Optional;

public interface CommentDao {
    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    void deleteById(long id);
}
