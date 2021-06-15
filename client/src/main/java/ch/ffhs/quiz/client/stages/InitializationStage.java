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

public class InitializationStage extends Stage{
    

    public InitializationStage(final Client client, final Connection connection, final InputHandler inputHandler, final UserInterface ui){
        this.client = Objects.requireNonNull(client, "client must not be null");;
        this.serverConnection = Objects.requireNonNull(connection, "connection must not be null");
        this.inputHandler = Objects.requireNonNull(inputHandler, "inputHandler must not be null");
        this.ui = Objects.requireNonNull(ui, "ui must not be null");
    }

    @Override
    protected void setupStage(){
        // nothing to setup
    }

    @Override
    protected void createInitialUserInterface(){
        ui.welcomeAndExplain();
    }

    @Override
    protected void handleConversation() {
        NameMessage nameMessage;

        try{
            while(!serverConnection.hasMessage() || !serverConnection.receive(ReadyMessage.class).isReady()){
                if(!ui.isWaiting()) ui.waiting(StaticTextComponent.WAITING_FOR_PLAYERS.getText());
            }

            ui.proceed().askForName();

            do{
                String name = inputHandler.getUserName();
                serverConnection.send(new NameMessage(name));

                nameMessage = serverConnection.receive(NameMessage.class);

                if(!nameMessage.isConfirmed()) ui.alertNameReserved(nameMessage.getText());
            } while(!nameMessage.isConfirmed());

        } catch(IOException ioEx){
            ioEx.printStackTrace();
            throw new RuntimeException("This exception must not occur, because inputs get checked.", ioEx);
        }

        client.setPlayerName(nameMessage.getText());
        ui.welcomePlayerPersonally(nameMessage.getText());
    }

    @Override
    protected void terminateStage(){
        ui.waiting(StaticTextComponent.WAITING_FOR_PLAYERS.getText());
    }
}
