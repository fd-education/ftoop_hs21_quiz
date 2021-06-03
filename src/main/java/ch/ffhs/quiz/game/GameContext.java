package ch.ffhs.quiz.game;

import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.game.player.Player;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class GameContext {
    private final List<Player> players;
    private final Iterator<Question> questions;
    private final RoundContext roundContext;

    public GameContext(List<Player> players, List<Question> questions) {
        Objects.requireNonNull(players);
        Objects.requireNonNull(questions);

        this.players = players;
        this.questions = questions.listIterator();
        this.roundContext = new RoundContext();
    }

    public RoundContext getRoundContext() {
        return roundContext;
    }

    public boolean isFinished() {
        return !questions.hasNext();
    }

    public void nextRound() {
        if (isFinished()) throw new IllegalStateException("Game is over, no new questions available.");
        roundContext.nextRound(questions.next());
    }

    public List<Player> getPlayers() {
        return players;
    }
}
