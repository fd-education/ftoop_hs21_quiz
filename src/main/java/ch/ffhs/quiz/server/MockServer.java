package ch.ffhs.quiz.server;

import ch.ffhs.quiz.logger.LoggerUtils;
import ch.ffhs.quiz.messages.Message;
import ch.ffhs.quiz.messages.*;

import java.util.logging.Logger;
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
        Logger logger = LoggerUtils.getLogger();
        try(ServerSocket server = new ServerSocket(3141)) {
            logger.info("Server started ...");

            client = server.accept();
            logger.info("Client " + client + " connected to the server");

            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
            logger.info("Set up in- and output streams for client" + client);


            while(true) {
                logger.info("Waiting for client messages . . .");

                Message input = (Message) in.readObject();

                logger.info("Client sent " + input.getClass() + ": \"" + input.getText() + "\"");

                if (".".equals(input.getText())) {
                    out.writeObject("Server closing. Bye...");
                    logger.info("Stopping server...");
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

                    logger.info("Sent response. . . ");

                    continue;
                }

                if(input instanceof AnswerMessage){
                    out.writeObject("Your responded correctly");
                }
                logger.info("Clients message could not be processed.");
            }
        }
    }
}
