package ru.otus.book_storage.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.otus.book_storage.dao.comment.CommentDao;
import ru.otus.book_storage.dto.CommentUpdateDto;
import ru.otus.book_storage.exceptions.NotFoundException;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.models.Comment;
import ru.otus.book_storage.service.book.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BookService bookService;
    private final CommentDao commentDao;

    @Override
    @Transactional
    public Comment saveComment(String text, long bookId) {
        Book bookById = bookService.getById(bookId);
        Comment savedComment = Comment.builder()
                .text(text)
                .book(bookById)
                .build();
        return commentDao.save(savedComment);
    }

    @Override
    public Comment findById(long id) {
        return commentDao
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Comment not found by id: " + id));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentDao.deleteById(id);
    }

    @Override
    public List<Comment> findByBookId(long bookId) {
        Book bookById = bookService.getById(bookId);
        return bookById.getComments();
    }

    @Override
    @Transactional
    public void updateComment(CommentUpdateDto updateCommentDto) {
        String text = updateCommentDto.getText();
        if (StringUtils.hasText(text)) {
            Comment updateComment = findById(updateCommentDto.getId());
            updateComment.setText(updateComment.getText());
            commentDao.save(updateComment);
        }
    }
}
