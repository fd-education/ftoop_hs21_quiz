package ch.ffhs.quiz.game.gamesteps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class GameStepsHolder {
    private final Iterator<Class<? extends GameStep>> gameStepClassIterator;

    private GameStepsHolder(List<Class<? extends GameStep>> gameStepClassList) {
        Objects.requireNonNull(gameStepClassList);

        gameStepClassIterator = gameStepClassList.iterator();
    }

    @SafeVarargs
    public static GameStepsHolder of(final Class<? extends GameStep>... gameStepClassArray) {
        return new GameStepsHolder(List.of(gameStepClassArray));
    }

    public static GameStepsHolder emptyHolder() {
        return new GameStepsHolder(new ArrayList<>());
    }

    private boolean hasNext() {
        return gameStepClassIterator.hasNext();
    }

    private <T> GameStep nextStep(T ctorArgument) {
        Class<? extends GameStep> gameStepClass = gameStepClassIterator.next();
        try {
            return gameStepClass.getDeclaredConstructor(ctorArgument.getClass()).newInstance(ctorArgument);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(String.format("Calling constructor of game step %s failed with exception %s", gameStepClass.getName(), e.getCause()));
        }
    }

    public <T> void processAll(T ctorArgument) {
        while (hasNext()) {
            nextStep(ctorArgument).process();
        }
    }
}
