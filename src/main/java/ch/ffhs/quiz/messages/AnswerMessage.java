package ch.ffhs.quiz.messages;

public class AnswerMessage extends Message {

    private final int chosenAnswer;

    public AnswerMessage(int chosenAnswer) {
        this.chosenAnswer = chosenAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerMessage that = (AnswerMessage) o;

        return chosenAnswer == that.chosenAnswer;
    }

    @Override
    public int hashCode() {
        return chosenAnswer;
    }

    public int getChosenAnswer() {
        return chosenAnswer;
    }
}
