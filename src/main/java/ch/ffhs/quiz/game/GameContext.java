package ch.ffhs.quiz.game;

import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.game.player.Player;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameContext {
    private final List<Player> players;
    private final Iterator<Question> questions;
    private RoundContext roundContext;

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

    private static <T> List<T> filterNullValues(List<T> list) {
        Objects.requireNonNull(list);

        return list.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }


    public RoundContext getRoundContext() {
        if (roundContext == null) {
            throw new IllegalStateException("Round context can't be returned if no round has been started");
        }
        return roundContext;
    }

    public boolean isFinished() {
        return !questions.hasNext();
    }

    public void nextRound() {
        if (isFinished()) throw new IllegalStateException("Game is over, no new questions available.");
        roundContext = new RoundContext(questions.next());
    }

    public List<Player> getPlayers() {
        return players;
    }
}
