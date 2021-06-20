package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.ui.InputHandler;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.*;

class InitializationStageTest {
    static ServerSocket server;

    static Client client;
    static Connection serverConnection;
    static InputHandler inputHandler;
    static UserInterface ui;


    static InitializationStage initializationStage;

    @BeforeAll
    static void setup() throws Exception{
        server = new ServerSocket(3141);

        client = new Client("localhost", 3141);
        serverConnection = new ConnectionImpl(client.getOutputStream(), client.getInputStream());
        inputHandler = new InputHandler();
        ui = new UserInterface();

        initializationStage = new InitializationStage(client, serverConnection, inputHandler, ui);
    }

    @Test
    void ctorTest_CONNECTION_NULL(){
        assertThrows(NullPointerException.class, () -> new InitializationStage(client, null, inputHandler, ui));

        try{
            new InitializationStage(client, null, inputHandler, ui);
        } catch(NullPointerException NPE){
            assertEquals("connection must not be null", NPE.getMessage());
        }
    }

    @Test
    void ctorTest_CLIENT_NULL(){
        assertThrows(NullPointerException.class, () -> new InitializationStage(null, serverConnection, inputHandler, ui));

        try{
            new InitializationStage(null, serverConnection, inputHandler, ui);
        } catch(NullPointerException NPE){
            assertEquals("client must not be null", NPE.getMessage());
        }
    }

    @Test
    void ctorTest_INPUTHANDLER_NULL(){
        assertThrows(NullPointerException.class, () -> new InitializationStage(client, serverConnection, null, ui));

        try{
            new InitializationStage(client, serverConnection, null, ui);
        } catch(NullPointerException NPE){
            assertEquals("inputHandler must not be null", NPE.getMessage());
        }
    }

    @Test
    void ctorTest_USERINTERFACE_NULL(){
        assertThrows(NullPointerException.class, () -> new InitializationStage(client, serverConnection, inputHandler, null));

        try{
            new InitializationStage(client, serverConnection, inputHandler, null);
        } catch(NullPointerException NPE){
            assertEquals("ui must not be null", NPE.getMessage());
        }
    }

    @AfterAll
    static void teardown() throws IOException {
        server.close();
    }
}