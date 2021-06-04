package ch.ffhs.quiz.messages;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public abstract class Message implements Serializable {
    protected String text;
    protected Instant timeStamp;

    public Message() {
        this.timeStamp = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
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
