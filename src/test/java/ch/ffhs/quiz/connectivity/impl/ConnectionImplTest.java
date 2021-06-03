package ch.ffhs.quiz.connectivity.impl;

import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.messages.AnswerMessage;
import ch.ffhs.quiz.messages.FeedbackMessage;
import ch.ffhs.quiz.messages.MessageUtils;
import ch.ffhs.quiz.messages.ScoreboardMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionImplTest {
    Connection connection;
    InputStream inputStream;
    OutputStream outputStream;

    @BeforeEach
    void setUp()  {
        inputStream = new ByteArrayInputStream(new byte[0]);
        outputStream = new ByteArrayOutputStream();
        connection = new ConnectionImpl(outputStream, inputStream);
    }

    @Test
    void receive_positive_simple() throws IOException {
        inputStream = new ByteArrayInputStream(MessageUtils.serialize(new ScoreboardMessage("test")).getBytes(StandardCharsets.UTF_8));
        connection = new ConnectionImpl(outputStream, inputStream);

        assertEquals("test", connection.receive(ScoreboardMessage.class).getText());
    }

    @Test
    void send_positive_simple()  {
        connection.send(new ScoreboardMessage("Test"));

        assertTrue(outputStream.toString().contains("Test"));
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

        assertThrows(IllegalStateException.class, () -> connection.send(new ScoreboardMessage("Test")));
    }
}