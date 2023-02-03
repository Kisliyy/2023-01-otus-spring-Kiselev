package ru.otus.testing_students.question.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.testing_students.answer.model.Answer;
import ru.otus.testing_students.answer.service.AnswerService;
import ru.otus.testing_students.handlers.CsvHandler;
import ru.otus.testing_students.question.model.Question;
import ru.otus.testing_students.service.IdCounter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final CsvHandler csvHandler;
    private final AnswerService answerService;
    private final IdCounter idCounter;

    @Autowired
    public QuestionServiceImpl(CsvHandler csvHandler,
                               AnswerService answerService,
                               IdCounter idCounter) {
        this.csvHandler = csvHandler;
        this.answerService = answerService;
        this.idCounter = idCounter;
    }

    @Override
    public List<Question> getQuestionsWithAnswers() throws IOException {
        List<String> lines = csvHandler.handleCsvFile();
        return getQuestions(lines);
    }

    @Override
    public int getCountRightAnswers(List<Question> questions, List<String> answers) {
        List<String> clearAnswers = answers.stream().map(String::trim).collect(Collectors.toList());
        int countRrightAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.size() == clearAnswers.size()) {
                Question question = questions.get(i);
                Answer wrightAnswer = question.getRightAnswer();
                String answer = clearAnswers.get(i);
                if (wrightAnswer.getAnswer().equalsIgnoreCase(answer)) {
                    countRrightAnswers++;
                }
            } else {
                throw new RuntimeException("The student did not answer all the questions");
            }
        }
        return countRrightAnswers;
    }

    private List<Question> getQuestions(List<String> lines) {
        List<Question> questions = new ArrayList<>(0);
        for (int i = 0; i < lines.size() - 2; i++) {
            Question newQuestion = new Question();
            String line = lines.get(i);
            if (line.endsWith("?")) {
                newQuestion.setQuestion(line);
                String lineAnswer = lines.get(i + 1);
                List<Answer> answers = answerService.getAnswer(lineAnswer);
                newQuestion.setAnswers(answers);
                String lineWrightAnswer = lines.get(i + 2);
                Answer wrightAnswer = answerService.getWrightAnswer(newQuestion.getAnswers(), lineWrightAnswer);
                newQuestion.setRightAnswer(wrightAnswer);
                newQuestion.setId(idCounter.getNextId());
                questions.add(newQuestion);
            }
        }
        return questions;
    }
}
