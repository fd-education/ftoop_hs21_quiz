package ch.ffhs.quiz.logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static org.junit.jupiter.api.Assertions.*;

class LoggerUtilsTest_ConsoleLogger {
    Logger consoleLogger;

    @BeforeEach
    void setup(){
        LoggerUtils.setGlobalLogLevel(Level.ALL);
        consoleLogger = LoggerUtils.getConsoleLogger();

    }

    @Test
    void consoleLogger_positive_correctName() {
        assertEquals(this.getClass().getName(), consoleLogger.getName());
    }

    @Test
    void consoleLogger_positive_isCreated() {
        assertNotNull(consoleLogger);
        assertInstanceOf(Logger.class, consoleLogger);
    }

    @Test
    void consoleLogger_positive_logsCorrectly() throws Exception {
        String err = tapSystemErr((() -> consoleLogger.warning("errTest")));
        String out = tapSystemOutNormalized((() -> consoleLogger.info("outTest")));

        assertTrue(err.contains("errTest"));
        assertTrue(out.contains("outTest"));
    }

    @Test
    void setGlobalLogLevel_positive_simple() throws Exception {
        String out1 = tapSystemOutNormalized((() -> consoleLogger.info("outTest")));
        String err1 = tapSystemErr((() -> consoleLogger.warning("errTest")));
        LoggerUtils.setGlobalLogLevel(Level.WARNING);
        String err2 = tapSystemErr((() -> consoleLogger.warning("errTest")));
        String out2 = tapSystemOutNormalized((() -> consoleLogger.info("outTest")));

        assertFalse(out1.isEmpty());
        assertFalse(err1.isEmpty());
        assertTrue(out2.isEmpty());
        assertFalse(err2.isEmpty());
    }
}