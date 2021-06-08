package ch.ffhs.quiz.logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
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
    void logger_positive_isCreated() {
        assertNotNull(logger);
        assertInstanceOf(Logger.class, logger);
    }

    @Test
    void logger_positive_logsCorrectly() throws Exception {
        String err = tapSystemErr((() -> logger.warning("errTest")));
        String out = tapSystemOut((() -> logger.info("outTest")));

        assertTrue(err.contains("errTest"));
        assertTrue(out.contains("outTest"));
    }
}