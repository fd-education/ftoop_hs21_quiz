package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.messages.AnswerMessage;
import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.RoundContext;
import ch.ffhs.quiz.game.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EvaluateResponsesStepTest {

    @Mock
    Question question;
    @Mock
    Player player1;
    @Mock
    Player player2;
    GameContext gameContext;
    EvaluateResponsesStep evaluateResponsesStep;
    RoundContext roundContext;

    @BeforeEach
    void setUp() {
        List<Question> questions = new ArrayList<>();
        questions.add(question);
        when(player1.getId()).thenReturn(0);
        when(player2.getId()).thenReturn(1);
        gameContext = new GameContext(List.of(player1, player2), questions);
        gameContext.nextRound();
        roundContext = gameContext.getRoundContext();
        evaluateResponsesStep = new EvaluateResponsesStep(gameContext);
        lenient().when(question.checkAnswer(eq(0))).thenReturn(true);
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
        roundContext.setPlayerAnswer(player2, new AnswerMessage(0));
        //needed otherwise timestamps are too close together
        TimeUnit.NANOSECONDS.sleep(1);
        roundContext.setPlayerAnswer(player1, new AnswerMessage(0));

        evaluateResponsesStep.process();

        assertEquals(player2, gameContext.getRoundContext().getWinningPlayer(), "Answer of Player 2 was earlier but did not win");
        assertTrue(gameContext.getRoundContext().wasPlayerCorrect(player1), "Answer of Player 1 was later but won");
    }

    @Test
    void process_positive_noWinner() {
        roundContext.setPlayerAnswer(player1, new AnswerMessage(1));
        roundContext.setPlayerAnswer(player2, new AnswerMessage(2));

        evaluateResponsesStep.process();

        assertNull(gameContext.getRoundContext().getWinningPlayer());
    }
}