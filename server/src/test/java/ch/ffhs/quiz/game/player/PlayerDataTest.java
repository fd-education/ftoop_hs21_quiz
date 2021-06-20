package ch.ffhs.quiz.game.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ch.ffhs.quiz.game.player.PlayerData;

import static org.junit.jupiter.api.Assertions.*;

class PlayerDataTest {
    PlayerData playerData;

    @BeforeEach
    void setup() {
        playerData = new PlayerData(0);
    }

    @Test
    void getId_positive_simple() {
        playerData = new PlayerData(0);

        assertEquals(0, playerData.getId());
    }

    @Test
    void getId_negative_invalidId() {
        assertThrows(IllegalArgumentException.class,() -> new PlayerData(-1));
    }

    @Test
    void setName_negative_nullArgs() {
        assertThrows(NullPointerException.class,() -> playerData.setName(null));
    }

    @Test
    void getScoreIncreaseScore_positive_simple() {
        playerData.increaseScore(1);

        assertEquals(1, playerData.getScore());
    }
}