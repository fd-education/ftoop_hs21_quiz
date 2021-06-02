package ch.ffhs.quiz.messages;

import java.util.Objects;

public class SuccessMessage extends Message {
    public SuccessMessage(String text) {
        Objects.requireNonNull(text);
        this.text = text;
    }
}
