package ch.ffhs.quiz.messages;

import java.util.Objects;

/**
 * Contains the feedback for a player.
 */
public class FeedbackMessage extends Message {
    private final boolean wasCorrect;
    private final boolean wasFastest;
    private final String winningPlayer;

    /**
     * Instantiates a new Feedback message.
     *
     * @param wasCorrect    whether the player has given a correct answer.
     * @param wasFastest    whether the player was the fastest to provide a correct answer.
     * @param winningPlayer the name of the winning player
     */
    public FeedbackMessage(boolean wasCorrect, boolean wasFastest, String winningPlayer) {
        Objects.requireNonNull(winningPlayer);
        this.winningPlayer = winningPlayer;
        this.wasFastest = wasFastest;
        this.wasCorrect = wasCorrect;
    }

    /**
     * Return whether the player has given a correct answer.
     *
     * @return true if the player has given a correct answer, false otherwise.
     */
    public boolean wasCorrect() {
        return wasCorrect;
    }

    /**
     * Return whether the player was the fastest to provide a correct answer.
     *
     * @return true if the player was the fastest to provide a correct answer, false otherwise.
     */
    public boolean wasFastest() {
        return wasFastest;
    }

    /**
     * Gets the name of the winning player.
     *
     * @return the name of the winning player
     */
    public String getWinningPlayer() {
        return winningPlayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackMessage that = (FeedbackMessage) o;

        if (wasCorrect != that.wasCorrect) return false;
        if (wasFastest != that.wasFastest) return false;
        return winningPlayer.equals(that.winningPlayer);
    }

    @Override
    public int hashCode() {
        int result = (wasCorrect ? 1 : 0);
        result = 31 * result + (wasFastest ? 1 : 0);
        result = 31 * result + winningPlayer.hashCode();
        return result;
    }
}
