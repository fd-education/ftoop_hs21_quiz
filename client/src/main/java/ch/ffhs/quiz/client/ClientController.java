package ch.ffhs.quiz.client;

import ch.ffhs.quiz.client.stages.GameStage;
import ch.ffhs.quiz.client.stages.InitializationStage;
import ch.ffhs.quiz.client.stages.TerminationStage;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;

import java.io.IOException;

public class ClientController{
    private static final InputHandler inputHandler = new InputHandler();

    public static void main(String[] args) throws IOException{

        Client client = new Client("localhost", 3141);
        Connection connection = new ConnectionImpl(client.getOutput(), client.getInput());
        UserInterface ui = new UserInterface();
        new InitializationStage(client, connection, inputHandler, ui).process();

        GameStage gStage;
        do{
            gStage = new GameStage(client, connection, inputHandler, ui);
            gStage.process();
        } while(!gStage.wasLastRound());

        new TerminationStage(client, connection, inputHandler, ui).process();
    }
}
