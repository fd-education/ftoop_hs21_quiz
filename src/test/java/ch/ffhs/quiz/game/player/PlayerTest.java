package ch.ffhs.quiz.game.player;

import ch.ffhs.quiz.game.player.Player;
import org.junit.jupiter.api.Test;
import ch.ffhs.quiz.game.player.PlayerData;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PlayerTest {
    @Test
    void ctor_negative_nullArg() {
        assertThrows(NullPointerException.class, () -> new Player(null, null));
        assertThrows(NullPointerException.class, () -> new Player(mock(PlayerData.class), null));
    }

}