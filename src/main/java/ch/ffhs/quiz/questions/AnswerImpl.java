package ch.ffhs.quiz.questions;

public class AnswerImpl implements Answer {

    private final String answerText;
    private final boolean isCorrect;

    public AnswerImpl(String answerText, boolean isCorrect) {

        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

    @Override
    public boolean isCorrect() {
        return isCorrect;
    }

    @Override
    public String toString() {
        return answerText;
    }
}
