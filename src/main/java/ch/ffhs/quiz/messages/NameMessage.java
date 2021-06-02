package ch.ffhs.quiz.messages;

import java.util.Objects;

public class NameMessage extends Message {
    private boolean confirmed;

    public NameMessage(String text) {
        Objects.requireNonNull(text);
        this.text = text;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isConfirmed(){
        return confirmed;
    }
}
