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
    void saveNewAuthorSuccessful() {
        String firstName = "Firstname";
        String lastName = "Lastname";
        Author newAuthor = Author.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        Author save = authorDao.save(newAuthor);
        Author persist = testEntityManager.persist(newAuthor);
        assertNotNull(save.getId());
        assertEquals(firstName, save.getFirstName());
        assertEquals(lastName, save.getLastName());
        assertEquals(save, persist);
    }

    @Test
    void saveExistAuthorSuccessful() {
        String firstName = "Firstname";
        String newFirstName = "NewFirstname";
        String lastName = "Lastname";
        String newLastName = "NewLastname";
        Author newAuthor = Author.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        Author persist = testEntityManager.persist(newAuthor);
        Author existAuthor = Author.builder()
                .id(persist.getId())
                .firstName(newFirstName)
                .lastName(newLastName)
                .build();
        Author saveAuthor = authorDao.save(existAuthor);
        assertNotNull(saveAuthor.getId());
        assertEquals(persist.getId(), saveAuthor.getId());
        assertEquals(newFirstName, saveAuthor.getFirstName());
        assertEquals(newLastName, saveAuthor.getLastName());
    }

    @Test
    void getAllAuthorsTest() {
        List<Author> allAuthors = authorDao.getAll();
        assertNotNull(allAuthors);
        assertFalse(allAuthors.isEmpty());
        assertTrue(allAuthors.stream().allMatch(Objects::nonNull));
        assertEquals(3, allAuthors.size());
    }

    @Test
    void findByFirstNameAndLastNameReturnAuthorTest() {
        String existAuthorFirstName = "Aleksandr";
        String existAuthorLastName = "Pushkin";
        Optional<Author> byFirstNameAndLastName = authorDao.findByFirstNameAndLastName(existAuthorFirstName, existAuthorLastName);
        assertFalse(byFirstNameAndLastName.isEmpty());
        Author author = byFirstNameAndLastName.get();
        assertNotNull(author.getId());
        assertEquals(existAuthorFirstName, author.getFirstName());
        assertEquals(existAuthorLastName, author.getLastName());
    }
}