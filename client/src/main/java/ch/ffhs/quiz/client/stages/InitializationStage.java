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

    // There's nothing to set up in the initial phase
    @Override
    protected void setupStage(){}

    // Create the Welcome screen along with an explanation of the quiz
    @Override
    protected void createInitialUserInterface(){
        ui.welcomeAndExplain();
    }

    // Wait for the servers first response, ask for the players name to send it to the server,
    // handle the verification of the name
    @Override
    protected void handleConversation() {
        NameMessage nameMessage;

        try{
            // wait for the servers response, along with a graphical output
            while(!serverConnection.hasMessage() || !serverConnection.receive(ReadyMessage.class).isReady()){
                if(!ui.isWaiting()) ui.waiting(StaticTextComponent.WAITING_FOR_PLAYERS.getComponent());
            }

            // ask for the players name
            ui.proceed().askForName();

            do{
                // store the name and send it to the server
                String name = inputHandler.getUserName();
                serverConnection.send(new NameMessage(name));

                // receive the servers confirmation and analyze it
                nameMessage = serverConnection.receive(NameMessage.class);
                if(!nameMessage.isConfirmed()) ui.alertNameReserved(nameMessage.getText());

              // repeat if the name is already reserved for another player
            } while(!nameMessage.isConfirmed());

        } catch(IOException ioEx){
            ioEx.printStackTrace();
            throw new RuntimeException("This exception must not occur, because inputs get checked.", ioEx);
        }

        // tell the client socket its players name and welcome the player personally
        client.setPlayerName(nameMessage.getText());
        ui.welcomePlayerPersonally(nameMessage.getText());
    }

    // terminate the initialization stage by waiting for the game stage to start
    @Override
    protected void terminateStage(){
        // put the ui in a waiting state until it gets revoked by the game stage
        ui.waiting(StaticTextComponent.WAITING_FOR_PLAYERS.getComponent());
    }
}
