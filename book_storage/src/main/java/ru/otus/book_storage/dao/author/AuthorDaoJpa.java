package ru.otus.book_storage.dao.author;

import org.springframework.stereotype.Component;
import ru.otus.book_storage.models.Author;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = entityManager
                .createQuery(
                        "SELECT a FROM Author as a",
                        Author.class
                );
        return query.getResultList();
    }

    @Override
    public Optional<Author> findById(long id) {
        if (id == 0) {
            return Optional.empty();
        }
        try {
            Author author = entityManager.find(Author.class, id);
            return Optional.ofNullable(author);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
