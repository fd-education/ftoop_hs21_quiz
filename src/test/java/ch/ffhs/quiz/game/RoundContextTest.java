package ch.ffhs.quiz.game;

import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.AnswerMessage;
import ch.ffhs.quiz.questions.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class RoundContextTest {
    RoundContext roundContext;
    @Mock
    Question question1;
    @Mock
    Question question2;
    @Mock
    Player player1;
    @Mock
    Player player2;

    @BeforeEach
    void setup() {
        roundContext = new RoundContext(question1);
    }

    @Test
    void setAndGetPlayerAnswer_positive_simple() {
        final AnswerMessage mockAnswer = mock(AnswerMessage.class);
        roundContext.setPlayerAnswer(player1, mockAnswer);

        assertEquals(mockAnswer, roundContext.getPlayerAnswer(player1));
    }

    @Test
    void getPlayerAnswer_negative_noAnswerForPlayer() {
        assertThrows(IllegalArgumentException.class, (() -> roundContext.getPlayerAnswer(player2)));
    }

    @Test
    void addCorrectPlayer() {
    }

    @Test
    void wasPlayerCorrect() {
    }

    @Test
    void getRoundNumber() {
    }
}