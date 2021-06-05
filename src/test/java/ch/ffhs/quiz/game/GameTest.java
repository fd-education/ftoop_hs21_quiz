package ch.ffhs.quiz.game;

import ch.ffhs.quiz.game.gamesteps.GameStepsHolder;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.questions.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @BeforeEach
    void setUp() {
        questions = new ArrayList<>();
        questions.add(question);
        players = List.of(player1, player2);
    }

    @Test
    void ctor_nullArgsFail() {
        assertThrows(NullPointerException.class, () -> new Game(null, players, setupSteps, mainSteps, teardownSteps));
        assertThrows(NullPointerException.class, () -> new Game(questions, null, setupSteps, mainSteps, teardownSteps));
        assertThrows(NullPointerException.class, () -> new Game(questions, players, null, mainSteps, teardownSteps));
    }

    @Test
    void ctor_invalidPlayerCountFails() {
        assertThrows(IllegalArgumentException.class, () -> new Game(questions, new ArrayList<>(), setupSteps, mainSteps, teardownSteps));
    }

    @Test
    void ctor_playerListsWithNullsFails() {
        List<Player> nullPlayerList = new ArrayList<>();

        nullPlayerList.add(null);
        assertThrows(IllegalArgumentException.class, () -> new Game(questions, nullPlayerList, setupSteps, mainSteps, teardownSteps));
    }

    @Test
    void start_positive_simple() {
        questions.add(question);
        final Game game = new Game(questions, players, setupSteps, mainSteps, teardownSteps);

        game.play();

        verify(setupSteps).processAll(any());
        verify(mainSteps, times(2)).processAll(any());
        verify(teardownSteps).processAll(any());
    }
}