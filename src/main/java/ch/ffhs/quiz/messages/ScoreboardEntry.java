package ch.ffhs.quiz.messages;

import java.util.Objects;

public class ScoreboardEntry {
    private final String playerName;
    private final int score;

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

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }
}
