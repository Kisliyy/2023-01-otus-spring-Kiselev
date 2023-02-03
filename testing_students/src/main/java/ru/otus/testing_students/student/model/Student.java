package ru.otus.testing_students.student.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    private String firstName;
    private String lastName;
    private int countRightAnswers;
    private boolean isPassed;

    @Override
    public String toString() {
        return String.format("Student: %s %s\n" +
                "Count right answers: %s\n" +
                "Passed: %s", firstName, lastName, countRightAnswers, isPassed);
    }
}
