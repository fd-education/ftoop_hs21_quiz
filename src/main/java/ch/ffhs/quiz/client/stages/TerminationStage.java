package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.connectivity.Connection;

import java.io.IOException;

public class TerminationStage extends Stage {

    public TerminationStage(Client client, Connection con, InputHandler inputHandler, UserInterface ui){
        this.client = client;
        this.serverConnection = con;
        this.inputHandler = inputHandler;
        this.ui = ui;
    }

    @Override
    protected void setupStage() {
        // nothing to setup here
    }

    @Override
    protected void createInitialUserInterface() {
        ui.printEnd();
    }

    @Override
    protected void handleConversation() {
        // no conversation at this point
    }

    @Override
    protected void terminateStage() {
        try {
            client.closeConnection();
            serverConnection.stop();
        } catch(IOException ioEx){
            throw new RuntimeException("May not be thrown.");
        }
    }
}
