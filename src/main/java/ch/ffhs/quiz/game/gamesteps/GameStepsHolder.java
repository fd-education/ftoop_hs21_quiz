package ch.ffhs.quiz.game.gamesteps;

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

    private <T> GameStep newGameStepInstance(Class<? extends GameStep> clazz, T ctorArgument) {
        try {
            return clazz.getDeclaredConstructor(ctorArgument.getClass()).newInstance(ctorArgument);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(String.format("Calling constructor of game step %s failed with exception %s", clazz.getName(), e.getCause()));
        }
    }

    public <T> void processAll(T ctorArgument) {
        for (Class<? extends GameStep> clazz : gameStepClassList) {
            newGameStepInstance(clazz, ctorArgument).process();
        }
    }
}
