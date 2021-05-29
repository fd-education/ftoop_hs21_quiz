package ch.ffhs.quiz.server.player.impl;

import ch.ffhs.quiz.server.player.*;

import java.io.IOException;
import java.util.Objects;

public class PlayerImpl implements Player {
    private final PlayerConnection connection;
    private final PlayerData data;

    public PlayerImpl(PlayerData data, PlayerConnection connection) {
        Objects.requireNonNull(data);
        Objects.requireNonNull(connection);

        this.data = data;
        this.connection = connection;
    }

    @Override
    public String receive() throws IOException {
        return connection.receive();
    }

    @Override
    public void send(Object data) {
        connection.send(data);
    }

    @Override
    public int getId() {
        return data.getId();
    }

    @Override
    public int getScore() {
        return data.getScore();
    }

    @Override
    public void reward() {
        data.increaseScore();
    }

    @Override
    public void stop() throws IOException {
        connection.stop();
    }
}
