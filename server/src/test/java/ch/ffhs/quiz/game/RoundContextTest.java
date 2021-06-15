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
    void addCorrectPlayerWasPlayerCorrect_positive_simple() {
        roundContext.addCorrectPlayer(player1);

        assertTrue(roundContext.wasPlayerCorrect(player1));
        assertFalse(roundContext.wasPlayerCorrect(player2));
    }

    @Test
    void setWinningPlayer_negative_cantSetWinningPlayerTwice() {
        roundContext.setWinningPlayer(player1);

        assertThrows(IllegalStateException.class, (() -> roundContext.setWinningPlayer(player1)));
    }

    @Test
    void all_negative_nullArgsThrow() {
        assertThrows(NullPointerException.class, (() -> roundContext.getPlayerAnswer(null)));
        assertThrows(NullPointerException.class, (() -> roundContext.setWinningPlayer(null)));
        assertThrows(NullPointerException.class, (() -> roundContext.addCorrectPlayer(null)));
        assertThrows(NullPointerException.class, (() -> roundContext.wasPlayerCorrect(null)));
        assertThrows(NullPointerException.class, (() -> roundContext.setPlayerAnswer(null, null)));
        assertThrows(NullPointerException.class, (() -> roundContext.setPlayerAnswer(player1, null)));
    }
}