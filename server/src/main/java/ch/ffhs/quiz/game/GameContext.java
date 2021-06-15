package ch.ffhs.quiz.game;

import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.game.player.Player;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The game context stores information on the current game.
 */
public class GameContext {
    private final List<Player> players;
    private final Iterator<Question> questions;
    private RoundContext roundContext;

    /**
     * Instantiates a new Game context.
     *
     * @param players   the players in this game
     * @param questions the questions in this game
     */
    public GameContext(List<Player> players, List<Question> questions) {
        Objects.requireNonNull(players);
        Objects.requireNonNull(questions);

        players = filterNullValues(players);
        if (players.size() < 1)
            throw new IllegalArgumentException("At least one player is required");

        questions = filterNullValues(questions);
        if (questions.size() < 1)
            throw new IllegalArgumentException("At least one question is required");

        this.players = players;
        this.questions = questions.listIterator();
    }

    // filters null values out of a list and returns the result. Does not modify the list directly
    private static <T> List<T> filterNullValues(List<T> list) {
        Objects.requireNonNull(list);

        return list.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }


    /**
     * Gets the current round context.
     *
     * @return the round context
     * @throws IllegalStateException if no round was started yet
     */
    public RoundContext getRoundContext() throws IllegalStateException {
        if (roundContext == null) {
            throw new IllegalStateException("Round context can't be returned if no round has been started");
        }
        return roundContext;
    }

    /**
     * Whether the game is finished or not.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished() {
        return !questions.hasNext();
    }

    /**
     * Sets the context for the next round of this game.
     */
    public void nextRound() {
        if (isFinished()) throw new IllegalStateException("Game is over, no new questions available.");
        roundContext = new RoundContext(questions.next());
    }

    /**
     * Gets the players in this game.
     *
     * @return the players
     */
    public List<Player> getPlayers() {
        return players;
    }
}
