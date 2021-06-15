package ch.ffhs.quiz.game.player;

import org.junit.jupiter.api.Test;
import ch.ffhs.quiz.game.player.PlayerData;

import static org.junit.jupiter.api.Assertions.*;

class PlayerDataTest {

    @Test
    void getId_positive_simple() {
        PlayerData playerData = new PlayerData(0);

        assertEquals(0, playerData.getId());
    }

    @Test
    void getId_negative_invalidId() {
        assertThrows(IllegalArgumentException.class,() -> new PlayerData(-1));
    }

    @Test
    void getScoreIncreaseScore_positive_simple() {
        PlayerData playerData = new PlayerData(0);

        playerData.increaseScore(1);

        assertEquals(1, playerData.getScore());
    }
}