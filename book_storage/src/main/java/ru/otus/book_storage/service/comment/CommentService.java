package ru.otus.book_storage.service.comment;

import ru.otus.book_storage.dto.CommentUpdateDto;
import ru.otus.book_storage.models.Comment;

import java.util.List;

public interface CommentService {
    Comment saveComment(String text, long bookId);

    Comment findById(long id);

    void deleteById(long id);

    List<Comment> findByBookId(long bookId);

    void updateComment(CommentUpdateDto updateComment);
}
