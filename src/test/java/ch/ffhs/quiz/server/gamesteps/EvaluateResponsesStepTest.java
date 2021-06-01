package ch.ffhs.quiz.server.gamesteps;

import ch.ffhs.quiz.messages.AnswerMessage;
import ch.ffhs.quiz.messages.MessageUtils;
import ch.ffhs.quiz.server.gamesteps.EvaluateResponsesStep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.server.GameContext;
import ch.ffhs.quiz.server.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.*;
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
        evaluateResponsesStep = new EvaluateResponsesStep(gameContext);
        gameContext.nextRound();
        when(question.checkAnswer(0)).thenReturn(true);
        when(question.checkAnswer(1)).thenReturn(false);
    }

    @RepeatedTest(10)
    void process_positive_simple() throws IOException, InterruptedException {
        when(player1.receive()).thenReturn(createAnswer(0));
        //needed otherwise timestamps are too close together
        TimeUnit.NANOSECONDS.sleep(1);
        when(player2.receive()).thenReturn(createAnswer(0));

        evaluateResponsesStep.process();

        assertEquals(player1, gameContext.getRoundContext().getWinningPlayer());
        assertTrue(gameContext.getRoundContext().wasPlayerCorrect(player2));
    }

    private String createAnswer(int answer) {
        return MessageUtils.serialize(new AnswerMessage(answer));
    }

    @Test
    void process_positive_noWinner() throws IOException {
        when(player1.receive()).thenReturn(createAnswer(1));
        when(player2.receive()).thenReturn(createAnswer(2) );

        evaluateResponsesStep.process();

        assertNull(gameContext.getRoundContext().getWinningPlayer());
    }

    @Test
    void process_negative_invalidAnswer() throws Exception {
        when(player1.receive()).thenReturn(createAnswer(200));
        when(player2.receive()).thenReturn(createAnswer(0));

        evaluateResponsesStep.process();


        assertEquals(player2, gameContext.getRoundContext().getWinningPlayer());
        assertFalse(gameContext.getRoundContext().wasPlayerCorrect(player1));
    }

    @Test
    void process_negative_invalidTimestamp() throws Exception {
        when(player1.receive()).thenReturn("""
                {"type":"d"}
                """);
        when(player2.receive()).thenReturn(createAnswer(0));

        String errOutput = tapSystemErr(() -> evaluateResponsesStep.process());

        assertTrue(errOutput.contains("player 0"));
        assertFalse(errOutput.contains("player 1"));
        assertEquals(player2, gameContext.getRoundContext().getWinningPlayer());
        assertFalse(gameContext.getRoundContext().wasPlayerCorrect(player1));
    }
}