package ch.ffhs.quiz.messages;

import java.util.Objects;

public class AnswerMessage extends Message {

    private final int chosenAnswer;

    public AnswerMessage(int chosenAnswer){
        this.chosenAnswer = chosenAnswer;
        Objects.requireNonNull(text);
        this.text = "";
    }

    public int getChosenAnswer() {
        return chosenAnswer;
    }
}
