package ru.otus.book_storage.dao.comment;

import org.springframework.stereotype.Repository;
import ru.otus.book_storage.dto.CommentDto;
import ru.otus.book_storage.models.Comment;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDaoJpa implements CommentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            entityManager.persist(comment);
            return comment;
        }
        return entityManager.merge(comment);
    }

    @Override
    public Optional<CommentDto> findById(long id) {
        if (id == 0) {
            return Optional.empty();
        }
        TypedQuery<CommentDto> query = entityManager
                .createQuery(
                        "SELECT new ru.otus.book_storage.dto.CommentDto(c.id, c.text) FROM Comment as c WHERE c.id = :id",
                        CommentDto.class
                );
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(long id) {
        Query query = entityManager.createQuery("DELETE FROM Comment WHERE id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<CommentDto> findByBookId(long bookId) {
        TypedQuery<CommentDto> query = entityManager
                .createQuery(
                        "SELECT new ru.otus.book_storage.dto.CommentDto(c.id, c.text) FROM Comment as c WHERE c.book.id = :book_id",
                        CommentDto.class
                );
        query.setParameter("book_id", bookId);
        return query.getResultList();
    }

    @Override
    public void updateComment(Comment comment) {
        Query query = entityManager
                .createQuery("UPDATE Comment SET text = :text WHERE id = :id");
        query.setParameter("id", comment.getId());
        query.setParameter("text", comment.getText());
        query.executeUpdate();
    }
}
