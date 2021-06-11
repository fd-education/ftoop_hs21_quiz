package ch.ffhs.quiz.connectivity.impl;

import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.messages.Message;
import ch.ffhs.quiz.messages.MessageUtils;

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
    public void send(Message message) {
        Objects.requireNonNull(message);
        requireOpenConnection();

        writer.println(MessageUtils.serialize(message));
    }

    private void requireOpenConnection() {
        if (this.isClosed()) {
            throw new IllegalStateException("Closed connection cannot be accessed");
        }
    }

    @Override
    public <T extends Message> T receive(Class<T> clazz) throws IOException {
        Objects.requireNonNull(clazz);
        requireOpenConnection();

        return MessageUtils.parse(reader.readLine(), clazz);
    }

    @Override
    public boolean hasMessage() throws IOException {
        requireOpenConnection();
        return reader.ready();
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
