package ch.ffhs.quiz.client;

import ch.ffhs.quiz.client.stages.InitializationStage;
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
        client = new Client("localhost", 3141);
        new InitializationStage().process();
        new Thread(() -> {
            try {
                MockServer.main(new String[0]);
            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }
        });
        //handleConversation();
    }

    private static void handleConversation() {
        try {


            String userName = inputHandler.getUserName();

            client.connectToGameServer("localhost", 3141, new NameMessage(userName));


        } catch(IOException | ClassNotFoundException ioEx){
            ioEx.printStackTrace();
        }
    }

    public static void handleResponse(Message message){
        if(message instanceof QuestionMessage){
            // ui entsprechende ausgabe
        }

        if(message instanceof FeedbackMessage){
            // ui entsprechende ausgabe
        }
    }
}
