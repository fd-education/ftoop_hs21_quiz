package ch.ffhs.quiz.messages;

import java.io.Serializable;
import java.time.Instant;

public abstract class Message implements Serializable {
    protected String text;
    protected Instant timeStamp;
    protected Class<? extends Message> clazz;

    public Message(){
        this.timeStamp = Instant.now();
    }

    @Override
    public String toString(){
        return text;
    }

    public String getText(){return text;}


    public static void main(String[] args) {
        Message message = new FeedbackMessage("Hey", false);

        System.out.println(MessageUtils.cast(message, FeedbackMessage.class).wasCorrect());

    }
}
