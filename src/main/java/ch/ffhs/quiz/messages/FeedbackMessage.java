package ch.ffhs.quiz.messages;

import java.util.Objects;

public class FeedbackMessage extends Message {
    private final boolean correct;

    public FeedbackMessage(String text, boolean correctAnswer){
        Objects.requireNonNull(text);
        this.text = text;

        this.correct = correctAnswer;

        this.clazz = this.getClass();
    }

    public boolean wasCorrect(){return this.correct;}
}
