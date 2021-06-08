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
        LoggerUtils.setGlobalLogLevel(Level.ALL);
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

    @Test
    void setGlobalLogLevel_positive_simple() throws Exception {
        String out1 = tapSystemOut((() -> logger.info("outTest")));
        String err1 = tapSystemErr((() -> logger.warning("errTest")));
        LoggerUtils.setGlobalLogLevel(Level.WARNING);
        String err2 = tapSystemErr((() -> logger.warning("errTest")));
        String out2 = tapSystemOut((() -> logger.info("outTest")));

        assertFalse(out1.isEmpty());
        assertFalse(err1.isEmpty());
        assertTrue(out2.isEmpty());
        assertFalse(err2.isEmpty());
    }
}