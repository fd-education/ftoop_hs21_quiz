package ch.ffhs.quiz.game.gamesteps;

import ch.ffhs.quiz.game.GameContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameStepsHolder {
    private final List<Class<? extends GameStep>> gameStepClassList;

    private GameStepsHolder(List<Class<? extends GameStep>> gameStepClassList) {
        Objects.requireNonNull(gameStepClassList);

        this.gameStepClassList = gameStepClassList;
    }

    @SafeVarargs
    public static GameStepsHolder of(final Class<? extends GameStep>... gameStepClassArray) {
        return new GameStepsHolder(List.of(gameStepClassArray));
    }

    public static GameStepsHolder emptyHolder() {
        return new GameStepsHolder(new ArrayList<>());
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

    private GameStep newGameStepInstance(Class<? extends GameStep> clazz, GameContext gameContext) {
        try {
            return clazz.getDeclaredConstructor(GameContext.class).newInstance(gameContext);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(String.format("Calling constructor of game step %s failed with exception %s", clazz.getName(), e.getCause()));
        }
    }

    public <T> void processAll(GameContext gameContext) {
        for (Class<? extends GameStep> clazz : gameStepClassList) {
            newGameStepInstance(clazz, gameContext).process();
        }
    }
}
