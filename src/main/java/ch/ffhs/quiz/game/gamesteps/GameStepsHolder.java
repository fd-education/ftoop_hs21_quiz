package ch.ffhs.quiz.game.gamesteps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class GameStepsHolder {
    private final List<Class<? extends GameStep>> gameStepClassIterator;

    private GameStepsHolder(List<Class<? extends GameStep>> gameStepClassList) {
        Objects.requireNonNull(gameStepClassList);

        gameStepClassIterator = gameStepClassList;
    }

    @SafeVarargs
    public static GameStepsHolder of(final Class<? extends GameStep>... gameStepClassArray) {
        return new GameStepsHolder(List.of(gameStepClassArray));
    }

    public static GameStepsHolder emptyHolder() {
        return new GameStepsHolder(new ArrayList<>());
    }


    private <T> GameStep nextStep(Class<? extends GameStep> clazz, T ctorArgument) {
        try {
            return clazz.getDeclaredConstructor(ctorArgument.getClass()).newInstance(ctorArgument);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(String.format("Calling constructor of game step %s failed with exception %s", clazz.getName(), e.getCause()));
        }
    }

    public <T> void processAll(T ctorArgument) {
        for (Class<? extends GameStep> clazz : gameStepClassIterator) {
            nextStep(clazz, ctorArgument).process();
        }
    }
}
