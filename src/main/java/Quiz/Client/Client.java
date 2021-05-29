package Quiz.Client;

import Quiz.Message;

import java.net.*;
import java.io.*;

public class Client {
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public void connectToGameServer(String host, int port, Message nameMessage) throws IOException, ClassNotFoundException{
        client = new Socket(host, port);

        in = new ObjectInputStream(client.getInputStream());
        out = new ObjectOutputStream(client.getOutputStream());

        sendMessage(nameMessage);
        System.out.println("Connected");
    }

    public void sendMessage(Message message) throws IOException, ClassNotFoundException {

        out.writeObject(message);

        Object input;
        while((input = in.readObject()) != null){
            System.out.println(input);
        }

        System.out.println("After sent message");
    }

    public void closeConnection() throws IOException{
        if(!client.isClosed()) client.close();
        in.close();
        out.close();
    }
}