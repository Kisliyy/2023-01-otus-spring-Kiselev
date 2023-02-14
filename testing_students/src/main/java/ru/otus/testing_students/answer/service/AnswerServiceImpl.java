package ru.otus.testing_students.answer.service;

import org.springframework.stereotype.Service;
import ru.otus.testing_students.answer.model.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Override
    public List<Answer> convertStringToAnswers(String lineAnswers) {
        List<String> stringAnswers = Arrays
                .stream(lineAnswers.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        List<Answer> answers = new ArrayList<>(1);
        if (stringAnswers.size() > 1) {
            stringAnswers
                    .stream()
                    .map(Answer::new)
                    .forEach(answers::add);
        }
        return answers;
    }

    @Override
    public Answer getRightAnswer(List<Answer> answersOption, String lineRightAnswer) {
        final String wrightAnswer = lineRightAnswer.trim();
        if (answersOption.size() > 1) {
            return answersOption
                    .stream()
                    .filter(answer -> answer.getAnswer().equals(wrightAnswer))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Not found wright answer!"));
        }
        return new Answer(wrightAnswer);
    }

}
