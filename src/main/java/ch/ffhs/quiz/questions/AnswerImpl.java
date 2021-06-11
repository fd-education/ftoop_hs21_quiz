package ch.ffhs.quiz.questions;

import java.util.*;

public class AnswerImpl implements Answer {
    String answer;
    Boolean isCorrectAnswer;

    public AnswerImpl(String answer, boolean isCorrectAnswer) {
        this.answer = answer;
        this.isCorrectAnswer = isCorrectAnswer;
    }

    @Override
    public boolean isCorrect() {
        return this.isCorrectAnswer;
    }

    @Override
    public String getAnswer() {
        return this.answer;
    }
}
