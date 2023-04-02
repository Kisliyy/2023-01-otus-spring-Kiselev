package ru.otus.book_storage.command.book;

public interface BookCommand {
    String addBook(String title,
                   String authorId,
                   String genreId);

    String getAllBook();

    String deleteBook(String id);

    String getBook(String id);

    String updateBook(String bookId,
                      String title,
                      String authorId,
                      String genreId);
}
