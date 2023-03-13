package ru.otus.book_storage.dao.author;

import org.springframework.stereotype.Repository;
import ru.otus.book_storage.models.Author;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            entityManager.persist(author);
            return author;
        }
        return entityManager.merge(author);
    }

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
    public Optional<Author> findByFirstNameAndLastName(String firstName, String lastName) {
        TypedQuery<Author> query = entityManager
                .createQuery(
                        "SELECT a FROM Author as a WHERE a.firstName = :firstName and a.lastName = :lastName",
                        Author.class
                );
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
