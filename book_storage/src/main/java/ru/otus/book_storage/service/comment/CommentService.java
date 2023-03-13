package ru.otus.book_storage.service.comment;

import ru.otus.book_storage.dto.CommentDto;
import ru.otus.book_storage.models.Comment;

import java.util.List;

public interface CommentService {
    Comment saveComment(String text, long bookId);

    CommentDto findById(long id);

    void deleteById(long id);

    List<CommentDto> findByBookId(long bookId);

    void updateComment(Comment comment);
}
