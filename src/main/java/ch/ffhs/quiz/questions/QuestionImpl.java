package ch.ffhs.quiz.questions;

import java.util.List;

public class QuestionImpl implements Question {
    List<Answer> answers;
    String question;
    Integer correctAnswer;
    public QuestionImpl(List<Answer> answers, String question, Integer correctAnswer) {
        this.answers = answers;
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean checkAnswer(Integer questionNumber) {
        return questionNumber.equals(this.correctAnswer);
    }

    @Override
    public List<Answer> getAnswers() {
        return this.answers;
    }

    @Override
    public String getQuestionText() {
        return question;
    }
}
