package ch.ffhs.quiz.messages;

import java.util.Objects;

/**
 * A data transfer object that describes a single entry in a scoreboard.
 */
public class ScoreboardEntry {
    private final String playerName;
    private final int score;

    /**
     * Instantiates a new Scoreboard entry.
     *
     * @param playerName the name of the player
     * @param score      the score of this player
     */
    public ScoreboardEntry(String playerName, int score) {
        Objects.requireNonNull(playerName);

        this.playerName = playerName;
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScoreboardEntry that = (ScoreboardEntry) o;

        if (score != that.score) return false;
        return playerName.equals(that.playerName);
    }

    @Override
    public int hashCode() {
        int result = playerName.hashCode();
        result = 31 * result + score;
        return result;
    }

    /**
     * Gets the name of the player.
     *
     * @return the player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Gets the score of the player.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }
}
