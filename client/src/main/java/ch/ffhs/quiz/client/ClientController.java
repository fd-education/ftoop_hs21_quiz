package ch.ffhs.quiz.client;

import ch.ffhs.quiz.client.stages.GameStage;
import ch.ffhs.quiz.client.stages.InitializationStage;
import ch.ffhs.quiz.client.stages.TerminationStage;
import ch.ffhs.quiz.client.ui.AnsiTerminal;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;

import java.io.IOException;

import static java.lang.Integer.parseInt;

/**
 * Class initializes all the required objects for the clientside to run.
 * Orchestrates the stages to run in the predefined order.
 */
public class ClientController{
    private static final InputHandler inputHandler = new InputHandler();


    public static void main(String[] args) throws IOException{
        String host = System.getProperty("host", "localhost");
        int port = parseInt(System.getProperty("port", "3141"));

        if(host == null || host.isBlank()){
            System.err.printf("Host %s not valid. Stopping...", host);
            return;
        }

        if(port < 0 || port > 65535) {
            System.err.printf("Port %d out of valid range. Stopping...", port);
            return;
        }

        Client client = new Client(host, port);
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
