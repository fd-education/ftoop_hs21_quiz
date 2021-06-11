package ch.ffhs.quiz.game.player.impl;

import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.messages.Message;
import ch.ffhs.quiz.game.player.*;

import java.io.IOException;
import java.util.Objects;

public class PlayerImpl implements Player {
    private final Connection connection;
    private final PlayerData data;
    private String name;

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

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasMessage() throws IOException {
        return connection.hasMessage();
    }
}
