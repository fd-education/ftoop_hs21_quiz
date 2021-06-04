package ch.ffhs.quiz.game.player;

import ch.ffhs.quiz.messages.Message;

import java.io.IOException;

public interface Player {

    <T extends Message> T receive(Class<T> clazz) throws IOException;

    void send(Message message);

    int getId();

    int getScore();

    void reward();

    void stop() throws IOException;
}
