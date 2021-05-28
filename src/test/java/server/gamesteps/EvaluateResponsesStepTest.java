package server.gamesteps;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.Question;
import server.GameContext;
import server.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class EvaluateResponsesStepTest {

    private Player player1;
    private Player player2;
    private GameContext gameContext;
    private EvaluateResponsesStep evaluateResponsesStep;
    Question question;

    @BeforeEach
    void setUp() {
        List<Question> questions = new ArrayList<>();
        question = mock(Question.class);
        questions.add(question);
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        when(player1.getId()).thenReturn(0);
        when(player2.getId()).thenReturn(1);
        gameContext = new GameContext(List.of(player1, player2), questions);
        evaluateResponsesStep = new EvaluateResponsesStep();
        gameContext.nextRound();
        when(question.checkAnswer(0)).thenReturn(true);
        when(question.checkAnswer(1)).thenReturn(false);
    }

    @Test
    void process_positive_simple() throws IOException {
        when(player1.receive()).thenReturn("0", "0");
        when(player2.receive()).thenReturn("1", "10");

        evaluateResponsesStep.process(gameContext);

        assertEquals(gameContext.getRoundContext().getWinningPlayer(), player1);
        assertFalse(gameContext.getRoundContext().wasPlayerCorrect(player2));
    }

    @Test
    void process_positive_noWinner() throws IOException {
        when(player1.receive()).thenReturn("2", "0");
        when(player2.receive()).thenReturn("1", "10");

        evaluateResponsesStep.process(gameContext);

        assertNull(gameContext.getRoundContext().getWinningPlayer());
    }

    @Test
    void process_negative_invalidAnswer() throws IOException {
        when(player1.receive()).thenReturn("aa", "0");
        when(player2.receive()).thenReturn("0", "10");

        evaluateResponsesStep.process(gameContext);

        assertEquals(gameContext.getRoundContext().getWinningPlayer(), player2);
        assertFalse(gameContext.getRoundContext().wasPlayerCorrect(player1));
    }

    @Test
    void process_negative_invalidTimestamp() throws IOException {
        when(player1.receive()).thenReturn("0", "bb");
        when(player2.receive()).thenReturn("0", "10");

        evaluateResponsesStep.process(gameContext);

        assertEquals(gameContext.getRoundContext().getWinningPlayer(), player2);
        assertFalse(gameContext.getRoundContext().wasPlayerCorrect(player1));
    }
}