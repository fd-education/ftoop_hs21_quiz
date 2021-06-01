package ch.ffhs.quiz.server.gamesteps;

import ch.ffhs.quiz.messages.AnswerMessage;
import ch.ffhs.quiz.messages.MessageUtils;
import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.server.GameContext;
import ch.ffhs.quiz.server.RoundContext;
import ch.ffhs.quiz.server.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReceiveResponsesStepTest {
    private Player player1;
    private Player player2;
    private GameContext gameContext;
    private RoundContext roundContext;
    private ReceiveResponsesStep receiveResponsesStep;

    @BeforeEach
    void setUp() {
        List<Question> questions = new ArrayList<>();
        Question question = mock(Question.class);
        questions.add(question);
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        when(player1.getId()).thenReturn(0);
        when(player2.getId()).thenReturn(1);
        gameContext = new GameContext(List.of(player1, player2), questions);
        roundContext = gameContext.getRoundContext();
        receiveResponsesStep = new ReceiveResponsesStep(gameContext);
        gameContext.nextRound();
    }

    private String createAnswer(int answer) {
        return MessageUtils.serialize(new AnswerMessage(answer));
    }

    @Test
    void process_positive_simple() throws IOException {
        when(player1.receive()).thenReturn(createAnswer(0));
        when(player2.receive()).thenReturn(createAnswer(1));

        receiveResponsesStep.process();

        assertNotNull(roundContext.getPlayerAnswer(player1));
        assertNotNull(roundContext.getPlayerAnswer(player2));
    }

    @Test
    void process_negative_invalidAnswer() throws IOException {
        when(player1.receive()).thenReturn("""
                {"type":"d"}
                """);

        assertThrows(RuntimeException.class, () -> receiveResponsesStep.process());
    }
}