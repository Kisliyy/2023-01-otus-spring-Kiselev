package ru.otus.book_storage.dao.genre;

import org.springframework.stereotype.Repository;
import ru.otus.book_storage.models.Genre;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
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
    public Optional<Genre> getByGenre(String genre) {
        TypedQuery<Genre> query = entityManager
                .createQuery(
                        "SELECT g FROM Genre as g WHERE g.genre = :genre",
                        Genre.class
                );
        query.setParameter("genre", genre);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == null) {
            entityManager.persist(genre);
            return genre;
        }
        return entityManager.merge(genre);
    }
}
