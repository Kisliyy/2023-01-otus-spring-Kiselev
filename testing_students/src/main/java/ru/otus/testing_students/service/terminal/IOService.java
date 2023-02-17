package ru.otus.testing_students.service.terminal;

import java.io.IOException;

public interface IOService {
    String readLine() throws IOException;

    void println(String string);

    void println(Object obj);
}
