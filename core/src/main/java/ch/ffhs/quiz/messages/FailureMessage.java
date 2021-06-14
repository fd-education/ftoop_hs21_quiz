package ch.ffhs.quiz.messages;

import java.util.Objects;

/**
 * Signals a failure.
 */
public class FailureMessage extends Message{
    /**
     * Instantiates a new Failure message.
     *
     * @param text the reason for the failure
     */
    public FailureMessage(String text) {
        Objects.requireNonNull(text);
        this.text = text;
    }
}
