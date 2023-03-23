package ru.otus.book_storage.dao.book;

import org.springframework.stereotype.Component;
import ru.otus.book_storage.models.Book;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
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
        Optional<Book> byId = getById(id);
        if (byId.isPresent()) {
            Book book = byId.get();
            entityManager.remove(book);
        }
    }

    @Override
    public Optional<Book> getById(long id) {
        if (id == 0) {
            return Optional.empty();
        }
        try {
            Book findBook = entityManager.find(Book.class, id);
            return Optional.ofNullable(findBook);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = entityManager
                .createQuery("SELECT b FROM Book b", Book.class);
        return query.getResultList();
    }

}
