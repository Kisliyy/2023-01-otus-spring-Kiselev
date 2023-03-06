package ru.otus.book_storage.command.book;

public interface BookCommand {
    String addBook(String title,
                   String firstName,
                   String lastName,
                   String genre);

    String getAllBook();

    String deleteBook(Long id);

    String getBook(Long id);

    String updateBook(Long id,
                      String title,
                      String firstName,
                      String lastName,
                      String genre);
}
