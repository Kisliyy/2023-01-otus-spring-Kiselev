package ru.otus.testing_students.question.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.testing_students.answer.model.Answer;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    private Long id;
    private String question;
    private List<Answer> answers;
    private Answer rightAnswer;

    @Override
    public String toString() {
        String output = "Question: " + question;
        if (answers.size() > 1) {
            return output + "\n" +
                    "Answers: " + answers
                    .stream().map(Answer::getAnswer).collect(Collectors.joining(", "));
        }
        return output;
    }
}
