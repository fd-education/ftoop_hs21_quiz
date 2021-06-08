package questions;

import java.util.*;

public class AnswerImp implements Answer {
    String answer;
    Boolean isCorrectAnswer;

    public AnswerImp(String answer, boolean isCorrectAnswer) {
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
