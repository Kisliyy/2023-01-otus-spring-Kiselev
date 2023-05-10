package ru.otus.book_storage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @GetMapping(value = "/")
    public String allBooks() {
        return "allBook";
    }

    @GetMapping(value = "/add")
    public String addBook() {
        return "createBook";
    }

    @GetMapping(value = "/edit")
    public String editBook(@RequestParam(name = "id") String id, Model model) {
        model.addAttribute("bookId", id);
        return "editBook";
    }
}
