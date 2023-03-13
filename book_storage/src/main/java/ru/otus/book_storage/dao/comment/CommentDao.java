package ru.otus.book_storage.dao.comment;

import ru.otus.book_storage.dto.CommentDto;
import ru.otus.book_storage.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {
    Comment save(Comment comment);

    Optional<CommentDto> findById(long id);

    void deleteById(long id);

    List<CommentDto> findByBookId(long bookId);

    void updateComment(Comment comment);
}
