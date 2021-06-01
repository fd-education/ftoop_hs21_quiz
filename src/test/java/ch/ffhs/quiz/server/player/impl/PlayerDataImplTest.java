package ch.ffhs.quiz.server.player.impl;

import org.junit.jupiter.api.Test;
import ch.ffhs.quiz.server.player.PlayerData;

import static org.junit.jupiter.api.Assertions.*;

class PlayerDataImplTest {

    @Test
    void getId_positive_simple() {
        PlayerData playerData = new PlayerDataImpl(0);

        assertEquals(0, playerData.getId());
    }

    @Test
    void getId_negative_invalidId() {
        assertThrows(IllegalArgumentException.class,() -> new PlayerDataImpl(-1));
    }

    @Test
    void getScoreIncreaseScore_positive_simple() {
        PlayerData playerData = new PlayerDataImpl(0);

        playerData.increaseScore();

        assertEquals(1, playerData.getScore());
    }
}