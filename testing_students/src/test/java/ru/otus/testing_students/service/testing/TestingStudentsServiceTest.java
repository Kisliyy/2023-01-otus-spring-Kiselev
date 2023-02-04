package ru.otus.testing_students.service.testing;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import ru.otus.testing_students.answer.model.Answer;
import ru.otus.testing_students.config.AbstractTestConfig;
import ru.otus.testing_students.question.model.Question;
import ru.otus.testing_students.question.service.QuestionService;
import ru.otus.testing_students.service.Terminal;
import ru.otus.testing_students.student.model.Student;
import ru.otus.testing_students.student.service.StudentService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestingStudentsServiceTest extends AbstractTestConfig {

    private final QuestionService questionService = mock(QuestionService.class);
    private final Terminal terminal = mock(Terminal.class);
    private final StudentService studentService = mock(StudentService.class);

    private final TestingService testingStudentsService = new TestingStudentsService(questionService, terminal, studentService);

    @Test
    void conductSurveySequenceMethodCallTest() throws IOException {
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
        when(terminal.readLine()).thenReturn(firstName, lastName, answer);

        when(questionService.getQuestionsWithAnswers()).thenReturn(questionList);
        when(questionService.getCountRightAnswers(anyList(), anyList())).thenReturn(countRightAnswer);
        when(studentService.createStudent(firstName, lastName, countRightAnswer)).thenReturn(student);

        testingStudentsService.conductSurvey();

        InOrder inOrder = Mockito.inOrder(terminal, questionService, studentService);
        inOrder.verify(terminal).println("Enter a first name:");
        inOrder.verify(terminal).readLine();
        inOrder.verify(terminal).println("Enter a last name:");
        inOrder.verify(terminal).readLine();
        inOrder.verify(questionService).getQuestionsWithAnswers();
        inOrder.verify(terminal).println(qs);
        inOrder.verify(terminal).println("If you have several answer options, select one of them and enter it:");
        inOrder.verify(terminal).readLine();
        inOrder.verify(questionService).getCountRightAnswers(anyList(), anyList());
        inOrder.verify(studentService).createStudent(firstName, lastName, countRightAnswer);
        inOrder.verify(terminal).println(student);
    }
}