package ch.ffhs.quiz.messages;

/**
 * Used to signal a state of readiness.
 */
public class ReadyMessage extends Message{
    /**
     * Always returns true to signal readiness.
     *
     * @return true
     */
    public boolean isReady(){
        return true;
    }
}
