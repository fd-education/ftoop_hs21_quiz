package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.client.ui.components.text.StaticTextComponent;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.messages.NameMessage;
import ch.ffhs.quiz.messages.ReadyMessage;

import java.io.IOException;

public class InitializationStage extends Stage{
    

    public InitializationStage(Client client, Connection con, InputHandler inputHandler, UserInterface ui){
        this.client = client;
        this.serverConnection = con;
        this.inputHandler = inputHandler;
        this.ui = ui;
    }

    @Override
    protected void setupStage(){

    }

    @Override
    protected void createInitialUserInterface(){
        ui.welcomeAndExplain();
    }

    @Override
    protected void handleConversation() {
        NameMessage name;

        try{
            while(!serverConnection.receive(ReadyMessage.class).isReady()){
                ui.waiting(StaticTextComponent.WAITING_FOR_PLAYERS.getText());
            }

            ui.proceed().askForName();

            do{
                input = inputHandler.getUserName();
                serverConnection.send(new NameMessage(input));

                name = serverConnection.receive(NameMessage.class);

                if(!name.isConfirmed()) ui.alertNameReserved(name.getText());
            } while(!name.isConfirmed());

        } catch(IOException ioEx){
            ioEx.printStackTrace();
            throw new RuntimeException("This exception must not occur, because inputs get checked.", ioEx);
        }

        client.setPlayerName(name.getText());
        ui.welcomePlayerPersonally(name.getText());
    }

    @Override
    protected void terminateStage(){
        ui.await();
        ui.waiting(StaticTextComponent.WAITING_FOR_PLAYERS.getText());
    }
}
