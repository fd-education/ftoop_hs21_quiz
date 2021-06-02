package ch.ffhs.quiz.logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErrAndOutNormalized;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.*;

class LoggerUtilsTest {
    Logger logger;

    @BeforeEach
    void setup() {
        logger = LoggerUtils.getLogger();
    }

    @Test
    void logger_positive_correctName() {
        assertEquals(this.getClass().getName(), logger.getName());
    }

    @Test
    void logger_positive_logsToConsole() throws Exception {
        String loggedText = tapSystemErrAndOutNormalized(() -> logger.info("test"));
        assertTrue(loggedText.contains("test"));
    }
}