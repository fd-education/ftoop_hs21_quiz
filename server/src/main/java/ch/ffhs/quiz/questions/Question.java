package ch.ffhs.quiz.questions;

import java.util.List;

/**
 * The interface Question.
 */
public interface Question {

    /**
     * Checks if the given answer is correct.
     *
     * @param questionNumber the question number
     * @return the boolean
     */
    boolean checkAnswer(int questionNumber);

    /**
     * Gets the answers of the question.
     *
     * @return the answers
     */
    List<Answer> getAnswers();

    /**
     * Gets the question text.
     *
     * @return the question text
     */
    String getQuestionText();
}
