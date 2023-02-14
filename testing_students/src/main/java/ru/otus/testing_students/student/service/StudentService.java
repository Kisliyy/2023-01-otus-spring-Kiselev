package ru.otus.testing_students.student.service;

import ru.otus.testing_students.student.model.Student;

public interface StudentService {

    Student createStudent(String firstName, String lastName, int countWrightAnswerStudent);
}
