package ch.ffhs.quiz.game.player;

import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.messages.Message;
import ch.ffhs.quiz.game.player.*;

import java.io.IOException;
import java.util.Objects;

/**
 * Represents a player with a {@link Connection} and {@link PlayerData} object.
 */
public class Player {
    private final Connection connection;
    private final PlayerData data;

    /**
     * Instantiates a new Player.
     *
     * @param data       the data of the player
     * @param connection the connection this player uses
     */
    public Player(PlayerData data, Connection connection) {
        Objects.requireNonNull(data);
        Objects.requireNonNull(connection);

        this.data = data;
        this.connection = connection;
    }

    /**
     * Receive a message of a given message class.
     *
     * @param <T>          the type parameter
     * @param messageClass type of expected message
     * @return a message of the specified type
     * @throws IOException if an I/O Error occurs
     */
    public <T extends Message> T receive(Class<T> messageClass) throws IOException {
        return connection.receive(messageClass);
    }

    /**
     * Send a message.
     *
     * @param message the message
     */
    public void send(Message message) {
        connection.send(message);
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return data.getId();
    }

    /**
     * Gets the current score.
     *
     * @return the score
     */
    public int getScore() {
        return data.getScore();
    }

    /**
     * Rewards the player by increasing his score by a fixed amount of points
     */
    public void reward() {
        data.increaseScore(1);
    }

    /**
     * Disconnects the player from the server.
     *
     * @throws IOException if an I/O Error occurs
     */
    public void disconnect() throws IOException {
        connection.close();
    }

    /**
     * Sets the name of the player.
     *
     * @param name the name of the player
     */
    public void setName(String name) {
        data.setName(name);
    }

    /**
     * Gets the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return data.getName();
    }

    /**
     * Check if there is an unread message from this player.
     *
     * @return true if a message is ready, false otherwise
     * @throws IOException if an I/O Error occurs
     */
    public boolean hasMessage() throws IOException {
        return connection.hasMessage();
    }
}
