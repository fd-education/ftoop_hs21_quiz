package ch.ffhs.quiz.messages;

import java.util.Objects;

public class NameMessage extends Message {

    public NameMessage(String text) {
        Objects.requireNonNull(text);
        this.text = text;

        this.clazz = this.getClass();
    }
}
