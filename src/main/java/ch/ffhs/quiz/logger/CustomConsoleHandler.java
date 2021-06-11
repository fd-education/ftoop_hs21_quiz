package ch.ffhs.quiz.logger;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class CustomConsoleHandler extends ConsoleHandler {

    @Override
    public void publish(LogRecord record) {
        if (!isLoggable(record)) {
            return;
        }
        String msg = getFormatter().format(record).trim();
        log(msg, record.getLevel());
    }

    private void log(String msg, Level level) {
        if (level.intValue() < Level.WARNING.intValue()) {
            System.out.println(msg);
        } else {
            System.err.println(msg);
        }
    }
}
