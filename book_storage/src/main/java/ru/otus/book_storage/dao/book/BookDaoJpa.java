package ru.otus.book_storage.dao.book;

import org.springframework.stereotype.Repository;
import ru.otus.book_storage.models.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            entityManager.persist(book);
            return book;
        }
        return entityManager.merge(book);
    }

    @Override
    public void deleteById(long id) {
        Query query = entityManager
                .createQuery("DELETE FROM Book as b WHERE b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Optional<Book> getById(long id) {
        if (id == 0) {
            return Optional.empty();
        }
        TypedQuery<Book> query = entityManager
                .createQuery("SELECT b FROM Book b JOIN FETCH b.genre JOIN FETCH b.author WHERE b.id = :id", Book.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = entityManager
                .createQuery("SELECT b FROM Book b JOIN FETCH b.genre JOIN FETCH b.author", Book.class);
        return query.getResultList();
    }

    @Override
    public void update(Book book) {
        Query query = entityManager.createQuery("UPDATE Book SET title = :title, genre =: genre, author =: author WHERE id = :id");
        query.setParameter("title", book.getTitle());
        query.setParameter("genre", book.getGenre());
        query.setParameter("author", book.getAuthor());
        query.setParameter("id", book.getId());
        query.executeUpdate();
    }
}
