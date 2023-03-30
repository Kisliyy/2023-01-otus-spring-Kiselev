package ru.otus.book_storage.command.comment;

public interface CommentCommand {
    String addComment(String bookId, String text);

    String getComment(String commentId);

    String getCommentsByBook(String bookId);

    String deleteComment(String commentId);

    String updateComment(String commentId, String text);
}
