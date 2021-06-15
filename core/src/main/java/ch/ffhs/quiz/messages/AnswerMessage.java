package ch.ffhs.quiz.messages;

import java.time.Duration;

/**
 * Contains the answer of a player.
 */
public class AnswerMessage extends Message {

    private final int chosenAnswer;
    private final Duration answerTime;

    /**
     * Instantiates a new Answer message.
     *
     * @param chosenAnswer the answer as chosen by the player
     */
    public AnswerMessage(int chosenAnswer, Duration answerTime) {
        this.chosenAnswer = chosenAnswer;
        this.answerTime = answerTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerMessage that = (AnswerMessage) o;

        return chosenAnswer == that.chosenAnswer;
    }

    @Override
    public int hashCode() {
        return chosenAnswer;
    }

    /**
     * Gets the chosen answer.
     *
     * @return the chosen answer
     */
    public int getChosenAnswer() {
        return chosenAnswer;
    }

    /**
     * Gets the time the player needed to answer the question
     * @return the time to answer
     */
    public Duration getAnswerTime(){return answerTime;}
}
