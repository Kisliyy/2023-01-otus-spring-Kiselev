package ru.otus.book_storage.service.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.book_storage.dao.comment.CommentDao;
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
    private BookService bookService;
    @MockBean
    private CommentDao commentDao;

    @Autowired
    private CommentService commentService;

    private final long bookId = 23L;
    private final long commentId = 1L;
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
        when(commentDao.save(savedComment)).thenReturn(persistComment);

        Comment comment = commentService.saveComment(textComment, bookId);

        assertEquals(commentId, comment.getId());
        assertEquals(textComment, comment.getText());

        verify(bookService, times(1)).getById(bookId);
        verify(commentDao, times(1)).save(savedComment);
    }


    @Test
    void findByIdReturnCommentTest() {
        Comment findComment = Comment.builder()
                .id(commentId)
                .text(textComment)
                .build();
        when(commentDao.findById(commentId)).thenReturn(Optional.of(findComment));

        Comment comment = commentService.findById(commentId);
        assertEquals(commentId, comment.getId());
        assertEquals(textComment, comment.getText());
        verify(commentDao, times(1)).findById(commentId);
    }

    @Test
    void findByIdReturnNotFoundExceptionTest() {
        when(commentDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> commentService.findById(anyLong()));
        verify(commentDao, times(1)).findById(anyLong());
    }

    @Test
    void deleteByIdCommentTest() {
        doNothing()
                .when(commentDao)
                .deleteById(commentId);

        commentService.deleteById(commentId);

        verify(commentDao, times(1)).deleteById(commentId);
    }

    @Test
    void getCommentsByBookIdTest() {
        List<Comment> commentsByBookId = List.of(
                new Comment(),
                new Comment(),
                new Comment()
        );
        Book findBook = Book.builder()
                .id(bookId)
                .comments(commentsByBookId)
                .build();
        when(bookService.getById(bookId)).thenReturn(findBook);
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

        when(commentDao.findById(commentId)).thenReturn(Optional.of(updatedComment));
        updatedComment.setText(newTextComment);
        when(commentDao.save(updatedComment)).thenReturn(updatedComment);
        commentService.updateComment(updatedCommentDto);
        verify(commentDao, times(1)).findById(commentId);
        verify(commentDao, times(1)).save(updatedComment);
    }
}