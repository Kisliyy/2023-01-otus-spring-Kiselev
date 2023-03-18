package ru.otus.book_storage.dao.genre;

import org.springframework.stereotype.Component;
import ru.otus.book_storage.models.Genre;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = entityManager
                .createQuery(
                        "SELECT g FROM Genre as g",
                        Genre.class
                );
        return query.getResultList();
    }

    @Override
    public Optional<Genre> getById(long id) {
        if (id == 0) {
            return Optional.empty();
        }
        try {
            Genre genre = entityManager.find(Genre.class, id);
            return Optional.ofNullable(genre);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
