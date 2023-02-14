package ru.otus.testing_students.exceptions;

public class HandleException extends RuntimeException {
    public HandleException(Throwable exception) {
        super(exception);
    }
}
