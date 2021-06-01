package ch.ffhs.quiz.client;

import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;
import ch.ffhs.quiz.messages.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client{
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;
    private final ConnectionImpl serverConnection;

    public Client(String host, int port) throws IOException{

        this.client = new Socket(host, port);

        this.out = new PrintWriter(client.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        this.serverConnection = new ConnectionImpl(out, in);
    }

    public void connectToGameServer(String host, int port, Message nameMessage) throws IOException, ClassNotFoundException{
        client = new Socket(host, port);

        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);

        serverConnection.send(nameMessage);
        System.out.println("Connected");
    }

    public void closeConnection() throws IOException{
        if(!client.isClosed()) client.close();
        in.close();
        out.close();
    }

    public BufferedReader getInput(){return in;}

    public PrintWriter getOutput(){return out;}
}