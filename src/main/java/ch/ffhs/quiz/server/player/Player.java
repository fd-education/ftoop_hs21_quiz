package ch.ffhs.quiz.server.player;

import java.io.IOException;

public interface Player {
    String receive() throws IOException;

    void send(Object data);

    int getId();

    int getScore();

    void reward();

    void stop() throws IOException;
}
