package Quiz.MessageTypes;

import Quiz.Message;

import java.util.Objects;

public class AnswerMessage extends Message {

    public AnswerMessage(String text){
        Objects.requireNonNull(text);
        this.text = text;
    }
}
