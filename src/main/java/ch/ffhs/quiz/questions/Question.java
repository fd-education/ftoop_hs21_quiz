package ch.ffhs.quiz.questions;

import java.util.List;

public interface Question {
    boolean checkAnswer(Integer questionNumber);
    List<AnswerImp> getAnswers();
    String getQuestionText();
}
