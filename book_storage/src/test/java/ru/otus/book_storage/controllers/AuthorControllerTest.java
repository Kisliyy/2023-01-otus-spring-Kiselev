package ru.otus.book_storage.controllers;

import org.hamcrest.Matchers;
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
import ru.otus.book_storage.models.Author;
import ru.otus.book_storage.service.author.AuthorService;

import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(value = {AuthorController.class, SecurityConfig.class})
class AuthorControllerTest {

    @MockBean
    private AuthorService authorService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(
            username = "user",
            roles = "USER"
    )
    void shouldReturnCorrectListAuthorsDto() throws Exception {
        final Long authorId = 1000L;
        final String firstName = "firstName";
        final String lastName = "lastName";
        Author author = new Author(authorId, firstName, lastName);
        List<Author> authorList = List.of(author);

        when(authorService.getAll()).thenReturn(authorList);

        mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/authors")
                                .contentType(MediaType.ALL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(authorList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is(firstName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is(lastName)));

        verify(authorService, times(1)).getAll();
    }


    @Test
    void shouldReturnIsRedirectionIfUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/authors")
                                .contentType(MediaType.ALL)
                )
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .is3xxRedirection()
                );
    }
}