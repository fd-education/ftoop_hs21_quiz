package ch.ffhs.quiz.questions;

public class AnswerImpl implements Answer {
    final String answer;
    final Boolean isCorrectAnswer;

    /**
     * Instantiates a new Answer.
     *
     * @param answer          the answer
     * @param isCorrectAnswer tells us if it is the correct answer
     */
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
