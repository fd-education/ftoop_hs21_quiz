package ch.ffhs.quiz.messages;

import java.io.Serializable;
import java.time.Instant;

public abstract class Message implements Serializable {
    protected String text;
    protected Instant timeStamp;

    public Message() {
        this.timeStamp = Instant.now();
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getText() {
        return text;
    }
}
