package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.connectivity.Connection;

import java.util.logging.Logger;

/**
 * Abstract class to unify the game processing stages.
 */
public abstract class Stage {
    /**
     * The input handler.
     */
    protected InputHandler inputHandler;
    /**
     * The client socket.
     */
    protected Client client;
    /**
     * The server connection.
     */
    protected Connection serverConnection;
    /**
     * The user interface.
     */
    protected UserInterface ui;
    /**
     * The logger
     */
    protected Logger logger;

    /**
     * Phase for general logic that must be executed before the stage can be processed
     */
    protected abstract void setupStage();

    /**
     * Print the initial user interface for the respective stage
     */
    protected abstract void createInitialUserInterface();

    /**
     * Process user input, handle server requests and responses
     */
    protected abstract void handleConversation();

    /**
     * Phase for general logic that must be executed after the stage was processed
     */
    protected abstract void terminateStage();

    /**
     * Execute all the four phases of a stage in a predefined order
     */
    public final void process(){
        setupStage();
        createInitialUserInterface();
        handleConversation();
        terminateStage();
    }
}
