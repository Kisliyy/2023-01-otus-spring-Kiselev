package ru.otus.book_storage.dao.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.models.Comment;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(CommentDaoJpa.class)
class CommentDaoJpaTest {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void saveNewCommentTest() {
        Long existBookId = 1L;
        Book existBook = testEntityManager.find(Book.class, existBookId);

        String textComment = "testing comment";
        Comment newComment = Comment.builder()
                .text(textComment)
                .book(existBook)
                .build();

        Comment actualComment = commentDao.save(newComment);
        Comment expectedComment = testEntityManager.persist(newComment);
        assertEquals(expectedComment, actualComment);
        assertNotNull(actualComment.getId());
        assertNotNull(actualComment.getBook());
        assertEquals(textComment, actualComment.getText());
    }

    @Test
    @Sql(value = "/data/comment-data-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/data/comment-data-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findByIdReturnCommentTest() {
        long existCommentId = 1L;
        Optional<Comment> byId = commentDao.findById(existCommentId);
        Comment comment = testEntityManager.find(Comment.class, existCommentId);
        assertFalse(byId.isEmpty());
        Comment commentDto = byId.get();
        assertEquals(comment.getId(), commentDto.getId());
        assertEquals(comment.getText(), commentDto.getText());
    }

    @Test
    void findByIdReturnOptionalEmptyTest() {
        long commentId = 0;
        Optional<Comment> findComment = commentDao.findById(commentId);
        assertTrue(findComment.isEmpty());
    }

    @Test
    @Sql(value = "/data/comment-data-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/data/comment-data-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteByIdCommentSuccessfulTest() {
        long existCommentId = 1L;
        commentDao.deleteById(existCommentId);
        Comment comment = testEntityManager.find(Comment.class, existCommentId);
        assertNull(comment);
    }

}