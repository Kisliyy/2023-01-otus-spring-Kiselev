package ru.otus.testing_students.question.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.testing_students.answer.model.Answer;
import ru.otus.testing_students.answer.service.AnswerService;
import ru.otus.testing_students.question.model.Question;
import ru.otus.testing_students.service.IdCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final AnswerService answerService;
    private final IdCounter idCounter;

    @Autowired
    public QuestionServiceImpl(AnswerService answerService,
                               IdCounter idCounter) {
        this.answerService = answerService;
        this.idCounter = idCounter;
    }

    @Override
    public List<Question> convertStringsToQuestions(List<String> lines) {
        List<Question> questions = new ArrayList<>(0);
        for (int i = 0; i < lines.size() - 2; i++) {
            Question newQuestion = new Question();
            String line = lines.get(i);
            if (line.endsWith("?")) {
                newQuestion.setQuestion(line);
                String lineAnswer = lines.get(i + 1);
                List<Answer> answers = answerService.convertStringToAnswers(lineAnswer);
                newQuestion.setAnswers(answers);
                String lineWrightAnswer = lines.get(i + 2);
                Answer wrightAnswer = answerService.getRightAnswer(newQuestion.getAnswers(), lineWrightAnswer);
                newQuestion.setRightAnswer(wrightAnswer);
                newQuestion.setId(idCounter.getNextId());
                questions.add(newQuestion);
            }
        }
        return questions;
    }

    @Override
    public int getCountRightAnswers(List<Question> questions, List<String> answers) {
        List<String> clearAnswers = answers
                .stream()
                .map(String::trim)
                .collect(Collectors.toList());
        int countRightAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.size() == clearAnswers.size()) {
                Question question = questions.get(i);
                Answer wrightAnswer = question.getRightAnswer();
                String answer = clearAnswers.get(i);
                if (wrightAnswer.getAnswer().equalsIgnoreCase(answer)) {
                    countRightAnswers++;
                }
            } else {
                throw new RuntimeException("The student did not answer all the questions");
            }
        }
        return countRightAnswers;
    }
}
