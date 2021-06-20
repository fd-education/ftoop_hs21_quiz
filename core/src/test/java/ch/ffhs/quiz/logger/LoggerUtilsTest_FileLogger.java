package ch.ffhs.quiz.logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class LoggerUtilsTest_FileLogger {
    Logger namedFileLogger;
    static Path testLogFile;

    @BeforeEach
    void setup() throws IOException {
        LoggerUtils.setGlobalLogLevel(Level.ALL);
        namedFileLogger = LoggerUtils.getNamedFileLogger("Test");
    }

    @Test
    void fileLogger_positive_correctName() {
        assertEquals(this.getClass().getName(), namedFileLogger.getName());
    }

    @Test
    void fileLogger_positive_isCreated() {
        assertNotNull(namedFileLogger);
        assertInstanceOf(Logger.class, namedFileLogger);
    }

    @Test
    void fileLogger_positive_logsCorrectly() throws Exception {
        namedFileLogger.info("File logger test");

        namedFileLogger.warning("Append this !");

        // only test class and method identity, date will never be identical and is therefore ignored.
        String EXPECTED_FIRST_LINE_END = "ch.ffhs.quiz.logger.LoggerUtilsTest_FileLogger fileLogger_positive_logsCorrectly";
        String EXPECTED_SECOND_LINE = "INFO: File logger test";
        String EXPECTED_THIRD_LINE_END = "ch.ffhs.quiz.logger.LoggerUtilsTest_FileLogger fileLogger_positive_logsCorrectly";
        String EXPECTED_FOURTH_LINE = "WARNING: Append this !";

        testLogFile = LoggerUtils.getLogFileLocation("Test");
        List<String> data = Files.readAllLines(testLogFile);

        assertTrue(data.get(0).endsWith(EXPECTED_FIRST_LINE_END));
        assertEquals(data.get(1), EXPECTED_SECOND_LINE);
        assertTrue(data.get(2).endsWith(EXPECTED_THIRD_LINE_END));
        assertEquals(data.get(3), EXPECTED_FOURTH_LINE);

        testLogFile.toFile().deleteOnExit();
    }
}