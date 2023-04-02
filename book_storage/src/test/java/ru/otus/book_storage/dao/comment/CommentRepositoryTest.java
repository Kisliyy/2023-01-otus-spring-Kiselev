package ru.otus.book_storage.dao.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.book_storage.models.Comment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@ComponentScan({"ru.otus.book_storage.dao", "ru.otus.book_storage.config"})
@ActiveProfiles("test")
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void shouldReturnCorrectCommentsList() {
        List<Comment> comments = commentRepository.findAll();
        assertNotNull(comments);
        assertEquals(3, comments.size());
    }
}