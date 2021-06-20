package ch.ffhs.quiz;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.client.stages.GameStage;
import ch.ffhs.quiz.client.stages.InitializationStage;
import ch.ffhs.quiz.client.stages.TerminationStage;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;
import ch.ffhs.quiz.game.Game;
import ch.ffhs.quiz.game.gamesteps.impl.*;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.game.player.PlayerFactory;
import ch.ffhs.quiz.logger.LoggerUtils;
import ch.ffhs.quiz.messages.ScoreboardEntry;
import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.questions.QuestionFactory;
import ch.ffhs.quiz.server.Server;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.stubbing.Answer;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.mockito.Mockito.*;

class E2ETest {
    @Test
    void e2e() throws Exception {
        // Configure game
        Game game = Game.builder()
                .withSetupSteps(
                        NotifyGameStartsStep.class,
                        ConfirmNamesStep.class
                )
                .withRoundSteps(
                        SendQuestionStep.class,
                        ReceiveResponsesStep.class,
                        EvaluateResponsesStep.class,
                        FeedbackStep.class,
                        RoundSummaryStep.class
                )
                .withTeardownSteps(
                        DisconnectPlayersStep.class
                ).build();

        List<Question> questions = QuestionFactory.questionBuilder("testQuestions/test_questions_2.txt").subList(0, 3);

        // No noise
        LoggerUtils.setGlobalLogLevel(Level.OFF);

        // Start the game server
        Server server = new Server(3141);

        final Thread gameThread = new Thread(() -> {
            List<Player> players = PlayerFactory.connectPlayers(server, 2);
            game.play(players, questions);
        });
        gameThread.start();

        // Set up in- and output for two players
        InputHandler inputHandler1 = mock(InputHandler.class);
        UserInterface userInterface1 = mock(UserInterface.class);
        Client client1 = new Client("localhost", 3141);
        Connection con1 = new ConnectionImpl(client1.getOutputStream(), client1.getInputStream());

        InputHandler inputHandler2 = mock(InputHandler.class);
        UserInterface userInterface2 = mock(UserInterface.class);
        Client client2 = new Client("localhost", 3141);
        Connection con2 = new ConnectionImpl(client2.getOutputStream(), client2.getInputStream());

        // Set the name for both players
        when(userInterface1.proceed()).thenReturn(userInterface1);
        when(inputHandler1.getUserName()).thenReturn("Nicola");
        new InitializationStage(client1, con1, inputHandler1, userInterface1).process();

        when(userInterface2.proceed()).thenReturn(userInterface2);
        when(inputHandler2.getUserName()).thenReturn("Fabian");
        new InitializationStage(client2, con2, inputHandler2, userInterface2).process();

        Runnable client1Runnable = () -> new GameStage(client1, con1, inputHandler1, userInterface1).process();
        Runnable client2Runnable = () -> new GameStage(client2, con2, inputHandler2, userInterface2).process();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Give correct answer for player 2
        when(inputHandler1.awaitUserAnswer()).thenReturn(0);
        when(inputHandler2.awaitUserAnswer()).thenReturn(1);

        Future<?> future1 = executorService.submit(client1Runnable);
        Future<?> future2 = executorService.submit(client2Runnable);
        future1.get();
        future2.get();

        // Give correct Answer for both players, but player 2 is slower
        when(inputHandler1.awaitUserAnswer()).thenReturn(2);
        when(inputHandler2.awaitUserAnswer()).then((Answer<Integer>) waitingAnswer -> {
            Thread.sleep(10);
            return 2;
        });

        future1 = executorService.submit(client1Runnable);
        future2 = executorService.submit(client2Runnable);
        future1.get();
        future2.get();

        // Both players give wrong answer
        when(inputHandler1.awaitUserAnswer()).thenReturn(2);
        when(inputHandler2.awaitUserAnswer()).thenReturn(1);

        future1 = executorService.submit(client1Runnable);
        future2 = executorService.submit(client2Runnable);
        future1.get();
        future2.get();

        // Shutdown stage for both players
        client1Runnable = () -> new TerminationStage(client1, con1, inputHandler1, userInterface1).process();
        client2Runnable = () -> new TerminationStage(client2, con2, inputHandler2, userInterface2).process();
        future1 = executorService.submit(client1Runnable);
        future2 = executorService.submit(client2Runnable);
        future1.get();
        future2.get();

        executorService.shutdown();


        assertTimeoutPreemptively(Duration.ofMillis(100), (Executable) gameThread::join);


        // Verify that the correct UI Methods were called.
        verify(userInterface1).welcomeAndExplain();
        verify(userInterface2).welcomeAndExplain();
        verify(userInterface1).welcomePlayerPersonally("Nicola");
        verify(userInterface2).welcomePlayerPersonally("Fabian");
        verify(userInterface1, times(3)).countdown();
        verify(userInterface2, times(3)).countdown();

        String question = "Was ist die Wurzel von 4?";

        List<String> answers = List.of("A 3", "B 2", "C 1");
        verify(userInterface1).printQuestion(question, answers);
        verify(userInterface2).printQuestion(question, answers);
        verify(userInterface1).markChosenAnswer(question, answers, 0);
        verify(userInterface1).markCorrectAndChosenAnswer(question, answers, 0, 1);
        verify(userInterface2).markChosenAnswer(question, answers, 1);
        verify(userInterface2).markCorrectAndChosenAnswer(question, answers, 1, 1);
        verify(userInterface1).printPlayerWasWrong("Fabian");
        verify(userInterface2).printPlayerHasWon();
        List<ScoreboardEntry> scoreboardEntries = List.of(
                new ScoreboardEntry("Fabian", 1),
                new ScoreboardEntry("Nicola", 0)
        );
        verify(userInterface1).printScoreboard(scoreboardEntries, "Nicola");
        verify(userInterface2).printScoreboard(scoreboardEntries, "Fabian");

        question = "Was ist die Hauptstadt der Schweiz?";

        answers = List.of("A Lausanne", "B Lausanne", "C Lausanne");
        verify(userInterface1).printQuestion(question, answers);
        verify(userInterface2).printQuestion(question, answers);
        verify(userInterface1).markChosenAnswer(question, answers, 2);
        verify(userInterface1).markCorrectAndChosenAnswer(question, answers, 2,2);
        verify(userInterface2).markChosenAnswer(question, answers, 2);
        verify(userInterface2).markCorrectAndChosenAnswer(question, answers, 2, 2);
        verify(userInterface1).printPlayerHasWon();
        verify(userInterface2).printPlayerOnlyWasCorrect("Nicola");

        question = "Wie ist der Vorname von Einstein?";

        answers = List.of("A Albert", "B Kurt", "C Henry");
        verify(userInterface1).printQuestion(question, answers);
        verify(userInterface2).printQuestion(question, answers);
        verify(userInterface1).markChosenAnswer(question, answers, 2);
        verify(userInterface1).markCorrectAndChosenAnswer(question, answers, 2, 0);
        verify(userInterface2).markChosenAnswer(question, answers, 1);
        verify(userInterface2).markCorrectAndChosenAnswer(question, answers, 1, 0);
        verify(userInterface2).printNooneCorrect();
        verify(userInterface1).printNooneCorrect();
        // The scoreboard stays the same when no player got a point
        scoreboardEntries = List.of(
                new ScoreboardEntry("Nicola", 1),
                new ScoreboardEntry("Fabian", 1)
        );
        verify(userInterface1, times(2)).printScoreboard(scoreboardEntries, "Nicola");
        verify(userInterface2, times(2)).printScoreboard(scoreboardEntries, "Fabian");

        verify(userInterface1).printEnd();
        verify(userInterface2).printEnd();
    }
}
