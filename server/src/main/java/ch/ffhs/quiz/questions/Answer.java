package ch.ffhs.quiz.questions;

/**
 * The interface Answer.
 */
public interface Answer {
    /**
     * Tells us if the answer is correct
     *
     * @return the boolean
     */
    boolean isCorrect();

    /**
     * Gets answer.
     *
     * @return the answer
     */
    String getAnswer();
}
