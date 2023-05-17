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
import ru.otus.book_storage.models.Genre;
import ru.otus.book_storage.service.genre.GenreService;

import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(value = {GenreController.class, SecurityConfig.class})
class GenreControllerTest {

    @MockBean
    private GenreService genreService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(
            username = "user"
    )
    void shouldReturnCorrectListGenresDto() throws Exception {
        final Long genreId = 1000L;
        final String genreName = "genreName";
        Genre genre = new Genre(genreId, genreName);
        List<Genre> genreList = List.of(genre);

        when(genreService.getAll()).thenReturn(genreList);

        mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/genres")
                                .contentType(MediaType.ALL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(genreList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(genreName)));

        verify(genreService, times(1)).getAll();
    }

    @Test
    void shouldReturnIsRedirectionIfUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/genres")
                                .contentType(MediaType.ALL)
                )
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .is3xxRedirection()
                );
    }
}