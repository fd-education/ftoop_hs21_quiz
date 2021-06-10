package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.gamesteps.impl.ReceiveResponsesStep;
import ch.ffhs.quiz.messages.AnswerMessage;
import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.RoundContext;
import ch.ffhs.quiz.game.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceiveResponsesStepTest {
    @Mock
    private Player player1;
    @Mock
    private Player player2;
    private RoundContext roundContext;
    private ReceiveResponsesStep receiveResponsesStep;

    @BeforeEach
    void setUp() {
        List<Question> questions = new ArrayList<>();
        Question question = mock(Question.class);
        questions.add(question);
        GameContext gameContext = new GameContext(List.of(player1, player2), questions);
        gameContext.nextRound();
        roundContext = gameContext.getRoundContext();
        receiveResponsesStep = new ReceiveResponsesStep(gameContext);
    }


    @Test
    void process_positive_simple() throws IOException {
        when(player1.receive(AnswerMessage.class)).thenReturn(new AnswerMessage(0));
        when(player2.receive(AnswerMessage.class)).thenReturn(new AnswerMessage(0));

        receiveResponsesStep.process();

        assertNotNull(roundContext.getPlayerAnswer(player1));
        assertNotNull(roundContext.getPlayerAnswer(player2));
    }

    @Test
    void process_negative_invalidAnswer() throws IOException {
        when(player1.receive(AnswerMessage.class)).thenReturn(new AnswerMessage(0){
            @Override
            public Instant getTimeStamp() {
                return null;
            }
        });

        assertThrows(RuntimeException.class, () -> receiveResponsesStep.process());
    }
}