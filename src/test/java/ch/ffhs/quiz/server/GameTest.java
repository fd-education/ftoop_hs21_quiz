package ch.ffhs.quiz.server;

import ch.ffhs.quiz.server.gamesteps.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.server.player.Player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GameTest {
    List<Question> questions;
    Server server;
    List<Player> players;
    List<Class<? extends GameStep>> mockStep;
    Player player1;
    Player player2;
    Question question;

    @BeforeEach
    void setUp() {
        mockStep = List.of(
                MockStep.class
        );
        questions = new ArrayList<>();
        question = mock(Question.class);
        questions.add(question);
        server = mock(Server.class);
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        players = List.of(player1, player2);
    }

    @Test
    void ctor_nullArgsFail() {
        assertThrows(NullPointerException.class, () -> new Game(null, players, mockStep));
        assertThrows(NullPointerException.class, () -> new Game(questions, null, mockStep));
        assertThrows(NullPointerException.class, () -> new Game(questions, players, null));
    }

    @Test
    void ctor_invalidListSizesFail() {
        assertThrows(IllegalArgumentException.class, () -> new Game(questions, new ArrayList<>(), mockStep));
        assertThrows(IllegalArgumentException.class, () -> new Game(questions, players, new ArrayList<>()));
    }

    @Test
    void ctor_listsWithNullsFail() {
        List<Player> nullPlayerList = new ArrayList<>();

        nullPlayerList.add(null);
        assertThrows(IllegalArgumentException.class, () -> new Game(questions, nullPlayerList, mockStep));
        List<Class<? extends GameStep>> nullGameStepClassesList = new ArrayList<>();

        nullGameStepClassesList.add(null);
        assertThrows(IllegalArgumentException.class, () -> new Game(questions, players, nullGameStepClassesList));
    }

    @Test
    void start_positive_simple() {
        final Game game = new Game(questions, players, mockStep);

        game.start();

        verify(player1).reward();
        verify(player2).reward();
        verify(question).getAnswers();
        verify(player1).getScore();
    }

    private static class MockStep extends GameStep{
        public MockStep(GameContext gameContext) {
            super(gameContext);
        }

        @Override
        protected void prepareStep() {
            roundContext.getCurrentQuestion().getAnswers();
        }

        @Override
        protected void handlePlayer(Player player) {
            player.reward();
        }

        @Override
        protected void completeStep() {
            gameContext.getPlayers().get(0).getScore();
        }
    }
}