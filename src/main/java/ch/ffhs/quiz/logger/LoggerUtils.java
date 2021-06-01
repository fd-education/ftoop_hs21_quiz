package ch.ffhs.quiz.logger;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE;

public class LoggerUtils {
    public static Logger getLogger() {
        Class<?> callingClass = StackWalker.getInstance(RETAIN_CLASS_REFERENCE).getCallerClass();
        Logger logger = Logger.getLogger(callingClass.getName());
        final ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
        return logger;
    }
}
