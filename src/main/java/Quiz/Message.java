package Quiz;

import java.io.Serializable;
import java.time.Instant;

public abstract class Message implements Serializable {
    protected String text;
    protected Instant timeStamp;

    public Message(){
        this.timeStamp = Instant.now();
    }

    @Override
    public String toString(){
        return this.text;
    }

    public String getText(){return this.text;}
}
