package ru.otus.testing_students.answer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    private String answer;

    @Override
    public String toString() {
        return answer;
    }

}
