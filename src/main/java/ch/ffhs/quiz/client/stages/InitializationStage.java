package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.client.textinterface.TextInterface;
import ch.ffhs.quiz.client.ui.AnsiBuilder;
import ch.ffhs.quiz.client.ui.AnsiBuilder.*;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;
import ch.ffhs.quiz.messages.MessageUtils;
import ch.ffhs.quiz.messages.NameMessage;

import java.io.IOException;

public class InitializationStage extends Stage{

    public InitializationStage(Client client, Connection con, InputHandler inputHandler){
        this.client = client;
        this.serverConnection = con;
        this.inputHandler = inputHandler;
    }

    @Override
    protected void setupStage(){

    }

    @Override
    protected void createUserInterface(){
        String welcome = TextInterface.WELCOME.getComponent();
        new AnsiBuilder(welcome).font(Color.BLUE, Decoration.BOLD, true).print();

        String explanation = TextInterface.EXPLANATION.getComponent();
        new AnsiBuilder(explanation).font(Color.BLUE, Decoration.BOLD, true).print();
    }

    @Override
    protected void handleConversation() {

        try{
            NameMessage name;

            do{
                //TODO: create handling for repeated name requests
                input = inputHandler.getUserName();
                serverConnection.send(new NameMessage(input));

                name = serverConnection.receive(NameMessage.class);

            } while(!name.isConfirmed());

        } catch(IOException ioEx){
            ioEx.printStackTrace();
            throw new RuntimeException("This exception must not occur, because inputs get checked.", ioEx);
        }
    }
    @Override
    protected void terminateStage(){
    }
}
