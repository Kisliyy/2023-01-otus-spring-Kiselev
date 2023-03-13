package ru.otus.book_storage.service.author;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.book_storage.dao.author.AuthorDao;
import ru.otus.book_storage.models.Author;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AuthorServiceImpl.class)
class AuthorServiceImplTest {

    @MockBean
    private AuthorDao authorDao;

    @Autowired
    private AuthorService authorService;

    private final String firstName = "firstName";
    private final String lastName = "lastName";

    @Test
    void saveAuthorSuccessfulTest() {
        long authorId = 1;
        Author persistAuthor = Author.builder()
                .id(authorId)
                .lastName(lastName)
                .firstName(firstName)
                .build();
        Author savedAuthor = Author.builder()
                .lastName(lastName)
                .firstName(firstName)
                .build();

        when(authorDao.save(savedAuthor)).thenReturn(persistAuthor);

        Author author = authorService.save(savedAuthor);
        assertEquals(persistAuthor, author);
        verify(authorDao, times(1)).save(savedAuthor);
    }

    @Test
    void getAllAuthorsTest() {
        List<Author> authors = List.of(
                new Author(),
                new Author(),
                new Author()
        );

        when(authorDao.getAll()).thenReturn(authors);

        List<Author> allAuthors = authorService.getAll();
        assertNotNull(allAuthors);
        assertFalse(allAuthors.isEmpty());
        assertEquals(3, allAuthors.size());
        assertEquals(authors, allAuthors);
    }


    @Test
    void findByFirstNameAndLastNameReturnAuthorTest() {
        long authorId = 1;
        Author findAuthor = Author.builder()
                .id(authorId)
                .lastName(lastName)
                .firstName(firstName)
                .build();

        when(authorDao.findByFirstNameAndLastName(firstName, lastName)).thenReturn(Optional.of(findAuthor));

        Author author = authorService.findByFirstNameAndLastName(firstName, lastName);
        assertEquals(findAuthor, author);
        verify(authorDao, times(1)).findByFirstNameAndLastName(firstName, lastName);
    }

    @Test
    void findByFirstNameAndLastNameReturnNullTest() {
        when(authorDao.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.empty());
        Author author = authorService.findByFirstNameAndLastName(anyString(), anyString());
        assertNull(author);
        verify(authorDao, times(1)).findByFirstNameAndLastName(anyString(), anyString());
    }
}