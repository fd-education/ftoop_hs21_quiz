package ch.ffhs.quiz.game;

import ch.ffhs.quiz.game.gamesteps.GameStep;
import ch.ffhs.quiz.game.gamesteps.GameStepsHolder;
import ch.ffhs.quiz.game.gamesteps.impl.EvaluateResponsesStep;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.questions.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameTest {
    List<Question> questions;
    List<Player> players;
    @Mock
    GameStepsHolder setupSteps;
    @Mock
    GameStepsHolder mainSteps;
    @Mock
    GameStepsHolder teardownSteps;
    @Mock
    Player player1;
    @Mock
    Player player2;
    @Mock
    Question question;
    Game game;
    Game.GameBuilder gameBuilder;

    @BeforeEach
    void setUp() {
        questions = new ArrayList<>();
        questions.add(question);
        players = List.of(player1, player2);
        game = Game.builder().build();
        gameBuilder = Game.builder();
    }

    @Test
    void builder_nullArgsFail() {
        assertThrows(NullPointerException.class, () -> gameBuilder.withSetupSteps((GameStepsHolder) null));
        assertThrows(NullPointerException.class, () -> gameBuilder.withSetupSteps((Class<? extends GameStep>) null));
    }

    @Test
    void builder_positive_simple() {
        gameBuilder
                .withSetupSteps(setupSteps)
                .withMainSteps(EvaluateResponsesStep.class)
                .withTeardownSteps(teardownSteps);

        assertDoesNotThrow(gameBuilder::build);
    }

    @Test
    void start_positive_simple() {
        try (MockedConstruction<EvaluateResponsesStep> mocked = mockConstruction(EvaluateResponsesStep.class)){
            game = gameBuilder
                    .withSetupSteps(setupSteps)
                    .withMainSteps(mainSteps)
                    .withTeardownSteps(EvaluateResponsesStep.class)
                    .build();
            questions.add(question);

            game.play(players, questions);

            verify(setupSteps).processAll(any());
            verify(mainSteps, times(2)).processAll(any());
            verify(mocked.constructed().get(0)).process();
        }
    }
}