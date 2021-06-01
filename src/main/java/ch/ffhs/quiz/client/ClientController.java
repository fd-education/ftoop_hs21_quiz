package ch.ffhs.quiz.client;

import ch.ffhs.quiz.client.textinterface.*;
import ch.ffhs.quiz.messages.Message;
import ch.ffhs.quiz.messages.*;
import ch.ffhs.quiz.server.MockServer;

import java.io.IOException;

import static java.lang.Integer.parseInt;

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

            String userName = inputHandler.getUsersName();

            client.connectToGameServer("localhost", 3141, new NameMessage(userName));

            System.out.println("Sent name");
//            String input = inputHandler.getInputLine();
//            Message message = (Message) client.sendMessage(new AnswerMessage(input));
//            handleResponse(message);

            String answer = inputHandler.getUsersAnswer();
            client.sendMessage(new AnswerMessage(parseInt(answer)));
            // System.out.println(message.getText());

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
