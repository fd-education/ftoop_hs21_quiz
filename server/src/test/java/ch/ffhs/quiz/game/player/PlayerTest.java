package ch.ffhs.quiz.game.player;

import ch.ffhs.quiz.connectivity.Connection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class PlayerTest {
    @Test
    void ctor_negative_nullArg() {
        assertThrows(NullPointerException.class, () -> new Player(null, null));
        assertThrows(NullPointerException.class, () -> new Player(mock(PlayerData.class), null));
    }

    @Test
    void all_negative_nullArgsThrow() {
        Player player = new Player(mock(PlayerData.class), mock(Connection.class));
        assertThrows(NullPointerException.class, () -> player.send(null));
        assertThrows(NullPointerException.class, () -> player.setName(null));
        assertThrows(NullPointerException.class, () -> player.receive(null));

    }

}