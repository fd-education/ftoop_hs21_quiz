package ch.ffhs.quiz.logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

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
    void logger_isCreated() {
        assertNotNull(logger);
        assertInstanceOf(Logger.class, logger);
    }
}