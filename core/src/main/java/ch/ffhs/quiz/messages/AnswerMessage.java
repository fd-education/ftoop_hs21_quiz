package ch.ffhs.quiz.messages;

/**
 * Contains the answer of a player.
 */
public class AnswerMessage extends Message {

    private final int chosenAnswer;

    /**
     * Instantiates a new Answer message.
     *
     * @param chosenAnswer the answer as chosen by the player
     */
    public AnswerMessage(int chosenAnswer) {
        this.chosenAnswer = chosenAnswer;
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
}
