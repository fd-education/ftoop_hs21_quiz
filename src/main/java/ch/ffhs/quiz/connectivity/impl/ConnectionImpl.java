package ch.ffhs.quiz.connectivity.impl;

import ch.ffhs.quiz.connectivity.Connection;

import java.io.*;
import java.util.Objects;

public class ConnectionImpl implements Connection {
    private final PrintWriter writer;
    private final BufferedReader reader;
    private boolean closed = false;

    public ConnectionImpl(OutputStream outputStream, InputStream inputStream) {
        Objects.requireNonNull(outputStream);
        Objects.requireNonNull(inputStream);

        PrintWriter writer = new PrintWriter(outputStream, true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        this.writer = writer;
        this.reader = reader;
    }


    @Override
    public void send(Object data) {
        Objects.requireNonNull(data);
        requireOpenConnection();

        writer.println(data);
    }

    private void requireOpenConnection() {
        if (this.isClosed()) {
            throw new IllegalStateException("Closed connection cannot be accessed");
        }
    }

    @Override
    public String receive() throws IOException {
        requireOpenConnection();

        return reader.readLine();
    }

    @Override
    public void stop() throws IOException {
        writer.close();
        reader.close();
        closed = true;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }
}
