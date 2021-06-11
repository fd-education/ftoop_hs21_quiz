package ch.ffhs.quiz.client;

import ch.ffhs.quiz.client.stages.GameStage;
import ch.ffhs.quiz.client.stages.InitializationStage;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;
import ch.ffhs.quiz.messages.FeedbackMessage;
import ch.ffhs.quiz.messages.Message;
import ch.ffhs.quiz.messages.NameMessage;
import ch.ffhs.quiz.messages.QuestionMessage;
import ch.ffhs.quiz.server.MockServer;

import java.io.IOException;

public class ClientController{
    private static final InputHandler inputHandler = new InputHandler();
    private static Client client;

    public static void main(String[] args) throws IOException{

        Client client = new Client("localhost", 3141);
        Connection con = new ConnectionImpl(client.getOutput(), client.getInput());
        new InitializationStage(client, con, inputHandler).process();

        GameStage gStage;
        do{
            gStage = new GameStage(client, con, inputHandler);
            gStage.process();
        } while(!gStage.wasLastRound());

        System.out.println("Client finished...");
    }
}
