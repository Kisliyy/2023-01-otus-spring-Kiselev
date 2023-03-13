package ru.otus.book_storage.command.comment;

public interface CommentCommand {
    String addComment(long bookId, String text);

    String getComment(long commentId);

    String getCommentByBook(long bookId);

    String deleteComment(long commentId);

    String updateComment(long commentId, String text);
}
