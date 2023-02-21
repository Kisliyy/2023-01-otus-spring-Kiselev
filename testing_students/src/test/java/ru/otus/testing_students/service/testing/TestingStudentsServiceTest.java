package ru.otus.testing_students.service.testing;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.testing_students.answer.model.Answer;
import ru.otus.testing_students.config.AbstractTestConfig;
import ru.otus.testing_students.handlers.CsvHandler;
import ru.otus.testing_students.question.model.Question;
import ru.otus.testing_students.question.service.QuestionService;
import ru.otus.testing_students.service.message.MessageSourceService;
import ru.otus.testing_students.service.terminal.IOService;
import ru.otus.testing_students.student.model.Student;
import ru.otus.testing_students.student.service.StudentService;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


class TestingStudentsServiceTest extends AbstractTestConfig {

    @MockBean
    private QuestionService questionService;
    @MockBean
    private IOService ioService;
    @MockBean
    private StudentService studentService;
    @MockBean
    private CsvHandler csvHandler;

    @MockBean
    private MessageSourceService messageSourceService;
    @Autowired
    private TestingService testingStudentsService;

    @Test
    void conductSurveySequenceMethodCallTest() {
        Long id = 1L;
        String question = "Will you take the spring course on otus?";
        String answer = "yes";
        Answer rightAnswer = new Answer(answer);

        Question qs = Question.builder()
                .id(id)
                .answers(Collections.emptyList())
                .question(question)
                .rightAnswer(rightAnswer)
                .build();

        List<Question> questionList = List.of(qs);

        String firstName = "test";
        String lastName = "test_last";
        int countRightAnswer = 1;
        boolean isPassed = true;

        Student student = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .countRightAnswers(countRightAnswer)
                .isPassed(isPassed)
                .build();

        when(messageSourceService.getLocalizationMessage(any()))
                .thenReturn("If you have several answer options, select one of them and enter it:");
        when(messageSourceService.getLocalizationQuestionMessage(qs)).thenReturn(qs.toString());
        when(messageSourceService.getLocalizationStudentMessage(student)).thenReturn(student.toString());

        when(ioService.readLine()).thenReturn(answer);
        when(csvHandler.handleCsvFile()).thenReturn(Collections.emptyList());
        when(questionService.convertStringsToQuestions(anyList())).thenReturn(questionList);
        when(questionService.getCountRightAnswers(anyList(), anyList())).thenReturn(countRightAnswer);
        when(studentService.createStudent(firstName, lastName, countRightAnswer)).thenReturn(student);

        testingStudentsService.conductSurvey(firstName, lastName);

        InOrder inOrder = Mockito.inOrder(ioService, questionService, studentService, csvHandler, messageSourceService);
        inOrder.verify(csvHandler).handleCsvFile();

        inOrder.verify(questionService).convertStringsToQuestions(anyList());

        inOrder.verify(messageSourceService).getLocalizationQuestionMessage(qs);
        inOrder.verify(ioService).println(qs.toString());

        inOrder.verify(messageSourceService).getLocalizationMessage(anyString());
        inOrder.verify(ioService).println("If you have several answer options, select one of them and enter it:");
        inOrder.verify(ioService).readLine();

        inOrder.verify(questionService).getCountRightAnswers(anyList(), anyList());
        inOrder.verify(studentService).createStudent(firstName, lastName, countRightAnswer);
        inOrder.verify(messageSourceService).getLocalizationStudentMessage(student);
        inOrder.verify(ioService).println(student.toString());
    }
}