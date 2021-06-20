package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;
import ch.ffhs.quiz.logger.LoggerUtils;
import ch.ffhs.quiz.messages.AnswerMessage;
import ch.ffhs.quiz.messages.FeedbackMessage;
import ch.ffhs.quiz.messages.QuestionMessage;
import ch.ffhs.quiz.messages.RoundSummaryMessage;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.*;

class GameStageTest {
    static ServerSocket server;

    static Client client, mockClient;
    static Connection serverConnection, mockConnection;
    static InputHandler inputHandler, mockInputHandler;
    static UserInterface ui, mockUi;
    static Logger mockLogger;


    static GameStage gameStage, gameStageMockedArguments, mockGameStage;

    @BeforeAll
    static void setup() throws Exception{
        server = new ServerSocket(3141);

        client = new Client("localhost", 3141);
        serverConnection = new ConnectionImpl(client.getOutputStream(), client.getInputStream());
        inputHandler = new InputHandler();
        ui = new UserInterface();

        gameStage = new GameStage(client, serverConnection, inputHandler, ui);


        mockClient = mock(Client.class);
        mockConnection = mock(ConnectionImpl.class);
        mockInputHandler = mock(InputHandler.class);
        mockUi = mock(UserInterface.class);

        gameStageMockedArguments = new GameStage(mockClient, mockConnection, mockInputHandler, mockUi);
        mockGameStage = mock(GameStage.class);

        mockLogger = mock(Logger.class);
        LoggerUtils.setGlobalLogLevel(Level.OFF);
    }

    @BeforeEach()
    void resetMocks(){
        reset(mockClient, mockConnection, mockInputHandler, mockUi, mockLogger, mockGameStage);
    }

    @AfterAll
    static void teardown() throws IOException {
        server.close();
    }

    @Test
    void ctorTest_CONNECTION_NULL(){
        assertThrows(NullPointerException.class, () -> new GameStage(client, null, inputHandler, ui));

        try{
            new GameStage(client, null, inputHandler, ui);
        } catch(NullPointerException NPE){
            assertEquals("connection must not be null", NPE.getMessage());
        }
    }

    @Test
    void ctorTest_CLIENT_NULL(){
        assertThrows(NullPointerException.class, () -> new GameStage(null, serverConnection, inputHandler, ui));

        try{
            new GameStage(null, serverConnection, inputHandler, ui);
        } catch(NullPointerException NPE){
            assertEquals("client must not be null", NPE.getMessage());
        }
    }

    @Test
    void ctorTest_INPUTHANDLER_NULL(){
        assertThrows(NullPointerException.class, () -> new GameStage(client, serverConnection, null, ui));

        try{
            new GameStage(client, serverConnection, null, ui);
        } catch(NullPointerException NPE){
            assertEquals("inputHandler must not be null", NPE.getMessage());
        }
    }

    @Test
    void ctorTest_USERINTERFACE_NULL(){
        assertThrows(NullPointerException.class, () -> new GameStage(client, serverConnection, inputHandler, null));

        try{
            new GameStage(client, serverConnection, inputHandler, null);
        } catch(NullPointerException NPE){
            assertEquals("ui must not be null", NPE.getMessage());
        }
    }

    @Test
    void setupStageTest_positive() throws IOException{
        QuestionMessage mockQuestionMessage = mock(QuestionMessage.class);

        when(mockConnection.receive(QuestionMessage.class)).thenReturn(mockQuestionMessage);

        gameStageMockedArguments.setupStage();

        verify(mockConnection, times(1)).receive(QuestionMessage.class);
        verify(mockQuestionMessage, times(1)).getQuestion();
        verify(mockQuestionMessage, times(1)).getAnswers();
    }

    @Test
    void setupStageTest_negative() throws Exception{

        int status = catchSystemExit(() -> {
            when(mockConnection.receive(QuestionMessage.class)).thenThrow(IOException.class);
            gameStageMockedArguments.setupStage();

            verify(mockUi, times(1)).printErrorScreen();
            verify(mockLogger, times(1)).warning(anyString());
        });

        assertEquals(-1, status);
    }

