package ru.otus.book_storage.dao.comment;

import org.springframework.stereotype.Component;
import ru.otus.book_storage.models.Comment;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Component
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
    public Optional<Comment> findById(long id) {
        if (id == 0) {
            return Optional.empty();
        }
        try {
            Comment comment = entityManager.find(Comment.class, id);
            return Optional.ofNullable(comment);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(long id) {
        Optional<Comment> commentById = findById(id);
        if (commentById.isPresent()) {
            Comment comment = commentById.get();
            entityManager.remove(comment);
        }
    }
}
