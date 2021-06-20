package ch.ffhs.quiz.messages;

/**
 * Used to signal a state of readiness.
 */
public class ReadyMessage extends Message{
    private final boolean ready;

    public ReadyMessage(boolean ready){
        this.ready = ready;
    }

    /**
     * Always returns true to signal readiness.
     *
     * @return true
     */

    @SuppressWarnings("SameReturnValue")
    public boolean isReady(){
        return ready;
    }
}
