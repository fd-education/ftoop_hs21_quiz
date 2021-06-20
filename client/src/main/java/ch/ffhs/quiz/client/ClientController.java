package ch.ffhs.quiz.client;

import ch.ffhs.quiz.client.stages.GameStage;
import ch.ffhs.quiz.client.stages.InitializationStage;
import ch.ffhs.quiz.client.stages.TerminationStage;
import ch.ffhs.quiz.client.ui.AnsiTerminal;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.client.ui.UserInterfaceUtils;
import ch.ffhs.quiz.client.ui.components.interfaces.InterruptableUIComponent;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;

import java.io.IOException;

/**
 * Class initializes all the required objects for the clientside to run.
 * Orchestrates the stages to run in the predefined order.
 */
public class ClientController{
    private static final InputHandler inputHandler = new InputHandler();


    public static void main(String[] args) throws IOException{

        Client client = new Client("localhost", 3141);
        Connection connection = new ConnectionImpl(client.getOutputStream(), client.getInputStream());
        UserInterface ui = new UserInterface();

        addShutdownHook(client, connection, inputHandler, ui);

        // First do all the things specified in the InitializationStage
        new InitializationStage(client, connection, inputHandler, ui).process();

        // Then loop through the GameStage until the last round went through
        GameStage gStage;
        do{
            gStage = new GameStage(client, connection, inputHandler, ui);
            gStage.process();
        } while(!gStage.wasLastRound());

        // Finally terminate the game from clientside
        new TerminationStage(client, connection, inputHandler, ui).process();
    }

    // adds a shutdown hook to catch system.exit() and ctrl+c in a clean fashion
    private static void addShutdownHook(final Client client, final Connection connection, final InputHandler inputHandler, final UserInterface ui){
        Runtime.getRuntime().addShutdownHook(new Thread(()-> {

            // stop any currently running ui component (only the dynamic ones)
            ui.stopExecution();

            // await any other output
            try {
                Thread.sleep(100);
            } catch(InterruptedException iEx){
                Thread.currentThread().interrupt();
            }

            // clear the terminal and start the ordinary termination process
            AnsiTerminal.clearTerminal();
            new TerminationStage(client, connection, inputHandler, ui).process();
        }));
    }
}
