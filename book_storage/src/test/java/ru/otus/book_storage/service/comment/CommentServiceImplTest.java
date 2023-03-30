package ru.otus.book_storage.service.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.book_storage.dao.comment.CommentRepository;
import ru.otus.book_storage.dto.CommentUpdateDto;
import ru.otus.book_storage.exceptions.NotFoundException;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.models.Comment;
import ru.otus.book_storage.service.book.BookService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CommentServiceImpl.class)
class CommentServiceImplTest {
    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private BookService bookService;

    @Autowired
    private CommentService commentService;

    private final String bookId = "bookId";
    private final String commentId = "commentId";
    private final String textComment = "textComment";

    @Test
    void saveCommentSuccessfulTest() {

        Book findBook = Book.builder()
                .id(bookId)
                .build();
        Comment savedComment = Comment.builder()
                .text(textComment)
                .book(findBook)
                .build();
        Comment persistComment = Comment.builder()
                .id(commentId)
                .text(textComment)
                .book(findBook)
                .build();
        when(bookService.getById(bookId)).thenReturn(findBook);
        when(commentRepository.save(savedComment)).thenReturn(persistComment);

        Comment comment = commentService.saveComment(textComment, bookId);

        assertEquals(commentId, comment.getId());
        assertEquals(textComment, comment.getText());

        verify(bookService, times(1)).getById(bookId);
        verify(commentRepository, times(1)).save(savedComment);
    }


    @Test
    void findByIdReturnCommentTest() {
        Comment findComment = Comment.builder()
                .id(commentId)
                .text(textComment)
                .build();
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(findComment));

        Comment comment = commentService.findById(commentId);
        assertEquals(commentId, comment.getId());
        assertEquals(textComment, comment.getText());
        verify(commentRepository, times(1)).findById(commentId);
    }

    @Test
    void findByIdReturnNotFoundExceptionTest() {
        when(commentRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> commentService.findById(anyString()));
        verify(commentRepository, times(1)).findById(anyString());
    }

    @Test
    void deleteByIdCommentTest() {
        doNothing()
                .when(commentRepository)
                .deleteById(commentId);

        commentService.deleteById(commentId);

        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    void getCommentsByBookIdTest() {
        List<Comment> commentsByBookId = List.of(
                new Comment(),
                new Comment(),
                new Comment()
        );
        when(commentService.findByBookId(bookId)).thenReturn(commentsByBookId);
        List<Comment> byBookId = commentService.findByBookId(bookId);

        assertNotNull(byBookId);
        assertFalse(byBookId.isEmpty());
        assertEquals(3, byBookId.size());
    }

    @Test
    void updateCommentSuccessfulTest() {
        String newTextComment = "newTextComment";
        CommentUpdateDto updatedCommentDto = new CommentUpdateDto(commentId, newTextComment);

        Comment updatedComment = Comment
                .builder()
                .id(commentId)
                .text(textComment)
                .build();

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(updatedComment));
        updatedComment.setText(newTextComment);
        when(commentRepository.save(updatedComment)).thenReturn(updatedComment);
        commentService.updateComment(updatedCommentDto);
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, times(1)).save(updatedComment);
    }
}