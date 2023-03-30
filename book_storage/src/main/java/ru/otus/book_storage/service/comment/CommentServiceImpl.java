package ru.otus.book_storage.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.book_storage.dao.comment.CommentRepository;
import ru.otus.book_storage.dto.CommentUpdateDto;
import ru.otus.book_storage.exceptions.NotFoundException;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.models.Comment;
import ru.otus.book_storage.service.book.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookService bookService;

    @Override
    public Comment saveComment(String text, String bookId) {
        Book byId = bookService.getById(bookId);
        Comment savedComment = Comment.builder()
                .text(text)
                .book(byId)
                .build();
        return commentRepository.save(savedComment);
    }

    @Override
    public Comment findById(String id) {
        return commentRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Comment not found by id: " + id));
    }

    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> findByBookId(String bookId) {
        return commentRepository.findByBookId(bookId);
    }

    @Override
    public void updateComment(CommentUpdateDto updateCommentDto) {
        String text = updateCommentDto.getText();
        if (StringUtils.hasText(text)) {
            Comment updateComment = findById(updateCommentDto.getId());
            updateComment.setText(updateComment.getText());
            commentRepository.save(updateComment);
        }
    }
}
