package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.gamesteps.GameStep;
import ch.ffhs.quiz.game.gamesteps.GameStepsHolder;
import ch.ffhs.quiz.game.gamesteps.impl.ConfirmNamesStep;
import ch.ffhs.quiz.game.gamesteps.impl.EvaluateResponsesStep;
import ch.ffhs.quiz.game.gamesteps.impl.FeedbackStep;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameStepsHolderTest {

    @Test
    void emptyOfEqualsEmptyHolder() {
        Assertions.assertEquals(GameStepsHolder.of(), GameStepsHolder.emptyHolder());
    }

    @Test
    void of_positive_simple() {
        assertDoesNotThrow(() -> GameStepsHolder.of(
                ConfirmNamesStep.class,
                EvaluateResponsesStep.class,
                FeedbackStep.class
        ));
    }

    @Test
    void of_negative_nullFails() {
        assertThrows(NullPointerException.class, (() -> GameStepsHolder.of((Class<? extends GameStep>) null)));
    }

    @Test
    void processAll() {
        try (MockedConstruction<ConfirmNamesStep> mocked = mockConstruction(ConfirmNamesStep.class)) {
            try (MockedConstruction<FeedbackStep> mocked2 = mockConstruction(FeedbackStep.class)) {
                final GameStepsHolder gameStepsHolder = GameStepsHolder.of(
                        ConfirmNamesStep.class,
                        FeedbackStep.class
                );
                final GameContext mockedGameContext = mock(GameContext.class);

                gameStepsHolder.processAll(mockedGameContext);
                gameStepsHolder.processAll(mockedGameContext);

                verify(mocked.constructed().get(0)).process();
                verify(mocked2.constructed().get(0)).process();
                verify(mocked.constructed().get(1)).process();
                verify(mocked2.constructed().get(1)).process();
            }
        }
    }
}