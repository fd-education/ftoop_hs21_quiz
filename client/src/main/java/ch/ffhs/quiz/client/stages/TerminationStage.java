package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.connectivity.Connection;

import java.io.IOException;
import java.util.Objects;

/**
 * Class that provides all the logic that must be executed,
 * after the real game has ended.
 */
public class TerminationStage extends Stage {
    /**
     * Instantiates a new Termination stage.
     *
     * @param client       the client
     * @param connection   the connection
     * @param inputHandler the input handler
     * @param ui           the ui
     */
    public TerminationStage(final Client client, final Connection connection, final InputHandler inputHandler, final UserInterface ui){
        this.client = Objects.requireNonNull(client, "client must not be null");
        this.serverConnection = Objects.requireNonNull(connection, "connection must not be null");
        this.inputHandler = Objects.requireNonNull(inputHandler, "inputHandler must not be null");
        this.ui = Objects.requireNonNull(ui, "ui must not be null");
    }

    // Nothing to set up in the termination stage
    @Override
    protected void setupStage() {
    }

    // Thank the player for his/ her participation
    @Override
    protected void createInitialUserInterface() {
        ui.printEnd();
        logger.info("Said goodbye to the player.");
    }

    // No conversation in this stage
    @Override
    protected void handleConversation() {}

    // Close the client socket as well as the connection to the server
    @Override
    protected void terminateStage() {
        try {
            client.closeConnection();
            serverConnection.close();

            logger.info("All connections closed...\n\n");
        } catch(IOException ioEx){
            logger.warning("IOException: Closing of connections failed. \n" + ioEx.getMessage() + "\n\n");
            ui.printErrorScreen();
            System.exit(-1);
        }
    }
}