    @Test
    void createInitialUserInterface(){

        doReturn(null).when(mockUi).proceed();
        doNothing().when(mockUi).countdown();
        doNothing().when(mockUi).printQuestion(anyString(), anyList());
        doNothing().when(mockUi).askForAnswer();

        gameStageMockedArguments.createInitialUserInterface();

        verify(mockUi, times(1)).proceed();
        verify(mockUi, times(1)).countdown();
        verify(mockUi, times(1)).printQuestion(null, List.of());
        verify(mockUi, times(1)).askForAnswer();
    }

    @Test
    void handleConversationTest_withAnswer_positive() throws Exception{
        FeedbackMessage mockFeedbackMessage = mock(FeedbackMessage.class);
        when(mockFeedbackMessage.getWinningPlayer()).thenReturn("Player1");

        when(mockInputHandler.awaitUserAnswer()).thenReturn(1);

        doNothing().when(mockUi).markChosenAnswer(anyString(), anyList(), anyInt());
        doNothing().when(mockConnection).send(mock(AnswerMessage.class));
        when(mockConnection.receive(FeedbackMessage.class)).thenReturn(mockFeedbackMessage);

        gameStageMockedArguments.handleConversation();

        verify(mockInputHandler, times(1)).awaitUserAnswer();
        verify(mockUi, times(1)).markChosenAnswer(null, List.of(), 1);
        verify(mockConnection, times(1)).send(any());
        verify(mockConnection, times(1)).receive(any());
    }

    @Test
    void handleConversationTest_withoutAnswer_positive() throws Exception{
        FeedbackMessage mockFeedbackMessage = mock(FeedbackMessage.class);
        when(mockFeedbackMessage.getWinningPlayer()).thenReturn("Player1");

        when(mockInputHandler.awaitUserAnswer()).thenReturn(-1);

        doNothing().when(mockUi).markChosenAnswer(anyString(), anyList(), anyInt());
        doNothing().when(mockConnection).send(mock(AnswerMessage.class));
        when(mockConnection.receive(FeedbackMessage.class)).thenReturn(mockFeedbackMessage);

        gameStageMockedArguments.handleConversation();

        verify(mockInputHandler, times(1)).awaitUserAnswer();
        verify(mockUi, times(0)).markChosenAnswer(null, null, 1);
        verify(mockConnection, times(1)).send(any());
        verify(mockConnection, times(1)).receive(FeedbackMessage.class);
    }

    @Test
    void handleConversationTest_negative() throws Exception{
        int status = catchSystemExit(() -> {
            when(mockInputHandler.awaitUserAnswer()).thenReturn(-1);
            doNothing().when(mockUi).markChosenAnswer(anyString(), anyList(), anyInt());
            doNothing().when(mockConnection).send(mock(AnswerMessage.class));
            when(mockConnection.receive(FeedbackMessage.class)).thenThrow(IOException.class);

            gameStageMockedArguments.handleConversation();

            verify(mockUi, times(1)).printErrorScreen();
            verify(mockLogger, times(1)).warning(anyString());
        });

        assertEquals(-1, status);
    }

    @Test
    void terminateStageTest_positive() throws Exception{
        RoundSummaryMessage mockRoundSummaryMessage = mock(RoundSummaryMessage.class);
        when(mockConnection.receive(RoundSummaryMessage.class)).thenReturn(mockRoundSummaryMessage);

        gameStageMockedArguments.terminateStage();

        verify(mockConnection, times(1)).receive(RoundSummaryMessage.class);
    }

    @Test
    void terminateStageTest_negative() throws Exception{

        int status = catchSystemExit(() -> {
            when(mockConnection.receive(RoundSummaryMessage.class)).thenThrow(IOException.class);

            gameStageMockedArguments.terminateStage();

            verify(mockUi, times(1)).printErrorScreen();
            verify(mockLogger, times(1)).warning(anyString());
        });

        assertEquals(-1, status);
    }
}