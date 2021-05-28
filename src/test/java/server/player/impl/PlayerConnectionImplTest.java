package server.player.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.player.PlayerConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PlayerConnectionImplTest {
    PrintWriter writer;
    BufferedReader reader;
    PlayerConnection connection;

    @BeforeEach
    void setUp() {
        writer = mock(PrintWriter.class);
        reader = mock(BufferedReader.class);
        connection = new PlayerConnectionImpl(writer, reader);
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
        assertThrows(NullPointerException.class, () -> new PlayerConnectionImpl(null, null));
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