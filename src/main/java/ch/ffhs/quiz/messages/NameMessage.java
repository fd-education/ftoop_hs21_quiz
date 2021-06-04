package ch.ffhs.quiz.messages;

import java.util.Objects;

public class NameMessage extends Message {
    private boolean confirmed;

    public NameMessage(String text) {
        Objects.requireNonNull(text);
        this.text = text;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        NameMessage that = (NameMessage) o;

        return confirmed == that.confirmed;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (confirmed ? 1 : 0);
        return result;
    }
}
