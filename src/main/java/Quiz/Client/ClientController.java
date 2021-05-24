package Quiz.Client;


import Quiz.Client.TextInterface.*;

public class ClientController {
    public static void main(String[] args){
        String welcome = Colors.BLUE_BOLD.colorText(TextInterface.WELCOME.getComponent());
        System.out.println(welcome);
    }
}
