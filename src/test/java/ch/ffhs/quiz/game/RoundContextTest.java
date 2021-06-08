package ch.ffhs.quiz.game;

import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.questions.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

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
        roundContext = new RoundContext();
    }

    @Test
    void nextRound_positive_simple() {
        roundContext.nextRound(question1);
        final int roundNumber1 = roundContext.getRoundNumber();
        final Question returnedQuestion1 = roundContext.getCurrentQuestion();
        roundContext.nextRound(question2);
        final int roundNumber2 = roundContext.getRoundNumber();
        final Question returnedQuestion2 = roundContext.getCurrentQuestion();

        assertEquals(question1, returnedQuestion1);
        assertEquals(question2, returnedQuestion2);
        assertEquals(1, roundNumber1);
        assertEquals(2, roundNumber2);
    }

    @Test
    void setAndGetPlayerAnswer_positive_simple() {

    }

    @Test
    void getPlayerAnswer() {
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