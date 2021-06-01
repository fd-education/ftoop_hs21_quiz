package ch.ffhs.quiz.client.stages;

public abstract class Stage {
    protected abstract void createUserInterface();
    protected abstract void handleUserInput();
    protected abstract void handleCommunication();

    public final void process(){
        createUserInterface();
        handleUserInput();
        handleCommunication();
    }
}
