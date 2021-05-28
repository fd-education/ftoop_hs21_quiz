package server.player.impl;

import org.junit.jupiter.api.Test;
import server.player.PlayerData;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PlayerImplTest {
    @Test
    void ctor_negative_nullArg() {
        assertThrows(NullPointerException.class, () -> new PlayerImpl(null, null));
        assertThrows(NullPointerException.class, () -> new PlayerImpl(mock(PlayerData.class), null));
    }

}