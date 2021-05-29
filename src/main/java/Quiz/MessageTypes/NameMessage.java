package Quiz.MessageTypes;

import Quiz.Message;

import java.util.Objects;

public class NameMessage extends Message {

    public NameMessage(String text) {
        Objects.requireNonNull(text);
        this.text = text;
    }
}
