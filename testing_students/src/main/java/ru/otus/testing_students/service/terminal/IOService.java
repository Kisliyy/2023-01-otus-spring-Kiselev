package ru.otus.testing_students.service.terminal;

public interface IOService {
    String readLine();

    void println(String string);

    void println(Object obj);
}
