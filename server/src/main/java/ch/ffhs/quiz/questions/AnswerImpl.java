package ch.ffhs.quiz.questions;

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
    @Override
    public String toString() {
        return answer;
    }
}
