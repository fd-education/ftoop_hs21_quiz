package ch.ffhs.quiz.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE;

/**
 * A helper class for logging.
 */
public class LoggerUtils {

    // To prevent an instantiation of this class.
    private LoggerUtils() {}

    /**
     * Gets a logger.
     * The name of the logger is the name of the calling class.
     *
     * @return the logger
     */
    public static Logger getLogger() {
        Class<?> callingClass = StackWalker.getInstance(RETAIN_CLASS_REFERENCE).getCallerClass();
        final Logger logger = Logger.getLogger(callingClass.getName());
        logger.setUseParentHandlers(false);
        // Only add a handler when no handler is defined yet
        if (logger.getHandlers().length == 0) {
            logger.addHandler(new CustomConsoleHandler());
        }
        return logger;
    }

    /**
     * Sets the global log level.
     * Only messages above this level will be logged.
     *
     * @param level the level
     */
    public static void setGlobalLogLevel(Level level) {
        Logger.getGlobal().getParent().setLevel(level);
    }
}
