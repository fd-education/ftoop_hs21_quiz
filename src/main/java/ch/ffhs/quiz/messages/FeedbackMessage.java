package ch.ffhs.quiz.messages;

import java.util.Objects;

public class FeedbackMessage extends Message {
    private final boolean wasCorrect;

    public FeedbackMessage(String text, boolean wasCorrect) {
        Objects.requireNonNull(text);
        this.text = text;

        this.wasCorrect = wasCorrect;
    }

    public boolean wasCorrect() {
        return wasCorrect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FeedbackMessage that = (FeedbackMessage) o;

        return wasCorrect == that.wasCorrect;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (wasCorrect ? 1 : 0);
        return result;
    }
}
