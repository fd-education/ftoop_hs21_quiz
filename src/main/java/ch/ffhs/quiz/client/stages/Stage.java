package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.connectivity.Connection;

public abstract class Stage {
    protected InputHandler inputHandler;
    protected Client client;
    protected Connection serverConnection;

    protected String input;
    protected String serverResponse;


    protected abstract void setupStage();

    protected abstract void createUserInterface();
    protected abstract void handleConversation();
    protected abstract void terminateStage();

    public final void process(){
        setupStage();
        createUserInterface();
        handleConversation();
        terminateStage();
    }
}
