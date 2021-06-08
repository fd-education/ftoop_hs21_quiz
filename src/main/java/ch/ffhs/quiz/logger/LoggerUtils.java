package ch.ffhs.quiz.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE;

public class LoggerUtils {
    public static Logger getLogger() {
        Class<?> callingClass = StackWalker.getInstance(RETAIN_CLASS_REFERENCE).getCallerClass();
        final Logger logger = Logger.getLogger(callingClass.getName());
        logger.setUseParentHandlers(false);
        logger.addHandler(new CustomConsoleHandler());
        return logger;
    }

    public static void setGlobalLogLevel(Level level) {
        Logger.getGlobal().getParent().setLevel(level);
    }
}
