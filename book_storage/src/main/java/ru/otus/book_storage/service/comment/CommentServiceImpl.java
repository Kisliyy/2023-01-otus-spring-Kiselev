package ru.otus.book_storage.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.book_storage.dao.comment.CommentDao;
import ru.otus.book_storage.dto.CommentDto;
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
    @Transactional(readOnly = true)
    public CommentDto findById(long id) {
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
    @Transactional(readOnly = true)
    public List<CommentDto> findByBookId(long bookId) {
        return commentDao.findByBookId(bookId);
    }

    @Override
    @Transactional
    public void updateComment(Comment comment) {
        commentDao.updateComment(comment);
    }
}
