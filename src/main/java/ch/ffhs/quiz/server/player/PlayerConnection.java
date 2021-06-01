package ch.ffhs.quiz.server.player;

import java.io.IOException;

public interface PlayerConnection {
    void send(Object data);
    String receive() throws IOException;

    void stop() throws IOException;
}
