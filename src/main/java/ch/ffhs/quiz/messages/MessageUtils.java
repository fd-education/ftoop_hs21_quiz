package ch.ffhs.quiz.messages;

public class MessageUtils {

    public static <T extends Message> T cast(Object obj, Class<T> messageType){
        return messageType.cast(obj);
    }
}
