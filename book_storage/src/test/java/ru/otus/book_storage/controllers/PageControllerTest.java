package ru.otus.book_storage.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(value = PageController.class)
class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void allBookVerifyNamePageTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("allBook"));
    }


    @Test
    void createBookVerifyNamePageTest() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/add"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("createBook"));
    }

    @Test
    void editBookVerifyNamePageTest() throws Exception {
        long bookId = 123;

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/edit").param("id", String.valueOf(bookId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("editBook"));
    }
}