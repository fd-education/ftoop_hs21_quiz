package ch.ffhs.quiz.messages;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A message is a read-only class that contains data that can be sent over a connection.
 */
public abstract class Message implements Serializable {
    protected String text = "";
    private final Instant timeStamp;

    /**
     * Instantiates a new message object.
     */
    public Message() {
        this.timeStamp = Instant.now();
    }


    /**
     * Compares another message object to this message.
     * To allow for useful comparison, the timestamp is ignored in this method.
     * For referential equality checks use the == operator
     *
     * @param o the other object
     * @return true if the messages have the same data apart from the timestamp, false otherwise
     */
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

    /**
     * Gets the timestamp of this message.
     * This allows to see the time the message was created.
     *
     * @return the time stamp
     */
    public Instant getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return text;
    }

    /**
     * Gets the text of the message.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }
}
