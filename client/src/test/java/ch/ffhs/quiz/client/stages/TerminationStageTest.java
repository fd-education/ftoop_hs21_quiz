package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.*;

class TerminationStageTest {
    static ServerSocket server;

    static Client client;
    static Connection serverConnection;
    static InputHandler inputHandler;
    static UserInterface ui;

    static TerminationStage terminationStage;

    @BeforeAll
    static void setup() throws Exception{
        server = new ServerSocket(3141);

        client = new Client("localhost", 3141);
        serverConnection = new ConnectionImpl(client.getOutputStream(), client.getInputStream());
        inputHandler = new InputHandler();
        ui = new UserInterface();

        terminationStage = new TerminationStage(client, serverConnection, inputHandler, ui);
    }


    @AfterAll
    static void teardown() throws IOException {
        server.close();
    }

    @Test
    void ctorTest_CONNECTION_NULL(){
        assertThrows(NullPointerException.class, () -> new TerminationStage(client, null, inputHandler, ui));

        try{
            new TerminationStage(client, null, inputHandler, ui);
        } catch(NullPointerException NPE){
            assertEquals("connection must not be null", NPE.getMessage());
        }
    }

    @Test
    void ctorTest_CLIENT_NULL(){
        assertThrows(NullPointerException.class, () -> new TerminationStage(null, serverConnection, inputHandler, ui));

        try{
            new TerminationStage(null, serverConnection, inputHandler, ui);
        } catch(NullPointerException NPE){
            assertEquals("client must not be null", NPE.getMessage());
        }
    }

    @Test
    void ctorTest_INPUTHANDLER_NULL(){
        assertThrows(NullPointerException.class, () -> new TerminationStage(client, serverConnection, null, ui));

        try{
            new TerminationStage(client, serverConnection, null, ui);
        } catch(NullPointerException NPE){
            assertEquals("inputHandler must not be null", NPE.getMessage());
        }
    }

    @Test
    void ctorTest_USERINTERFACE_NULL(){
        assertThrows(NullPointerException.class, () -> new TerminationStage(client, serverConnection, inputHandler, null));

        try{
            new TerminationStage(client, serverConnection, inputHandler, null);
        } catch(NullPointerException NPE){
            assertEquals("ui must not be null", NPE.getMessage());
        }
    }

    @Test
    void createInitialUserInterface() throws Exception{
        String EXPECTED = """
                [H[2J[1;34m╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
                ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
                [0m[s[26E[1;34m╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
                ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝
                [0m[u[1E[6E[1;34m                                   ███████╗███╗   ██╗██████╗ ███████╗
                                                   ██╔════╝████╗  ██║██╔══██╗██╔════╝
                                                   █████╗  ██╔██╗ ██║██║  ██║█████╗
                                                   ██╔══╝  ██║╚██╗██║██║  ██║██╔══╝
                                                   ███████╗██║ ╚████║██████╔╝███████╗
                                                   ╚══════╝╚═╝  ╚═══╝╚═════╝ ╚══════╝
                [0m[2E[1;34m                           Danke für die Teilnahme an diesem Spiel ! ~N.S.F.[0m[H[2J""";

        String output = tapSystemOut(() -> terminationStage.createInitialUserInterface());
        assertEquals(EXPECTED, output);
    }
}