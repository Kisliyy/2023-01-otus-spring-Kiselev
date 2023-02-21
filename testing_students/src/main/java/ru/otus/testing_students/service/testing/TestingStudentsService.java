package ru.otus.testing_students.service.testing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.testing_students.handlers.CsvHandler;
import ru.otus.testing_students.question.model.Question;
import ru.otus.testing_students.question.service.QuestionService;
import ru.otus.testing_students.service.message.MessageSourceService;
import ru.otus.testing_students.service.terminal.IOService;
import ru.otus.testing_students.student.model.Student;
import ru.otus.testing_students.student.service.StudentService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestingStudentsService implements TestingService {

    private final CsvHandler csvHandler;
    private final QuestionService questionService;
    private final MessageSourceService messageSourceService;

    private final StudentService studentService;
    private final IOService ioService;

    @Override
    public void conductSurvey(String firstName, String lastName) {
        List<String> lines = csvHandler.handleCsvFile();
        List<Question> questionsWithAnswers = questionService.convertStringsToQuestions(lines);
        List<String> studentAnswers = new ArrayList<>();
        for (Question question : questionsWithAnswers) {
            String localizationQuestionMessage = messageSourceService.getLocalizationQuestionMessage(question);
            ioService.println(localizationQuestionMessage);

            String localizationMessage = messageSourceService.getLocalizationMessage("print.answer-option");
            ioService.println(localizationMessage);

            String userAnswer = ioService.readLine();
            studentAnswers.add(userAnswer);
        }
        int countWrightAnswers = questionService.getCountRightAnswers(questionsWithAnswers, studentAnswers);
        Student student = studentService.createStudent(firstName, lastName, countWrightAnswers);
        String localizationStudentMessage = messageSourceService.getLocalizationStudentMessage(student);
        ioService.println(localizationStudentMessage);
    }
}
