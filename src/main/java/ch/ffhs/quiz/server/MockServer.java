package ch.ffhs.quiz.server;

import ch.ffhs.quiz.logger.Logger;
import ch.ffhs.quiz.messages.Message;
import ch.ffhs.quiz.messages.*;

import java.io.*;
import java.net.*;

public class MockServer {
    private static Socket client;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        start();
    }

    public static void start() throws IOException, ClassNotFoundException{
        try(ServerSocket server = new ServerSocket(3141)) {
            Logger.log("Server started ...");

            client = server.accept();
            Logger.log("Client " + client + " connected to the server");

            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
            Logger.log("Set up in- and output streams for client" + client);


            while(true) {
                Logger.log("Waiting for client messages . . .");

                Message input = (Message) in.readObject();

                Logger.log("Client sent " + input.getClass() + ": \"" + input.getText() + "\"");

                if (".".equals(input.getText())) {
                    out.writeObject("Server closing. Bye...");
                    Logger.log("Stopping server...");
                    break;
                }

                if(input instanceof NameMessage) {
                    out.writeObject("Hello " + input + " . . .");
                    out.writeObject("Here's your Question: ");
                    out.writeObject("""
                            \n
                            Ungefähr 500 Millionen ...?
                            A* Zugvögel ziehen jedes Jahr durch Deutschland
                            B Euro geben Deutschlandreisende jährlich für Übernachtungen aus
                            C Menschen werden für eine erdumspannende Kette benötigt
                            """);

                    Logger.log("Sent response. . . ");

                    continue;
                }

                if(input instanceof AnswerMessage){
                    out.writeObject("Your responded correctly");
                }
                Logger.log("Clients message could not be processed.");
            }
        }
    }
}
