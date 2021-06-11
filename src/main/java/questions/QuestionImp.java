package questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionImp implements Question {
    List<AnswerImp> answers;
    String question;
    Integer correctAnswer;
    public QuestionImp(List<AnswerImp> answers, String question, Integer correctAnswer) {
        this.answers = answers;
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean checkAnswer(Integer questionNumber) {
        return questionNumber.equals(this.correctAnswer);
    }

    @Override
    public List<AnswerImp> getAnswers() {
        return this.answers;
    }
}
