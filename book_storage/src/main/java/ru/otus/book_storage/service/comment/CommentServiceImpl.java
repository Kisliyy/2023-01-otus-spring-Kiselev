package ru.otus.book_storage.service.comment;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.otus.book_storage.dao.comment.CommentRepository;
import ru.otus.book_storage.dto.CommentResponseDto;
import ru.otus.book_storage.dto.CommentUpdateDto;
import ru.otus.book_storage.exceptions.NotFoundException;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.models.Comment;
import ru.otus.book_storage.service.book.BookService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BookService bookService;
    private final CommentRepository commentRepository;

    @Override
    @CircuitBreaker(name = "CommentService_saveComment", fallbackMethod = "fallbackSaveComment")
    public Comment saveComment(String text, long bookId) {
        Book bookById = bookService.getById(bookId);
        Comment savedComment = Comment.builder()
                .text(text)
                .book(bookById)
                .build();
        return commentRepository.save(savedComment);
    }

    @Override
    public Comment findById(long id) {
        return commentRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Comment not found by id: " + id));
    }

    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findByBookId(long bookId) {
        Book bookById = bookService.getById(bookId);
        return bookById
                .getComments()
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
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
