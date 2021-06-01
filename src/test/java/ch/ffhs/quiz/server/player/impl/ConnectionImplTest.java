package ch.ffhs.quiz.server.player.impl;

import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ch.ffhs.quiz.connectivity.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ConnectionImplTest {
    PrintWriter writer;
    BufferedReader reader;
    Connection connection;

    @BeforeEach
    void setUp() {
        writer = mock(PrintWriter.class);
        reader = mock(BufferedReader.class);
        connection = new ConnectionImpl(writer, reader);
    }

    @Test
    void receive_positive_simple() throws IOException {
        when(reader.readLine()).thenReturn("test");

        assertEquals("test", connection.receive());
    }

    @Test
    void send_positive_simple() {
        connection.send("Test");

        verify(writer).println((Object) "Test");
    }

    @Test
    void ctor_negative_nullArg() {
        assertThrows(NullPointerException.class, () -> new ConnectionImpl(null, null));
    }

    @Test
    void send_negative_nullArg() {
        assertThrows(NullPointerException.class, () -> connection.send(null));
    }

    @Test
    void stop_positive_simple() throws IOException {
        connection.stop();

        verify(writer).close();
        verify(reader).close();
    }
}