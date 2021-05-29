package ch.ffhs.quiz.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.server.player.Player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class GameTest {
    List<Question> questions;
    Server server;
    List<Player> players;

    @BeforeEach
    void setUp() {
        questions = new ArrayList<>();
        questions.add(mock(Question.class));
        server = mock(Server.class);
        players = List.of(mock(Player.class));
    }

    @Test
    void ctor_nullArgsFail() {
        assertThrows(NullPointerException.class, () -> new Game(null, players));
        assertThrows(NullPointerException.class, () -> new Game(questions, null));
    }

    @Test
    void ctor_invalidPlayerCountFails() {
        assertThrows(IllegalArgumentException.class, () -> new Game(questions, new ArrayList<>()));
    }

    @Test
    void ctor_nullPlayersFail() {
        List<Player> nullPlayerList = new ArrayList<>();

        nullPlayerList.add(null);
        assertThrows(IllegalArgumentException.class, () -> new Game(questions, nullPlayerList));
    }
}