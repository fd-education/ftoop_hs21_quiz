package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;
import ch.ffhs.quiz.logger.LoggerUtils;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.mockito.Mockito.*;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

class TerminationStageTest {
    static ServerSocket server;

    static Client client, mockClient;
    static Connection serverConnection, mockConnection;
    static InputHandler inputHandler, mockInputHandler;
    static UserInterface ui, mockUi;
    static Logger mockLogger;

    static TerminationStage terminationStage, terminateStageMockedArguments, mockTermStage;

    @BeforeAll
    static void setup() throws Exception{
        server = new ServerSocket(3141);

        client = new Client("localhost", 3141);
        serverConnection = new ConnectionImpl(client.getOutputStream(), client.getInputStream());
        inputHandler = new InputHandler();
        ui = new UserInterface();
        terminationStage = new TerminationStage(client, serverConnection, inputHandler, ui);

        mockLogger = mock(Logger.class);
        mockClient = mock(Client.class);
        mockConnection = mock(ConnectionImpl.class);
        mockInputHandler = mock(InputHandler.class);
        mockUi = mock(UserInterface.class);

        terminateStageMockedArguments = new TerminationStage(mockClient, mockConnection, mockInputHandler, mockUi);
        mockTermStage = mock(TerminationStage.class);
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
    void setupStageTest_positive(){
        terminationStage.setupStage();

        assertInstanceOf(Logger.class, terminationStage.logger);
    }

    @Test
    void setupStageTest_negative(){
        try(MockedStatic<LoggerUtils> mockLoggerUtils = mockStatic(LoggerUtils.class)){
            mockLoggerUtils.when(LoggerUtils::getUnnamedFileLogger).thenThrow(IOException.class);
            assertThrows(RuntimeException.class, () -> terminateStageMockedArguments.setupStage());

            try{
                terminateStageMockedArguments.setupStage();
            }catch(RuntimeException rtEx){
                assertEquals("Could not instantiate the file logger. null", rtEx.getMessage());
            }
        }
    }

    @Test
    void createInitialUserInterfaceTest_positive() throws Exception{
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


        terminationStage.setupStage();
        doNothing().when(mockLogger).info(anyString());
        String output = tapSystemOut(() -> terminationStage.createInitialUserInterface());
        assertEquals(EXPECTED, output);
    }

    @Test
    void terminateStageTest_positive() throws IOException{
        terminateStageMockedArguments.setupStage();

        terminateStageMockedArguments.terminateStage();
        verify(mockClient, times(1)).closeConnection();
        verify(mockConnection, times(1)).close();
    }

    @Test
    void terminateStageTest_negative() throws Exception{
        int status = catchSystemExit(() -> {
            terminateStageMockedArguments.setupStage();

            doThrow(IOException.class).when(mockClient).closeConnection();
            terminateStageMockedArguments.terminateStage();

            verify(mockUi, times(1)).printErrorScreen();
            verify(mockLogger, times(1)).warning(anyString());
        });

        assertEquals(-1, status);
    }
}