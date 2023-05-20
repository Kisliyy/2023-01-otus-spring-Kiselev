package ru.otus.book_storage.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.book_storage.config.SecurityConfig;

@WebMvcTest(value = {PageController.class, SecurityConfig.class})
class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(
            username = "user",
            roles = "USER"
    )
    void allBookVerifyNamePageTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("allBook"));
    }


    @Test
    @WithMockUser(
            username = "user",
            roles = "USER"
    )
    void createBookVerifyNamePageTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/add"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("createBook"));
    }

    @Test
    @WithMockUser(
            username = "user",
            roles = "USER"
    )
    void editBookVerifyNamePageTest() throws Exception {
        long bookId = 123;

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/edit").param("id", String.valueOf(bookId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("editBook"));
    }


    @Test
    void shouldOnAllBookPageReturnIsRedirectionIfUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/")
                )
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .is3xxRedirection()
                );
    }

    @Test
    void shouldOnEditBookPageReturnIsRedirectionIfUserIsNotAuthenticated() throws Exception {
        long bookId = 123;
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/edit").param("id", String.valueOf(bookId))
                )
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .is3xxRedirection()
                );
    }

    @Test
    void shouldOnAddBookPageReturnIsRedirectionIfUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/add")
                )
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .is3xxRedirection()
                );
    }
}