package ch.ffhs.quiz.messages;

import java.util.List;
import java.util.Objects;


/**
 * Contains relevant information for the end of a round.
 */
public class RoundSummaryMessage extends Message {
    private final List<ScoreboardEntry> rankedPlayersList;
    private final boolean isLastRound;

    /**
     * Instantiates a new Round summary message.
     *
     * @param rankedPlayersList the list of scoreboard entries
     * @param isLastRound       if this was the last round
     */
    public RoundSummaryMessage(List<ScoreboardEntry> rankedPlayersList, boolean isLastRound) {
        Objects.requireNonNull(rankedPlayersList);

        this.rankedPlayersList = rankedPlayersList;
        this.isLastRound = isLastRound;
    }

    /**
     * Gets the contained list of scoreboard entries.
     *
     * @return the list of scoreboard entries
     */
    public List<ScoreboardEntry> getRankedPlayersList() {
        return rankedPlayersList;
    }

    /**
     * Whether this was the last round.
     *
     * @return true if this was the last round, false otherwise.
     */
    public boolean isLastRound() {
        return isLastRound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoundSummaryMessage that = (RoundSummaryMessage) o;

        if (isLastRound != that.isLastRound) return false;
        return rankedPlayersList.equals(that.rankedPlayersList);
    }

    @Override
    public int hashCode() {
        int result = rankedPlayersList.hashCode();
        result = 31 * result + (isLastRound ? 1 : 0);
        return result;
    }
}
