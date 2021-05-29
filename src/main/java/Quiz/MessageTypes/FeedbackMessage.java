package Quiz.MessageTypes;

import Quiz.Message;

import java.util.Objects;

public class FeedbackMessage extends Message {
    private final boolean correct;

    public FeedbackMessage(String text, boolean correctAnswer){
        Objects.requireNonNull(text);
        this.text = text;

        this.correct = correctAnswer;
    }

    public boolean wasCorrect(){return this.correct;}
}
