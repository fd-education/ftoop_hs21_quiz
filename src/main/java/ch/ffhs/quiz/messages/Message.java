package ch.ffhs.quiz.messages;

import java.io.Serializable;
import java.time.Instant;

public abstract class Message implements Serializable {
    protected String text;
    protected Instant timeStamp;

    public Message(){
        this.timeStamp = Instant.now();
    }

    public String getText(){return text;}
}
