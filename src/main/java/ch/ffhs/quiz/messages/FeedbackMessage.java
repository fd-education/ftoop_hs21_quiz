package ch.ffhs.quiz.messages;

import java.util.Objects;

public class FeedbackMessage extends Message {
    private final boolean wasCorrect;

    public FeedbackMessage(String text, boolean wasCorrect){
        Objects.requireNonNull(text);
        this.text = text;

        this.wasCorrect = wasCorrect;
    }

    public boolean wasCorrect(){return wasCorrect;}
}
