package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.client.ui.AnsiTerminal;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.logger.LoggerUtils;

import java.util.Arrays;
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
     * The logger (logs messages into a file)
     */
    protected static final Logger logger = LoggerUtils.getUnnamedFileLogger();


    /**
     * Phase for general logic that must be executed before the stage can be processed
     */
    protected void setupStage(){}

    /**
     * Print the initial user interface for the respective stage
     */
    protected void createInitialUserInterface(){}

    /**
     * Process user input, handle server requests and responses
     */
    protected void handleConversation(){}

    /**
     * Phase for general logic that must be executed after the stage was processed
     */
    protected void terminateStage(){}


    /**
     * Execute all the four phases of a stage in a predefined order
     */
    public final void process() {
        try {
            setupStage();
            createInitialUserInterface();
            handleConversation();
            terminateStage();
        } catch(Exception ex){
            logger.warning("Exception thrown during game process: " + ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
            ui.printErrorScreen();
            System.exit(-1);
        }
    }

    protected final void printErrorAndQuit(){
        try{
           Thread.sleep(200);
        }catch(InterruptedException ignored){}

        AnsiTerminal.clearTerminal();
        ui.printErrorScreen();
        System.exit(-1);
    }
}
