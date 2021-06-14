package ch.ffhs.quiz.messages;

import java.util.Objects;

/**
 * Contains the name of a player and whether this name is confirmed to be unique.
 */
public class NameMessage extends Message {
    protected boolean confirmed;

    /**
     * Instantiates a new name message.
     * The name is set as not confirmed.
     *
     * @param name the unconfirmed name of the player
     */
    public NameMessage(String name) {
        Objects.requireNonNull(name);
        this.text = name;
        this.confirmed = false;
    }

    /**
     * Return whether the name of the player is confirmed to be unique.
     *
     * @return true if the name is confirmed to be unique, false otherwise.
     */
    public boolean isConfirmed() {
        return confirmed;
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
