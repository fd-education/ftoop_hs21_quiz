package ch.ffhs.quiz.game;

import ch.ffhs.quiz.game.gamesteps.GameStepsHolder;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.questions.Question;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Game {
    private final List<Player> players;
    private final List<Question> questions;
    private final GameStepsHolder setupSteps;
    private final GameStepsHolder mainSteps;
    private final GameStepsHolder teardownSteps;

    public Game(List<Question> questions, List<Player> players, GameStepsHolder setupSteps, GameStepsHolder mainSteps, GameStepsHolder teardownSteps) {
        Objects.requireNonNull(questions);
        Objects.requireNonNull(players);
        Objects.requireNonNull(setupSteps);
        Objects.requireNonNull(mainSteps);
        Objects.requireNonNull(teardownSteps);

        players = filterNullValues(players);
        if (players.size() < 1)
            throw new IllegalArgumentException("Cannot create a game with less than one player");

        this.players = players;
        this.questions = filterNullValues(questions);
        this.setupSteps = setupSteps;
        this.mainSteps = mainSteps;
        this.teardownSteps = teardownSteps;
    }

    private static <T> List<T> filterNullValues(List<T> list) {
        Objects.requireNonNull(list);

        return list.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void play() {
        GameContext gameContext = new GameContext(players, questions);
        setupSteps.processAll(gameContext);
        while (!gameContext.isFinished()) {
            gameContext.nextRound();
            mainSteps.processAll(gameContext);
        }
        teardownSteps.processAll(gameContext);
    }
}
