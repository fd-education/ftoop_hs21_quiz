package ch.ffhs.quiz.client;

import ch.ffhs.quiz.messages.Message;

import java.net.*;
import java.io.*;
import java.util.*;

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

    public List<Message> sendMessage(Message message) throws IOException, ClassNotFoundException {

        out.writeObject(message);

        System.out.println("After sent message");

        List<Message> response = new ArrayList<>();
        Object input;
        while(in.available() > 0){
            input = in.readObject();
            response.add((Message) input);
        }

        return response;

    }

    public void closeConnection() throws IOException{
        if(!client.isClosed()) client.close();
        in.close();
        out.close();
    }
}