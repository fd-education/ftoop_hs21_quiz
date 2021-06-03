package ch.ffhs.quiz.server.player.impl;

import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.messages.Message;
import ch.ffhs.quiz.server.player.*;

import java.io.IOException;
import java.util.Objects;

public class PlayerImpl implements Player {
    private final Connection connection;
    private final PlayerData data;

    public PlayerImpl(PlayerData data, Connection connection) {
        Objects.requireNonNull(data);
        Objects.requireNonNull(connection);

        this.data = data;
        this.connection = connection;
    }

    @Override
    public <T extends Message> T receive(Class<T> clazz) throws IOException {
        return connection.receive(clazz);
    }

    @Override
    public void send(Message message) {
        connection.send(message);
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
