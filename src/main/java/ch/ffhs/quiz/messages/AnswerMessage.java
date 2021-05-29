package ch.ffhs.quiz.messages;

import java.util.Objects;

public class AnswerMessage extends Message {

    public AnswerMessage(String text){
        Objects.requireNonNull(text);
        this.text = text;

        this.clazz = this.getClass();
    }
}
