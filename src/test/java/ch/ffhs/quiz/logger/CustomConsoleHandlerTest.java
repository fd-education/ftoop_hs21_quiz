package ch.ffhs.quiz.logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomConsoleHandlerTest {
    ConsoleHandler consoleHandler;

    @BeforeEach
    void setUp() {
        consoleHandler = new CustomConsoleHandler();
    }

    @Test
    void publish_positive_correctLogLocations() throws Exception {
        String err = tapSystemErr((() -> consoleHandler.publish(new LogRecord(Level.WARNING, "errTest"))));
        String out = tapSystemOut((() -> consoleHandler.publish(new LogRecord(Level.INFO, "outTest"))));

        assertTrue(err.contains("errTest"));
        assertTrue(out.contains("outTest"));
    }
}