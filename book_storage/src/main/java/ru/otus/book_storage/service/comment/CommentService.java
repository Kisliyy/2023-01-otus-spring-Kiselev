package ru.otus.book_storage.service.comment;

import ru.otus.book_storage.dto.CommentUpdateDto;
import ru.otus.book_storage.models.Comment;

import java.util.List;

public interface CommentService {
    Comment saveComment(String text, String bookId);

    Comment findById(String id);

    void deleteById(String id);

    List<Comment> findByBookId(String bookId);

    void updateComment(CommentUpdateDto updateComment);
}
