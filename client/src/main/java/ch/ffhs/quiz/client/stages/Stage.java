package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.connectivity.Connection;

public abstract class Stage {
    protected InputHandler inputHandler;
    protected Client client;
    protected Connection serverConnection;
    protected UserInterface ui;

    protected abstract void setupStage();
    protected abstract void createInitialUserInterface();
    protected abstract void handleConversation();
    protected abstract void terminateStage();

    public final void process(){
        setupStage();
        createInitialUserInterface();
        handleConversation();
        terminateStage();
    }
}
