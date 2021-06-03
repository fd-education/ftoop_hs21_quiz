package ch.ffhs.quiz.client;

import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;
import ch.ffhs.quiz.messages.Message;

import java.io.*;
import java.net.Socket;

public class Client{
    private Socket client;
    private OutputStream out;
    private InputStream in;
    private final ConnectionImpl serverConnection;

    public Client(String host, int port) throws IOException{

        this.client = new Socket(host, port);

        this.out = client.getOutputStream();
        this.in = client.getInputStream();

        this.serverConnection = new ConnectionImpl(out, in);
    }

    public void connectToGameServer(String host, int port, Message nameMessage) throws IOException, ClassNotFoundException{
        client = new Socket(host, port);

        in = client.getInputStream();
        out = client.getOutputStream();

        serverConnection.send(nameMessage);
        System.out.println("Connected");
    }

    public void closeConnection() throws IOException{
        if(!client.isClosed()) client.close();
        in.close();
        out.close();
    }

    public InputStream getInput(){return in;}

    public OutputStream getOutput(){return out;}
}