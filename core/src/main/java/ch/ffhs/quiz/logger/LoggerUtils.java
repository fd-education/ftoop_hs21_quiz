package ch.ffhs.quiz.logger;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

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
    public static Logger getConsoleLogger() {
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
     * Get a logger that logs to a file with the current date as filename.
     * Returns an ordinary logger for the console, if an error with the specified file
     * occurs !
     *
     * @return logger
     * @throws IOException if a parent directory does not exist
     */
    public static Logger getUnnamedFileLogger() throws IOException{
        Class<?> callingClass = StackWalker.getInstance(RETAIN_CLASS_REFERENCE).getCallerClass();
        final Logger logger = Logger.getLogger(callingClass.getName());
        return configureLogger(logger, "");
    }

    /**
     * Get a logger that logs to a file with the given name.
     * Returns an ordinary logger for the console, if an error with the specified file
     * occurs !
     *
     * @param fileName the desired file name
     * @return logger
     * @throws IOException if a parent directory does not exist
     */
    public static Logger getNamedFileLogger(String fileName) throws IOException{
        Class<?> callingClass = StackWalker.getInstance(RETAIN_CLASS_REFERENCE).getCallerClass();
        final Logger logger = Logger.getLogger(callingClass.getName());

        return configureLogger(logger, fileName);
    }

    /**
     * Get a logger that logs to a FacadeQuiz.log file in the system specific log directory
     * @return logger
     */
    private static Logger configureLogger(Logger logger, String fileName) throws IOException{

        logger.setUseParentHandlers(false);

        String postfix = fileName.isBlank()? getDateString() : fileName;

        // Only add a handler when no handler is defined yet
        if (logger.getHandlers().length == 0) {
            String logLocation = getLogFileLocation(postfix).toString();

            if (logLocation.isBlank()) {
                logger.addHandler(new CustomConsoleHandler());
            } else {
                Handler fileHandler = new FileHandler(logLocation, true);
                fileHandler.setFormatter(new SimpleFormatter());
                logger.addHandler(fileHandler);
            }
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

    /**
     * Get the path to the system specific log file location.
     *
     * @param fileName the name for the logfile
     * @return path
     * @throws IOException if a parent directory does not exist
     */
    public static Path getLogFileLocation(String fileName) throws IOException{
        String os = System.getProperty("os.name").toLowerCase();
        Path dirPath = null;

        if(os.contains("windows")){
            dirPath = Paths.get("C:/Windows/temp/FacadeQuiz");
        }

        if(os.contains("mac") || os.toLowerCase().contains("darwin")){
            dirPath = Paths.get("/var/log/FacadeQuiz");
        }

        if(os.contains("nux")){
            dirPath = Paths.get("/var/log/FacadeQuiz");
        }

        if(os.contains("sunos")){
            dirPath = Paths.get("/var/log/FacadeQuiz");
        }

        return createLogDirAndFile(dirPath, fileName);
    }

    // Create the directory and file write logs to
    private static Path createLogDirAndFile(Path dirPath, String fileName) throws IOException{
        Path filePath = null;
        try{
            Path finalDirPath = Files.createDirectories(dirPath);
            filePath = Paths.get(finalDirPath.toString(), String.format("%s.log", fileName));
            return Files.createFile(filePath);
        } catch(FileAlreadyExistsException ignored){
            return filePath;
        } catch(NullPointerException npEx){
            return Paths.get("");
        }
    }

    // Get the current date as a String without special characters.
    private static String getDateString(){
        String format = "ddMMyyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        LocalDate today = LocalDate.now();
        return today.format(formatter);
    }
}
