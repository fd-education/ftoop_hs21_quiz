package ch.ffhs.quiz.server;

import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.server.gamesteps.GameStep;
import ch.ffhs.quiz.server.player.Player;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Game {
    private final List<Player> players;
    private final List<Question> questions;
    private final List<Class<? extends GameStep>> gameStepClasses;

    public Game(List<Question> questions, List<Player> players, List<Class<? extends GameStep>> gameStepClasses) {
        Objects.requireNonNull(questions);
        Objects.requireNonNull(players);
        Objects.requireNonNull(gameStepClasses);

        players = filterNullValues(players);
        if (players.size() < 1)
            throw new IllegalArgumentException("Cannot create a game with less than one player");

        gameStepClasses = filterNullValues(gameStepClasses);
        if (gameStepClasses.size() < 1)
            throw new IllegalArgumentException("Cannot create a game with less than one game stage");

        this.gameStepClasses = gameStepClasses;
        this.players = players;
        this.questions = filterNullValues(questions);
    }

    private static <T> List<T> filterNullValues(List<T> list) {
        Objects.requireNonNull(list);

        return list.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void start() {
        GameContext gameContext = new GameContext(players, questions);
        while (!gameContext.isFinished()) {
            gameContext.nextRound();
            for (Class<? extends GameStep> gameStepClass : gameStepClasses) {
                newGameStepInstance(gameStepClass, gameContext).process();
            }
        }
    }

    private GameStep newGameStepInstance(Class<? extends GameStep> gameStepClass, GameContext gameContext) {
        try {
            return gameStepClass.getDeclaredConstructor(GameContext.class).newInstance(gameContext);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(String.format("Calling constructor of game step %s failed with exception %s", gameStepClass.getName(), e.getCause()));
        }
    }

    public void stop() throws IOException {
        for (Player player : players)
            player.stop();
    }
}
