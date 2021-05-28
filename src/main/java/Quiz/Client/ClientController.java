package Quiz.Client;


import Quiz.Client.TextInterface.*;

import java.io.IOException;

public class ClientController {
    private static final InputHandler inputHandler = new InputHandler();
    private static final Client client = new Client();

    public static void main(String[] args){
        handleConversation();
    }

    private static void handleConversation(){
        try {
            String welcome = Colors.BLUE_BOLD.colorText(TextInterface.WELCOME.getComponent());
            System.out.println(welcome);

            String userName = inputHandler.getUsersName();

            client.connectToGame("localhost", 3141, userName);
            System.out.println(client.getId());

            while(true) {
                String input = inputHandler.getInputLine();
                String response = client.sendMessage(input);
                System.out.println(response);
            }

        } catch(IOException ioEx){
            ioEx.printStackTrace();
        }
    }
}
