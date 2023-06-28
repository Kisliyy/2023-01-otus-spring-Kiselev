package ru.otus.book_storage.controllers;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @CircuitBreaker(name = "PageController_circuitbreaker", fallbackMethod = "fallbackPage")
    @GetMapping(value = "/")
    public String allBooks() {
        return "allBook";
    }

    @CircuitBreaker(name = "PageController_circuitbreaker", fallbackMethod = "fallbackPage")
    @GetMapping(value = "/add")
    public String addBook() {
        return "createBook";
    }

    @CircuitBreaker(name = "PageController_circuitbreaker", fallbackMethod = "fallbackPage")
    @GetMapping(value = "/edit")
    public String editBook(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("bookId", id);
        return "editBook";
    }

    @GetMapping(value = "/fallback")
    public String fallbackPage() {
        return "fallback";
    }
}
