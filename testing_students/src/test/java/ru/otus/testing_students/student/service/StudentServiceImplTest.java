package ru.otus.testing_students.student.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.testing_students.config.AbstractTestConfig;
import ru.otus.testing_students.student.model.Student;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest extends AbstractTestConfig {

    @Autowired
    private StudentService studentService;

    private final String firstName = "firstName";
    private final String lastName = "lastName";

    @Test
    void createStudentSuccessfulFailedSurvey() {
        int countRightAnswer = 0;

        Student student = studentService.createStudent(firstName, lastName, countRightAnswer);
        assertEquals(firstName, student.getFirstName());
        assertEquals(lastName, student.getLastName());
        assertEquals(countRightAnswer, student.getCountRightAnswers());
        assertFalse(student.isPassed());
    }

    @Test
    void createStudentSuccessfulPassedSurvey() {
        int countRightAnswer = 2;

        Student student = studentService.createStudent(firstName, lastName, countRightAnswer);
        assertEquals(firstName, student.getFirstName());
        assertEquals(lastName, student.getLastName());
        assertEquals(countRightAnswer, student.getCountRightAnswers());
        assertTrue(student.isPassed());
    }
}