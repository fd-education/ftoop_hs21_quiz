package ch.ffhs.quiz.connectivity.impl;

import ch.ffhs.quiz.connectivity.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class ConnectionImpl implements Connection {
    private final PrintWriter writer;
    private final BufferedReader reader;

    public ConnectionImpl(PrintWriter writer, BufferedReader reader) {
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
