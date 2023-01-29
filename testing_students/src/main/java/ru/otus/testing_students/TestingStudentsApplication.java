package ru.otus.testing_students;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.testing_students.handlers.CsvHandler;

import java.io.IOException;

public class TestingStudentsApplication {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("/spring-context.xml");
        CsvHandler csvHandler = context.getBean(CsvHandler.class);
        csvHandler.handleCsvFile().forEach(System.out::println);
    }
}
