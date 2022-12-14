package ch.ffhs.quiz.connectivity.impl;

import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.messages.AnswerMessage;
import ch.ffhs.quiz.messages.MessageMock;
import ch.ffhs.quiz.messages.MessageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

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
        inputStream = new ByteArrayInputStream(MessageUtils.serialize(new MessageMock("test")).getBytes(StandardCharsets.UTF_8));
        connection = new ConnectionImpl(outputStream, inputStream);

        assertEquals("test", connection.receive(MessageMock.class).getText());
    }

    @Test
    void send_positive_simple()  {
        connection.send(new MessageMock("Test"));

        assertTrue(outputStream.toString().contains("Test"));
    }

    @Test
    void send_receive_positive() throws IOException {
        PipedInputStream inputStream = new PipedInputStream();
        PipedInputStream inputStream2 = new PipedInputStream();
        PipedOutputStream outputStream = new PipedOutputStream(inputStream2);
        PipedOutputStream outputStream2 = new PipedOutputStream(inputStream);

        connection = new ConnectionImpl(outputStream, inputStream);
        Connection connection2 = new ConnectionImpl(outputStream2, inputStream2);

        final AnswerMessage answerMessage = new AnswerMessage(0, Duration.ZERO);
        final MessageMock message = new MessageMock("""
                This is a very important test.
                """);
        connection.send(message);
        connection2.send(answerMessage);

        assertEquals(answerMessage, connection.receive(AnswerMessage.class));
        assertEquals(message, connection2.receive(MessageMock.class));
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
        connection.close();

        assertThrows(IllegalStateException.class, () -> connection.send(new MessageMock("Test")));
    }

    @Test
    void hasMessage_positive_simple() throws IOException {
        InputStream inputStreamWithText = new ByteArrayInputStream("Some test text".getBytes(StandardCharsets.UTF_8));
        InputStream inputStreamWithoutText = new ByteArrayInputStream(new byte[0]);

        Connection connectionWithMessage = new ConnectionImpl(outputStream, inputStreamWithText);
        Connection connectionWithoutMessage = new ConnectionImpl(outputStream, inputStreamWithoutText);

        assertTrue(connectionWithMessage.hasMessage());
        assertFalse(connectionWithoutMessage.hasMessage());
    }
}