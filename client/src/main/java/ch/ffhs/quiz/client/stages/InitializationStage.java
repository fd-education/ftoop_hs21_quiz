package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.client.ui.components.text.StaticTextComponent;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.messages.NameMessage;
import ch.ffhs.quiz.messages.ReadyMessage;

import java.io.IOException;
import java.util.Objects;

/**
 * The InitializationStage handles all the steps that need to be done
 * before the game can start.
 */
public class InitializationStage extends Stage{

    /**
     * Instantiates a new Initialization stage.
     *
     * @param client       the client
     * @param connection   the connection
     * @param inputHandler the input handler
     * @param ui           the ui
     */
    public InitializationStage(final Client client, final Connection connection, final InputHandler inputHandler, final UserInterface ui){
        this.client = Objects.requireNonNull(client, "client must not be null");
        this.serverConnection = Objects.requireNonNull(connection, "connection must not be null");
        this.inputHandler = Objects.requireNonNull(inputHandler, "inputHandler must not be null");
        this.ui = Objects.requireNonNull(ui, "ui must not be null");
    }

    // Create the Welcome screen along with an explanation of the quiz
    @Override
    protected void createInitialUserInterface(){
        ui.welcomeAndExplain();
        logger.info("Welcoming user.");
    }

    // Wait for the servers first response, ask for the players name to send it to the server,
    // handle the verification of the name
    @Override
    protected void handleConversation() {
        waitUntilServerReady();

        // ask for the players name
        ui.proceed().askForName();
        logger.info("Asking user for name.");

        String username = getAndVerifyUserName();

        // tell the client socket its players name and welcome the player personally
        client.setPlayerName(username);
        ui.welcomePlayerPersonally(username);
        logger.info(String.format("\"%s\" is confirmed. Welcoming player personally.", username));
    }

    // terminate the initialization stage by waiting for the game stage to start
    @Override
    protected void terminateStage(){
        // put the ui in a waiting state until it gets revoked by the game stage
        logger.info("Waiting for the game to start...");
        ui.waiting(StaticTextComponent.WAITING_FOR_PLAYERS.getComponent());

    }

    // Wait for the server to signal that it's ready to process information
    private void waitUntilServerReady(){
        try {
            // wait for the servers response, along with a graphical output
            logger.info("Waiting for server response to continue.");
            while (!serverConnection.hasMessage() || !serverConnection.receive(ReadyMessage.class).isReady()) {
                if (!ui.isWaiting()) ui.waiting(StaticTextComponent.WAITING_FOR_PLAYERS.getComponent());
            }
        } catch(IOException ioEx) {
            logger.warning("IOException: Receiving ready message from server failed. \n" + ioEx.getMessage());
            printErrorAndQuit();
        }
    }

    // Ask for the players name and the servers verification for it
    private String getAndVerifyUserName(){
        NameMessage nameMessage;

        try{
            do{
                // store the name and send it to the server
                String name = inputHandler.getUserName();
                serverConnection.send(new NameMessage(name));
                logger.info(String.format("Sending username \"%s\" and waiting for confirmation.", name));

                // receive the servers confirmation and analyse it
                nameMessage = serverConnection.receive(NameMessage.class);
                if(!nameMessage.isConfirmed()){
                    ui.alertNameReserved(nameMessage.getText());
                    logger.info(String.format("\"%s\" is reserved. Retry ...", name));
                }

                // repeat if the name is already reserved for another player
            } while(!nameMessage.isConfirmed());

            return nameMessage.getText();

        } catch(IOException ioEx){
            logger.warning("IOException: Receiving name confirmation failed. \n" + ioEx.getMessage());
            printErrorAndQuit();
        }

        return "";
    }
}
