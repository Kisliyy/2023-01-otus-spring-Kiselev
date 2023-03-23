package ru.otus.book_storage.service.author;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.book_storage.dao.author.AuthorDao;
import ru.otus.book_storage.exceptions.NotFoundException;
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
    void getAllAuthorsTest() {
        List<Author> authors = List.of(
                new Author(),
                new Author(),
                new Author()
        );

        when(authorDao.findAll()).thenReturn(authors);

        List<Author> allAuthors = authorService.getAll();
        assertNotNull(allAuthors);
        assertFalse(allAuthors.isEmpty());
        assertEquals(3, allAuthors.size());
        assertEquals(authors, allAuthors);
    }


    @Test
    void findByIdReturnAuthorTest() {
        long authorId = 1;
        Author findAuthor = Author.builder()
                .id(authorId)
                .lastName(lastName)
                .firstName(firstName)
                .build();

        when(authorDao.findById(authorId)).thenReturn(Optional.of(findAuthor));

        Author author = authorService.findById(authorId);
        assertEquals(findAuthor, author);
        verify(authorDao, times(1)).findById(authorId);
    }

    @Test
    void findByFirstNameAndLastNameReturnNullTest() {
        when(authorDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> authorService.findById(anyLong()));
        verify(authorDao, times(1)).findById(anyLong());
    }
}