package Quiz.Client.ui;


public class ClientController {
    public static void main(String[] args) {
        String welcome = new AnsiBuilder(TextInterface.WELCOME.getComponent()).font(AnsiBuilder.Color.BLUE, true).create();
        System.out.println(welcome);
    }
}
