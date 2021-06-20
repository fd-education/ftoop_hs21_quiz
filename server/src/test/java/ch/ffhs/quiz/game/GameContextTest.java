package ch.ffhs.quiz.game;

import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.questions.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameContextTest {
    @Mock
    Player player;
    @Mock
    Question question1;
    @Mock
    Question question2;
    GameContext gameContext;
    List<Question> questions;
    List<Player> players;

    @BeforeEach
    void setUp() {
        players = List.of(player);
        questions = List.of(question1, question2);
        gameContext = new GameContext(players, questions);
    }

    @Test
    void getRoundContext_positive_hasExpectedQuestion() {
        gameContext.nextRound();

        assertEquals(question1, gameContext.getRoundContext().getQuestion());
    }

    @Test
    void getRoundContext_negative_throwsWhenRoundNotStarted() {
        assertThrows(IllegalStateException.class, (() -> gameContext.getRoundContext()));
    }

    @Test
    void isFinished_positive_simple() {
        gameContext.nextRound();
        boolean wasFinishedAfterFirstRound = gameContext.isFinished();
        gameContext.nextRound();

        assertFalse(wasFinishedAfterFirstRound);
        assertTrue(gameContext.isFinished());
    }

    @Test
    void nextRound_negative_throwsWhenFinished() {
        gameContext.nextRound();
        gameContext.nextRound();

        assertThrows(IllegalStateException.class, (() -> gameContext.nextRound()));
    }

    @Test
    void ctor_negative_nullArgsFail() {
        assertThrows(NullPointerException.class, (() -> new GameContext(null, questions)));
        assertThrows(NullPointerException.class, (() -> new GameContext(players, null)));
    }

    @Test
    void play_invalidListSizesFail() {
        assertThrows(IllegalArgumentException.class, () -> new GameContext(new ArrayList<>(), questions));
        assertThrows(IllegalArgumentException.class, () -> new GameContext(players, new ArrayList<>()));
    }

    @Test
    void ctor_playerListsWithNullsFail() {
        List<Player> nullPlayerList = new ArrayList<>();

        nullPlayerList.add(null);
        assertThrows(IllegalArgumentException.class, () -> new GameContext(nullPlayerList, questions));

        List<Question> nullQuestionList = new ArrayList<>();

        nullQuestionList.add(null);
        assertThrows(IllegalArgumentException.class, () -> new GameContext(players, nullQuestionList));
    }
}