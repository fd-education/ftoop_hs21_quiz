package ch.ffhs.quiz.messages;

import com.google.gson.Gson;

public class MessageUtils {
    private static final Gson gson = new Gson();

    public static <T extends Message> T parse(String text, Class<T> messageType){
        return gson.fromJson(text, messageType);
    }

    public static String serialize(Message message){
        return gson.toJson(message);
    }
}
