package ch.ffhs.quiz.messages;

import java.util.Objects;

public class FailureMessage extends Message{
    public FailureMessage(String text) {
        Objects.requireNonNull(text);
        this.text = text;
    }
}
