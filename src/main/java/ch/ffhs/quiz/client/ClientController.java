package ch.ffhs.quiz.client;

import ch.ffhs.quiz.client.stages.GameStage;
import ch.ffhs.quiz.client.stages.InitializationStage;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;

import java.io.IOException;

public class ClientController{
    private static final InputHandler inputHandler = new InputHandler();

    public static void main(String[] args) throws IOException{

        Client client = new Client("localhost", 3141);
        Connection con = new ConnectionImpl(client.getOutput(), client.getInput());
        UserInterface ui = new UserInterface();
        new InitializationStage(client, con, inputHandler, ui).process();

        GameStage gStage;
        do{
            gStage = new GameStage(client, con, inputHandler, ui);
            gStage.process();
        } while(!gStage.wasLastRound());

        System.out.println("Client finished...");
    }
}
