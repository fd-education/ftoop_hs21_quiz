package ch.ffhs.quiz.messages;

import java.util.Objects;

public class NameMessage extends Message {
    private boolean validated;

    public NameMessage(String text) {
        Objects.requireNonNull(text);
        this.text = text;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public boolean isValidated(){
        return validated;
    }
}
