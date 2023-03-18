package ru.otus.book_storage.command.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.book_storage.dto.CommentUpdateDto;
import ru.otus.book_storage.models.Comment;
import ru.otus.book_storage.service.comment.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class CommentCommandShell implements CommentCommand {

    private final CommentService commentService;

    @Override
    @ShellMethod(value = "Add comment", key = "add c")
    public String addComment(@ShellOption(help = "Book id") long bookId,
                             @ShellOption(help = "Text comment") String text) {
        Comment comment = commentService.saveComment(text, bookId);
        return comment.toString();
    }

    @Override
    @ShellMethod(value = "Get comment by comment id", key = "get c")
    public String getComment(@ShellOption(help = "Comment id") long commentId) {
        Comment byId = commentService.findById(commentId);
        return byId.toString();
    }

    @Override
    @ShellMethod(value = "Get comment by book id", key = "get c by b")
    @Transactional(readOnly = true)
    public String getCommentsByBook(@ShellOption(help = "Book id") long bookId) {
        List<Comment> byBookId = commentService.findByBookId(bookId);
        return byBookId
                .stream()
                .map(Comment::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    @ShellMethod(value = "Delete comment by id", key = "del c")
    public String deleteComment(@ShellOption(help = "Comment id") long commentId) {
        commentService.deleteById(commentId);
        return "Comment successfully deleted";
    }

    @Override
    @ShellMethod(value = "Update comment", key = "upt c")
    public String updateComment(@ShellOption(help = "Comment id") long commentId, @ShellOption(help = "Update text comment") String text) {
        CommentUpdateDto commentUpdateDto = new CommentUpdateDto(commentId, text);
        commentService.updateComment(commentUpdateDto);
        return "Comment successfully updated";
    }
}
