package ch.ffhs.quiz.messages;

public class ReadyMessage extends Message{
    private final boolean ready;

    public ReadyMessage(){
        this.ready = true;
    }

    public boolean isReady(){
        return ready;
    }
}
