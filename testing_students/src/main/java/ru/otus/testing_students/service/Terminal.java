package ru.otus.testing_students.service;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class Terminal {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public void println(String string) {
        System.out.println(string);
    }

    public void println(Object obj) {
        System.out.println(obj);
    }
}
