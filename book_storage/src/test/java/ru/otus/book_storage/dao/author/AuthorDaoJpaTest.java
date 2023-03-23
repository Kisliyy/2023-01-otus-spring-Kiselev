package ru.otus.book_storage.dao.author;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.book_storage.models.Author;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AuthorDaoJpa.class)
@ActiveProfiles("test")
class AuthorDaoJpaTest {

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    void getAllAuthorsTest() {
        List<Author> allAuthors = authorDao.getAll();
        assertNotNull(allAuthors);
        assertFalse(allAuthors.isEmpty());
        assertTrue(allAuthors.stream().allMatch(Objects::nonNull));
        assertEquals(3, allAuthors.size());
    }


    @Test
    void findByIdReturnAuthorTest() {
        Long existAuthorId = 1L;
        Optional<Author> findAuthor = authorDao.findById(existAuthorId);
        assertFalse(findAuthor.isEmpty());
        Author author = findAuthor.get();
        assertNotNull(author.getId());
        assertEquals(existAuthorId, author.getId());
    }

    @Test
    void findByIdReturnsCorrectEntityAuthorTest() {
        Long existAuthorId = 1L;
        Optional<Author> findAuthor = authorDao.findById(existAuthorId);
        assertFalse(findAuthor.isEmpty());
        Author actualAuthor = findAuthor.get();
        Author expectedAuthor = testEntityManager.find(Author.class, existAuthorId);
        assertEquals(expectedAuthor, actualAuthor);
    }


    @Test
    void findByIdAReturnOptionalEmptyTest() {
        Long nonExistentId = 50L;
        Optional<Author> findAuthor = authorDao.findById(nonExistentId);
        assertTrue(findAuthor.isEmpty());
    }
}