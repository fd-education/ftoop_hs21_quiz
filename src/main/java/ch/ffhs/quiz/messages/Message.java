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


}
