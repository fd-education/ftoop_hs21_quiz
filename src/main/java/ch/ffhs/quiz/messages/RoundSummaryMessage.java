package ch.ffhs.quiz.messages;

import java.util.List;
import java.util.Objects;


public class RoundSummaryMessage extends Message {
    private final List<ScoreboardEntry> rankedPlayersList;
    private final boolean lastRound;

    public RoundSummaryMessage(List<ScoreboardEntry> rankedPlayersList, boolean lastRound) {
        Objects.requireNonNull(rankedPlayersList);

        this.rankedPlayersList = rankedPlayersList;
        this.lastRound = lastRound;
    }

    public List<ScoreboardEntry> getRankedPlayersList() {
        return rankedPlayersList;
    }

    public boolean isLastRound() {
        return lastRound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoundSummaryMessage that = (RoundSummaryMessage) o;

        if (lastRound != that.lastRound) return false;
        return rankedPlayersList.equals(that.rankedPlayersList);
    }

    @Override
    public int hashCode() {
        int result = rankedPlayersList.hashCode();
        result = 31 * result + (lastRound ? 1 : 0);
        return result;
    }
}
