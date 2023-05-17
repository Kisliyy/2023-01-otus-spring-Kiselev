package ru.otus.book_storage.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.book_storage.config.SecurityConfig;
import ru.otus.book_storage.dto.CreateBookDto;
import ru.otus.book_storage.dto.UpdateBookDto;
import ru.otus.book_storage.exceptions.NotFoundException;
import ru.otus.book_storage.models.Author;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.models.Genre;
import ru.otus.book_storage.service.book.BookService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(value = {BookController.class, SecurityConfig.class})
class BookControllerTest {
    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;
    private Book book;

    final long authorId = 1000;
    final long genreId = 1000;
    final long bookId = 1000;

    private final String title = "title";
    private final String genreName = "genreName";
    private final String firstName = "firstName";
    private final String lastName = "lastName";


    @BeforeEach
    void init() {
        Author author = new Author(authorId, firstName, lastName);
        Genre genre = new Genre(genreId, genreName);
        this.book = Book.builder()
                .id(bookId)
                .title(title)
                .author(author)
                .genre(genre)
                .comments(Collections.emptyList())
                .build();
    }


    @Test
    @WithMockUser(
            username = "user"
    )
    void shouldReturnCorrectListBookDto() throws Exception {
        List<Book> bookList = List.of(book);
        when(bookService.getAllBook()).thenReturn(bookList);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.ALL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(bookList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is(title)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genreName", Matchers.is(genreName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstNameAuthor", Matchers.is(firstName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastNameAuthor", Matchers.is(lastName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].countComment", Matchers.is(0)));


        verify(bookService, times(1)).getAllBook();
    }

    @Test
    @WithMockUser(
            username = "user"
    )
    void shouldReturnFindBookDto() throws Exception {
        when(bookService.getById(bookId)).thenReturn(book);
        final String url = "/books/" + bookId;
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.ALL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is(title)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genreName", Matchers.is(genreName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstNameAuthor", Matchers.is(firstName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastNameAuthor", Matchers.is(lastName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.countComment", Matchers.is(0)));

        verify(bookService, times(1)).getById(bookId);
    }

    @Test
    @WithMockUser(
            username = "user"
    )
    void shouldReturnNotFoundByFoundBookException() throws Exception {
        String message = "Book not found!";
        when(bookService.getById(any())).thenThrow(new NotFoundException(message));

        final String url = "/books/" + bookId;
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.ALL))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(message)));

        verify(bookService, times(1)).getById(any());
    }

    @Test
    @WithMockUser(
            username = "user"
    )
    void shouldAddNewBookTest() throws Exception {
        CreateBookDto createBookDto = new CreateBookDto(title, authorId, genreId);
        Book toDomainObject = createBookDto.toDomainObject();
        when(bookService.save(toDomainObject)).thenReturn(book);

        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectToJson(createBookDto))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is(title)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genreName", Matchers.is(genreName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstNameAuthor", Matchers.is(firstName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastNameAuthor", Matchers.is(lastName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.countComment", Matchers.is(0)));

        verify(bookService, times(1)).save(toDomainObject);
    }

    @Test
    @WithMockUser(
            username = "user"
    )
    void shouldReturnExceptionByAddNewBookTest() throws Exception {
        CreateBookDto createBookDto = new CreateBookDto(null, authorId, genreId);
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectToJson(createBookDto))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.anyOf(
                                Matchers.is("The title of the book cannot be null"),
                                Matchers.is("The title of the book cannot be empty")
                        )
                ));
    }

    @Test
    @WithMockUser(
            username = "user"
    )
    void shouldDeleteBookTest() throws Exception {
        doNothing()
                .when(bookService)
                .deleteById(bookId);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/books")
                        .param("id", String.valueOf(bookId))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(
            username = "user"
    )
    void shouldUpdateBookTest() throws Exception {
        String newTitle = "newTitle";
        UpdateBookDto updateBookDto = new UpdateBookDto(bookId, newTitle, authorId, genreId);

        doNothing()
                .when(bookService)
                .updateBook(updateBookDto);

        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectToJson(updateBookDto))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(bookService, times(1)).updateBook(updateBookDto);
    }

    @Test
    @WithMockUser(
            username = "user"
    )
    void shouldReturnExceptionByUpdateBookTest() throws Exception {
        UpdateBookDto updateBookDto = new UpdateBookDto(null, null, authorId, genreId);
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectToJson(updateBookDto))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is("Id cannot be null!")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.anyOf(
                                Matchers.is("The title of the book cannot be empty!"),
                                Matchers.is("The title of the book cannot be null!")
                        )
                ));
    }

    private String objectToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    @Test
    void shouldReturnIsRedirectionIfUserIsNotAuthenticatedByRequestAllBook() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/books")
                                .contentType(MediaType.ALL)
                )
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .is3xxRedirection()
                );
    }

    @Test
    void shouldReturnIsRedirectionIfUserIsNotAuthenticatedByRequestFindByIdBook() throws Exception {
        final String url = "/books/" + bookId;
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get(url)
                                .contentType(MediaType.ALL)
                )
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .is3xxRedirection()
                );
    }

    @Test
    void shouldReturnIsRedirectionIfUserIsNotAuthenticatedByRequestAddBook() throws Exception {
        CreateBookDto createBookDto = new CreateBookDto(null, authorId, genreId);
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                                .content(objectToJson(createBookDto))
                )
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .is3xxRedirection()
                );
    }

    @Test
    void shouldReturnIsRedirectionIfUserIsNotAuthenticatedByRequestEditBook() throws Exception {
        UpdateBookDto updateBookDto = new UpdateBookDto(null, title, authorId, genreId);
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/books")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectToJson(updateBookDto))
                )
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .is3xxRedirection()
                );
    }

    @Test
    void shouldReturnIsRedirectionIfUserIsNotAuthenticatedByRequestDeleteBook() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/books")
                        .with(csrf())
                        .param("id", String.valueOf(bookId))
                )
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .is3xxRedirection()
                );
    }

}