package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.questions.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StopPlayersStepTest {
    @Mock
    private Player player1;
    @Mock
    private Player player2;
    StopPlayersStep stopPlayersStep;

    @BeforeEach
    void setUp() {
        Question question = mock(Question.class);
        GameContext gameContext = new GameContext(List.of(player1, player2), List.of(question));
        stopPlayersStep = new StopPlayersStep(gameContext);
    }

    @Test
    void process_positive_simple() throws IOException {
        stopPlayersStep.process();

        verify(player1).stop();
        verify(player2).stop();
    }

    @Test
    void process_negative_simple() throws IOException {
        doThrow(IOException.class).when(player2).stop();

        assertThrows(RuntimeException.class, () -> stopPlayersStep.process());
        verify(player1).stop();
    }
}