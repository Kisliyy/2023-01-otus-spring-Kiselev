package ru.otus.book_storage.service.author;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.book_storage.dao.author.AuthorRepository;
import ru.otus.book_storage.exceptions.NotFoundException;
import ru.otus.book_storage.models.Author;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AuthorServiceImpl.class)
class AuthorServiceImplTest {

    @MockBean
    private AuthorRepository authorRepository;

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

        when(authorRepository.findAll()).thenReturn(authors);

        List<Author> allAuthors = authorService.getAll();
        assertNotNull(allAuthors);
        assertFalse(allAuthors.isEmpty());
        assertEquals(3, allAuthors.size());
        assertEquals(authors, allAuthors);
    }


    @Test
    void findByIdReturnAuthorTest() {
        String authorId = "authorId";
        Author findAuthor = Author.builder()
                .id(authorId)
                .lastName(lastName)
                .firstName(firstName)
                .build();

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(findAuthor));

        Author author = authorService.findById(authorId);
        assertEquals(findAuthor, author);
        verify(authorRepository, times(1)).findById(authorId);
    }

    @Test
    void findByFirstNameAndLastNameReturnNullTest() {
        when(authorRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> authorService.findById(anyString()));
        verify(authorRepository, times(1)).findById(anyString());
    }
}