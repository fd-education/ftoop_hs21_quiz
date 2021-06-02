package ch.ffhs.quiz.messages;

public class AnswerMessage extends Message {

    private final int chosenAnswer;

    public AnswerMessage(int chosenAnswer){
        this.chosenAnswer = chosenAnswer;
        this.text = "";
    }

    public int getChosenAnswer() {
        return chosenAnswer;
    }
}
