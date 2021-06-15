package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.connectivity.Connection;

import java.io.IOException;
import java.util.Objects;

public class TerminationStage extends Stage {

    public TerminationStage(final Client client, final Connection connection, final InputHandler inputHandler, final UserInterface ui){
        this.client = Objects.requireNonNull(client, "client must not be null");;
        this.serverConnection = Objects.requireNonNull(connection, "connection must not be null");
        this.inputHandler = Objects.requireNonNull(inputHandler, "inputHandler must not be null");
        this.ui = Objects.requireNonNull(ui, "ui must not be null");
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
            serverConnection.close();
        } catch(IOException ioEx){
            // TODO: handle differently
            throw new RuntimeException("May not be thrown.");
        }
    }
}
