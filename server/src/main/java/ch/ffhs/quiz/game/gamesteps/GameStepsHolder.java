package ch.ffhs.quiz.game.gamesteps;

import ch.ffhs.quiz.game.GameContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Stores a number of {@linkplain GameStep game steps} and provides a utility method to start them in the given order.
 */
public class GameStepsHolder {
    private final List<Class<? extends GameStep>> gameStepClassList;

    private GameStepsHolder(List<Class<? extends GameStep>> gameStepClassList) {
        Objects.requireNonNull(gameStepClassList);

        this.gameStepClassList = gameStepClassList;
    }

    /**
     * Returns a game steps holder containing the given game steps.
     *
     * @param gameStepClassArray game step classes
     * @return the created game steps holder
     */
    @SafeVarargs
    public static GameStepsHolder of(final Class<? extends GameStep>... gameStepClassArray) {
        return new GameStepsHolder(List.of(gameStepClassArray));
    }

    /**
     * Returns a game step holder without any game steps. Useful for testing.
     *
     * @return the game steps holder
     */
    public static GameStepsHolder emptyHolder() {
        return new GameStepsHolder(new ArrayList<>());
    }

    // Functional method to instantiate a game step given its class and the game context.
    private static GameStep newGameStepInstance(Class<? extends GameStep> clazz, GameContext gameContext) {
        try {
            return clazz.getDeclaredConstructor(GameContext.class).newInstance(gameContext);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(String.format("Calling constructor of game step %s failed with exception %s", clazz.getName(), e.getCause()));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameStepsHolder that = (GameStepsHolder) o;

        return gameStepClassList.equals(that.gameStepClassList);
    }

    @Override
    public int hashCode() {
        return gameStepClassList.hashCode();
    }

    /**
     * Process all stored game steps.
     * This will create an instance for every stored game step class with the given game context and then process it.
     *
     * @param gameContext the game context to be used for the instantiation
     */
    public void processAll(GameContext gameContext) {
        for (Class<? extends GameStep> clazz : gameStepClassList) {
            newGameStepInstance(clazz, gameContext).process();
        }
    }
}
