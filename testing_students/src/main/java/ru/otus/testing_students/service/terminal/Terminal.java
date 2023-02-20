package ru.otus.testing_students.service.terminal;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class Terminal implements IOService {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String readLine() throws IOException {
        return reader.readLine();
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
