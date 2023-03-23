package ru.otus.book_storage.service.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.book_storage.dao.author.AuthorDao;
import ru.otus.book_storage.exceptions.NotFoundException;
import ru.otus.book_storage.models.Author;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;


    @Override
    public List<Author> getAll() {
        return authorDao.findAll();
    }

    @Override
    public Author findById(Long id) {
        return authorDao
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found by id: " + id));
    }
}
