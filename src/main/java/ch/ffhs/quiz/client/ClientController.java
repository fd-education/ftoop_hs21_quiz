package ch.ffhs.quiz.client;

import ch.ffhs.quiz.client.textinterface.*;
import ch.ffhs.quiz.messages.Message;
import ch.ffhs.quiz.messages.*;
import ch.ffhs.quiz.server.MockServer;

import java.io.IOException;
import java.util.*;

public class ClientController {
    private static final InputHandler inputHandler = new InputHandler();
    private static final Client client = new Client();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                MockServer.main(new String[0]);
            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }
        });
        handleConversation();
    }

    private static void handleConversation() {
        try {

            String welcome = Colors.BLUE_BOLD.colorText(TextInterface.WELCOME.getComponent());
            System.out.println(welcome);

            System.out.println(TextInterface.EXPLANATION.getComponent());

            String userName = inputHandler.getUserName();

            client.connectToGameServer("localhost", 3141, new NameMessage(userName));

            System.out.println("Sent name");

            String answer = inputHandler.getUserAnswer();
            List<Message> response = client.sendMessage(new AnswerMessage(answer));

            response.forEach(ClientController::handleResponse);

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
