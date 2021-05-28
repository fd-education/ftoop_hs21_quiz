package server.player.impl;

import server.player.PlayerData;

public class PlayerDataImpl implements PlayerData {
    private final int id;
    private int score;

    public PlayerDataImpl(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must not be negative");
        this.id = id;
        this.score = 0;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void increaseScore() {
        score += 1;
    }
}
