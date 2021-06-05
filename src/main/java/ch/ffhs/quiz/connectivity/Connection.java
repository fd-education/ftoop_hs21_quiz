package ch.ffhs.quiz.connectivity;

import ch.ffhs.quiz.messages.Message;

import java.io.IOException;

public interface Connection {
    void send(Message message);
    <T extends Message> T receive(Class<T> clazz) throws IOException;

    boolean hasMessage() throws IOException;

    void stop() throws IOException;

    boolean isClosed();
}
