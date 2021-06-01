package ch.ffhs.quiz.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

@Deprecated(forRemoval = true)
public class Logger {
    private static Date date = new Date();
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static void log(String text){
        String currDate = formatter.format(date);
        System.out.println(currDate + " : " + text);
    }
}