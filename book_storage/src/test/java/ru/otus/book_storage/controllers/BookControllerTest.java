package ru.otus.book_storage.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.book_storage.dto.CreateBookDto;
import ru.otus.book_storage.dto.UpdateBookDto;
import ru.otus.book_storage.models.Author;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.models.Genre;
import ru.otus.book_storage.service.author.AuthorService;
import ru.otus.book_storage.service.book.BookService;
import ru.otus.book_storage.service.genre.GenreService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@WebMvcTest(value = BookController.class)
class BookControllerTest {
    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;

    @Autowired
    private MockMvc mockMvc;

    private Author author;
    private Genre genre;
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
        this.author = new Author(authorId, firstName, lastName);
        this.genre = new Genre(genreId, genreName);
        this.book = Book.builder()
                .id(bookId)
                .title(title)
                .author(author)
                .genre(genre)
                .comments(Collections.emptyList())
                .build();
    }

    @Test
    void allBookVerifyNamePageTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/book/all"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("allBook"));
    }

    @Test
    void allBookVerifyContainsBookTest() throws Exception {

        when(bookService.getAllBook())
                .thenReturn(List.of(book));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/book/all"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(title)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(String.valueOf(bookId))))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(genreName)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(firstName)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(lastName)))
                .andReturn();

        verify(bookService, times(1)).getAllBook();
    }

    @Test
    void editBookContainsTest() throws Exception {
        long pushkinId = 123;
        String aleks = "Aleks";
        String pushkin = "pushkin";
        Author pushkinAuthor = new Author(pushkinId, aleks, pushkin);

        long genreAbstractId = 12354;
        String genreAbstractName = "genreAbstractName";
        Genre genreAbstract = new Genre(genreAbstractId, genreAbstractName);

        when(bookService.getById(bookId))
                .thenReturn(book);
        when(genreService.getAll())
                .thenReturn(List.of(genre, genreAbstract));
        when(authorService.getAll())
                .thenReturn(List.of(author, pushkinAuthor));


        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/book/edit").param("id", String.valueOf(bookId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("editBook"));
    }

    @Test
    void editBookVerifyContainsBookAndAllGenresAndAllAuthorsTest() throws Exception {
        long pushkinId = 123;
        String aleks = "Aleks";
        String pushkin = "pushkin";
        Author pushkinAuthor = new Author(pushkinId, aleks, pushkin);

        long genreAbstractId = 12354;
        String genreAbstractName = "genreAbstractName";
        Genre genreAbstract = new Genre(genreAbstractId, genreAbstractName);

        when(bookService.getById(bookId))
                .thenReturn(book);
        when(genreService.getAll())
                .thenReturn(List.of(genre, genreAbstract));
        when(authorService.getAll())
                .thenReturn(List.of(author, pushkinAuthor));


        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/book/edit").param("id", String.valueOf(bookId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(title)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(String.valueOf(bookId))))
                .andExpect(MockMvcResultMatchers.xpath("//*[@name='genreId']/option").nodeCount(2))
                .andExpect(MockMvcResultMatchers.xpath("//*[@name='authorId']/option").nodeCount(2))
                .andReturn();

        verify(bookService, times(1)).getById(bookId);
        verify(genreService, times(1)).getAll();
        verify(authorService, times(1)).getAll();
    }

    @Test
    void saveBookSuccessfulTest() throws Exception {
        CreateBookDto createBookDto = new CreateBookDto(title, authorId, genreId);
        Book domainObject = createBookDto.toDomainObject();
        when(bookService.save(domainObject)).thenReturn(book);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/book/save")
                .param("title", title)
                .param("authorId", String.valueOf(authorId))
                .param("genreId", String.valueOf(genreId));

        this.mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/book/all"))
                .andReturn();

        verify(bookService, times(1)).save(domainObject);
    }

    @Test
    void createBookVerifyNamePageTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/book/create"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("createBook"));
    }

    @Test
    void createBookVerifyContainsBookAndAllGenresAndAllAuthorsTest() throws Exception {
        long pushkinId = 123;
        String aleks = "Aleks";
        String pushkin = "pushkin";
        Author pushkinAuthor = new Author(pushkinId, aleks, pushkin);

        long genreAbstractId = 12354;
        String genreAbstractName = "genreAbstractName";
        Genre genreAbstract = new Genre(genreAbstractId, genreAbstractName);

        when(genreService.getAll())
                .thenReturn(List.of(genre, genreAbstract));
        when(authorService.getAll())
                .thenReturn(List.of(author, pushkinAuthor));


        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/book/create").param("id", String.valueOf(bookId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(title)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(String.valueOf(bookId))))
                .andExpect(MockMvcResultMatchers.xpath("//*[@name='genreId']/option").nodeCount(2))
                .andExpect(MockMvcResultMatchers.xpath("//*[@name='authorId']/option").nodeCount(2))
                .andReturn();

        verify(genreService, times(1)).getAll();
        verify(authorService, times(1)).getAll();
    }

    @Test
    void updateBookSuccessfulTest() throws Exception {
        UpdateBookDto updateBookDto = new UpdateBookDto(bookId, title, authorId, genreId);
        doNothing()
                .when(bookService)
                .updateBook(updateBookDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/book/update")
                .param("id", String.valueOf(bookId))
                .param("title", title)
                .param("authorId", String.valueOf(authorId))
                .param("genreId", String.valueOf(genreId));

        this.mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/book/all"))
                .andReturn();

        verify(bookService, times(1)).updateBook(updateBookDto);
    }

    @Test
    void deleteBookSuccessfulTest() throws Exception {

        doNothing()
                .when(bookService)
                .deleteById(bookId);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/book/delete")
                .param("id", String.valueOf(bookId));

        this.mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/book/all"))
                .andReturn();

        verify(bookService, times(1)).deleteById(bookId);
    }

}