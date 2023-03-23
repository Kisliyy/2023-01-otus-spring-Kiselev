package ru.otus.book_storage.command.book;

public interface BookCommand {
    String addBook(String title,
                   Long authorId,
                   Long genreId);

    String getAllBook();

    String deleteBook(Long id);

    String getBook(Long id);

    String updateBook(Long bookId,
                      String title,
                      Long authorId,
                      Long genreId);
}
