package ch.ffhs.quiz.game.player;

import java.util.Objects;

/**
 * Represents the pure data of a player.
 */
public class PlayerData {
    private final int id;
    private int score;
    private String name;

    /**
     * Creates a new data object. The default score is zero
     *
     * @param id the unique ID of this player. Must be positive.
     */
    public PlayerData(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must not be negative");
        this.id = id;
        this.score = 0;
        this.name = "";
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the current score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Increases the score by a unit of one.
     *
     * @param amount the amount
     */
    public void increaseScore(int amount) {
        score += amount;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name
     */
    public void setName(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }
}
