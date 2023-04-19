package ru.otus.book_storage.service.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.book_storage.dao.author.AuthorRepository;
import ru.otus.book_storage.exceptions.NotFoundException;
import ru.otus.book_storage.models.Author;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;


    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(Long id) {
        return authorRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found by id: " + id));
    }
}
