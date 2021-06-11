package ch.ffhs.quiz.messages;

import java.util.Objects;

public class FeedbackMessage extends Message {
    private final boolean wasCorrect;
    private final boolean wasFastest;
    private final String winningPlayer;

    public FeedbackMessage(boolean wasCorrect, boolean wasFastest, String winningPlayer) {
        Objects.requireNonNull(winningPlayer);
        this.winningPlayer = winningPlayer;
        this.wasFastest = wasFastest;
        this.wasCorrect = wasCorrect;
    }

    public boolean wasCorrect() {
        return wasCorrect;
    }

    public boolean wasFastest() {
        return wasFastest;
    }

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
