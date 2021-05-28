package Quiz.Client;

import java.net.*;
import java.io.*;

public class Client {
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;
    private String id;

    public void connectToGame(String host, int port, String identification) throws IOException{
        client = new Socket(host, port);

        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);

        out.println(identification);

        this.id = identification;
    }

    public String sendMessage(String response) throws IOException {
            out.println(response);
            return in.readLine();
    }

    public void closeConnection() throws IOException{
        if(!client.isClosed()) client.close();
        in.close();
        out.close();
    }

    public String getId(){
        return this.id;
    }
}