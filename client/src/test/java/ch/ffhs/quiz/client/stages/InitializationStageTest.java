package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;
import ch.ffhs.quiz.logger.LoggerUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InitializationStageTest {
    static ServerSocket server;

    static Client client, mockClient;
    static Connection serverConnection, mockConnection;
    static InputHandler inputHandler, mockInputHandler;
    static UserInterface ui, mockUi;
    static Logger mockLogger;


    static InitializationStage initializationStage, mockinitializationStage, initializationStageMockedArguments;

    @BeforeAll
    static void setup() throws Exception{
        server = new ServerSocket(3141);

        client = new Client("localhost", 3141);
        serverConnection = new ConnectionImpl(client.getOutputStream(), client.getInputStream());
        inputHandler = new InputHandler();
        ui = new UserInterface();

        initializationStage = new InitializationStage(client, serverConnection, inputHandler, ui);

        mockClient = mock(Client.class);
        mockConnection = mock(ConnectionImpl.class);
        mockInputHandler = mock(InputHandler.class);
        mockUi = mock(UserInterface.class);

        initializationStageMockedArguments = new InitializationStage(mockClient, mockConnection, mockInputHandler, mockUi);
        mockinitializationStage = mock(InitializationStage.class);

        mockLogger = mock(Logger.class);
        LoggerUtils.setGlobalLogLevel(Level.OFF);
    }

    @BeforeEach()
    void resetMocks(){
        reset(mockClient, mockConnection, mockInputHandler, mockUi, mockLogger, mockinitializationStage);
    }

    @AfterAll
    static void teardown() throws IOException {
        server.close();
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

    @Test
    void createInitialUserInterfaceTest(){
        doNothing().when(mockUi).welcomeAndExplain();
        doNothing().when(mockLogger).info(anyString());

        initializationStageMockedArguments.createInitialUserInterface();

        verify(mockUi, times(1)).welcomeAndExplain();
    }
}