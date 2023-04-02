package ru.otus.book_storage.dao.comment;

public interface CommentCustomRepository {
    void deleteCommentsByBookId(String id);
}
