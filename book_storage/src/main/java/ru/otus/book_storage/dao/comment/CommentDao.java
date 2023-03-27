package ru.otus.book_storage.dao.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.book_storage.models.Comment;

public interface CommentDao extends JpaRepository<Comment, Long> {
}
