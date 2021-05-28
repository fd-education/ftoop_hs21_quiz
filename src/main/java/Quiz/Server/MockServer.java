package Quiz.Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class MockServer {
    private static ArrayList<Socket> clients = new ArrayList<>();

    private static BufferedReader in;
    private static PrintWriter out;

    private static void handleConnection(Socket client) throws IOException {
        clients.add(client);
        System.out.printf("%s connected to the server... %s connection(s running\n", client.toString(), clients.size());
        sendMessage(client, String.format("%s", (clients.size() -1)));
    }

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(3141);

        while(true){
            try{
                System.out.println("Hello");
                Socket client = server.accept();
                if(!clients.contains(client)) {
                    handleConnection(client);
                }
                listen();
            } catch(IOException ioEx){
                ioEx.printStackTrace();
            }
        }
    }

    public static void listen() throws IOException{
        System.out.println("Listening ...");
        for(Socket client: clients){
            if(client.isClosed()){
                clients.remove(client);
                continue;
            }

            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String inputLine;
            while(!(inputLine = in.readLine()).isBlank()){
                handleConversation(inputLine);
                sendMessage(client, "Got your Message");
            }
        }
    }

    public static void handleConversation(String input){
        System.out.println(input);
    }

    public static void sendMessage(Socket client, String message) throws IOException{
        out = new PrintWriter(client.getOutputStream(), true);
        out.println(message);
    }

    public enum Reactions{
        FRIENDLY("Hello there"),
        UNFRIENDLY("GET AWAY"),
        BYE("Good Bye"),
        SEEYA("CU later"),
        SILENCE("Sshhhht...");

        private final String reaction;

        Reactions(String myReaction){
            this.reaction = myReaction;
        }

        public String getReaction(){
            return reaction;
        }
    }
}
