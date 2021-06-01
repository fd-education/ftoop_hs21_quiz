package ch.ffhs.quiz.server.player.impl;

import ch.ffhs.quiz.server.player.PlayerConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class PlayerConnectionImpl implements PlayerConnection {
    private final PrintWriter writer;
    private final BufferedReader reader;

    public PlayerConnectionImpl(PrintWriter writer, BufferedReader reader) {
        Objects.requireNonNull(writer);
        Objects.requireNonNull(reader);

        this.writer = writer;
        this.reader = reader;
    }


    @Override
    public void send(Object data) {
        Objects.requireNonNull(data);

        writer.println(data);
    }

    @Override
    public String receive() throws IOException {
        return reader.readLine();
    }

    @Override
    public void stop() throws IOException {
        writer.close();
        reader.close();
    }
}
