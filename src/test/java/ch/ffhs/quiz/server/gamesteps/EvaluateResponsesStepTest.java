package ch.ffhs.quiz.server.gamesteps;

import ch.ffhs.quiz.messages.AnswerMessage;
import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.server.GameContext;
import ch.ffhs.quiz.server.RoundContext;
import ch.ffhs.quiz.server.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EvaluateResponsesStepTest {

    Question question;
    private Player player1;
    private Player player2;
    private GameContext gameContext;
    private EvaluateResponsesStep evaluateResponsesStep;
    private RoundContext roundContext;

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
        roundContext = gameContext.getRoundContext();
        evaluateResponsesStep = new EvaluateResponsesStep(gameContext);
        gameContext.nextRound();
        when(question.checkAnswer(0)).thenReturn(true);
        when(question.checkAnswer(1)).thenReturn(false);
    }

    @Test
    void process_positive_simple() {
        roundContext.setPlayerAnswer(player1, new AnswerMessage(0));
        roundContext.setPlayerAnswer(player2, new AnswerMessage(1));

        evaluateResponsesStep.process();

        assertEquals(player1, gameContext.getRoundContext().getWinningPlayer());
        assertTrue(gameContext.getRoundContext().wasPlayerCorrect(player1));
        assertFalse(gameContext.getRoundContext().wasPlayerCorrect(player2));
    }

    @RepeatedTest(10)
    void process_positive_raceCondition() throws InterruptedException {
        roundContext.setPlayerAnswer(player1, new AnswerMessage(0));
        //needed otherwise timestamps are too close together
        TimeUnit.NANOSECONDS.sleep(1);
        roundContext.setPlayerAnswer(player2, new AnswerMessage(0));

        evaluateResponsesStep.process();

        assertEquals(player1, gameContext.getRoundContext().getWinningPlayer(), "Answer of Player 1 was earlier but did not win");
        assertTrue(gameContext.getRoundContext().wasPlayerCorrect(player2), "Answer of Player 2 was later but won");
    }

    @Test
    void process_positive_noWinner() {
        roundContext.setPlayerAnswer(player1, new AnswerMessage(1));
        roundContext.setPlayerAnswer(player2, new AnswerMessage(2));

        evaluateResponsesStep.process();

        assertNull(gameContext.getRoundContext().getWinningPlayer());
    }
}