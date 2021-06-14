package ch.ffhs.quiz.connectivity;

import ch.ffhs.quiz.messages.Message;

import java.io.IOException;

/**
 * A wrapper class for sending and receiving {@link Message} objects.
 */
public interface Connection {

    /**
     * Sends a message over the established connection.
     *
     * @param message the message to be sent.
     */
    void send(Message message);

    /**
     * Receive a message of the specified type over the established connection.
     *
     * @param messageClass type of expected message
     * @return a message of the specified type
     * @throws IOException if an I/O Error occurs
     */
    <T extends Message> T receive(Class<T> messageClass) throws IOException;

    /**
     * Checks if the connection has received a message that hasn't been read yet.
     *
     * @return true if a message is ready, false otherwise
     * @throws IOException if an I/O Error occurs
     */
    boolean hasMessage() throws IOException;

    /**
     * Closes the connection.
     *
     * @throws IOException if an I/O Error occurs
     */
    void close() throws IOException;

    /**
     * Whether the connection is closed or not.
     *
     * @return True if the connection is closed, false otherwise
     */
    boolean isClosed();
}
