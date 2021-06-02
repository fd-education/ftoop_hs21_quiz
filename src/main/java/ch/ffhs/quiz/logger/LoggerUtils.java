package ch.ffhs.quiz.logger;

import java.util.logging.Logger;

import static java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE;

public class LoggerUtils {
    public static Logger getLogger() {
        Class<?> callingClass = StackWalker.getInstance(RETAIN_CLASS_REFERENCE).getCallerClass();
        return Logger.getLogger(callingClass.getName());
    }
}
