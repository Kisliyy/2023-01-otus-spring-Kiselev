package ru.otus.testing_students.service.testing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.testing_students.handlers.CsvHandler;
import ru.otus.testing_students.question.model.Question;
import ru.otus.testing_students.question.service.QuestionService;
import ru.otus.testing_students.service.Terminal;
import ru.otus.testing_students.student.model.Student;
import ru.otus.testing_students.student.service.StudentService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestingStudentsService implements TestingService {

    private final CsvHandler csvHandler;
    private final QuestionService questionService;
    private final Terminal terminal;
    private final StudentService studentService;

    @Override
    public void conductSurvey() throws IOException {
        terminal.println("Enter a first name:");
        String firstName = terminal.readLine();
        terminal.println("Enter a last name:");
        String lastName = terminal.readLine();
        List<String> lines = csvHandler.handleCsvFile();
        List<Question> questionsWithAnswers = questionService.convertStringsToQuestions(lines);
        List<String> studentAnswers = new ArrayList<>();
        for (Question question : questionsWithAnswers) {
            terminal.println(question);
            terminal.println("If you have several answer options, select one of them and enter it:");
            String userAnswer = terminal.readLine();
            studentAnswers.add(userAnswer);
        }
        int countWrightAnswers = questionService.getCountRightAnswers(questionsWithAnswers, studentAnswers);
        Student student = studentService.createStudent(firstName, lastName, countWrightAnswers);
        terminal.println(student);
    }
}
