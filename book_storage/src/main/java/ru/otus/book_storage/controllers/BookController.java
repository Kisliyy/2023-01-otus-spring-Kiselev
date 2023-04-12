package ru.otus.book_storage.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.book_storage.dto.CreateBookDto;
import ru.otus.book_storage.dto.UpdateBookDto;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.service.author.AuthorService;
import ru.otus.book_storage.service.book.BookService;
import ru.otus.book_storage.service.genre.GenreService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;


    @GetMapping(value = "/all")
    public String allBookPage(Model model) {
        List<Book> allBook = bookService.getAllBook();
        model.addAttribute("books", allBook);
        return "allBook";
    }

    @GetMapping("/edit")
    public String editBookPage(@RequestParam("id") Long id, Model model) {
        Book findBook = bookService.getById(id);
        model.addAttribute("book", findBook);
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("authors", authorService.getAll());
        return "editBook";
    }

    @PostMapping(value = "/save")
    public String saveBook(@Valid @ModelAttribute("book") CreateBookDto createBookDto,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", genreService.getAll());
            model.addAttribute("authors", authorService.getAll());
            return "createBook";
        }
        Book newBook = createBookDto.toDomainObject();
        bookService.save(newBook);
        return "redirect:/book/all";
    }

    @GetMapping(value = "/create")
    public String createBookPage(Model model) {
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("book", new CreateBookDto());
        return "createBook";
    }

    @PostMapping("/update")
    public String updateBook(@Valid @ModelAttribute("book") UpdateBookDto updateBookDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", genreService.getAll());
            model.addAttribute("authors", authorService.getAll());
            return "editBook";
        }
        bookService.updateBook(updateBookDto);
        return "redirect:/book/all";
    }

    @PostMapping(value = "/delete")
    public String deleteBook(@RequestParam(name = "id") Long id) {
        bookService.deleteById(id);
        return "redirect:/book/all";
    }


}
