package ch.ffhs.quiz.connectivity;

import java.io.IOException;

public interface Connection {
    void send(Object data);
    String receive() throws IOException;

    void stop() throws IOException;

    boolean isClosed();
}
