package ru.otus.testing_students.service.terminal;

import org.springframework.stereotype.Component;
import ru.otus.testing_students.exceptions.HandleException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class TerminalService implements IOService {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new HandleException("The line could not be read", e);
        }
    }

    @Override
    public void println(String string) {
        System.out.println(string);
    }

    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }
}
