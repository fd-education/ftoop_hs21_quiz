package ch.ffhs.quiz.messages;

public class ConfirmedNameMessage extends NameMessage {
    public ConfirmedNameMessage(String text) {
        super(text);
        this.confirmed = true;
    }
}
