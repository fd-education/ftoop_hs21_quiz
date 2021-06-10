package ch.ffhs.quiz;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.client.stages.GameStage;
import ch.ffhs.quiz.client.stages.InitializationStage;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.connectivity.impl.ConnectionImpl;
import ch.ffhs.quiz.game.Game;
import ch.ffhs.quiz.game.gamesteps.impl.*;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.game.player.PlayerFactory;
import ch.ffhs.quiz.questions.AnswerImpl;
import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.questions.QuestionImpl;
import ch.ffhs.quiz.server.Server;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class E2ETest {
    @Test
    void e2e() throws Exception {
        Game game = Game.builder()
                .withSetupSteps(
                        NotifyGameStartsStep.class,
                        ConfirmNamesStep.class
                )
                .withMainSteps(
                        SendQuestionStep.class,
                        ReceiveResponsesStep.class,
                        EvaluateResponsesStep.class,
                        FeedbackStep.class,
                        RoundSummaryStep.class
                )
                .withTeardownSteps(
                        StopPlayersStep.class
                ).build();

        // TODO: Remove asap
        Question question1 = new QuestionImpl("Question 1", List.of(
                new AnswerImpl("A", true),
                new AnswerImpl("B", false),
                new AnswerImpl("C", false)
        ));

        // TODO: Remove asap
        Question question2 = new QuestionImpl("Question 2", List.of(
                new AnswerImpl("A", false),
                new AnswerImpl("B", true),
                new AnswerImpl("C", false)
        ));
        Server server = new Server(3141);

        final Thread gameThread = new Thread(() -> {
            List<Player> players = PlayerFactory.connectPlayers(server, 2);
            game.play(players, List.of(question1, question2));
        });
        gameThread.start();

        InputHandler inputHandler1 = mock(InputHandler.class);
        Client client1 = new Client("localhost", 3141);
        Connection con1 = new ConnectionImpl(client1.getOutput(), client1.getInput());

        InputHandler inputHandler2 = mock(InputHandler.class);
        Client client2 = new Client("localhost", 3141);
        Connection con2 = new ConnectionImpl(client2.getOutput(), client2.getInput());

        when(inputHandler1.getUserName()).thenReturn("Nicola");
        new InitializationStage(client1, con1, inputHandler1).process();

        when(inputHandler2.getUserName()).thenReturn("Fabian");
        new InitializationStage(client2, con2, inputHandler2).process();

        final Runnable client1Runnable = () -> new GameStage(client1, con1, inputHandler1).process();
        final Runnable client2Runnable = () -> new GameStage(client2, con2, inputHandler2).process();

        when(inputHandler1.getUserAnswer()).thenReturn("A");
        when(inputHandler2.getUserAnswer()).thenReturn("B");

        Thread threadClient1 = new Thread(client1Runnable);
        Thread threadClient2 = new Thread(client2Runnable);
        threadClient1.start();
        threadClient2.start();
        threadClient1.join();
        threadClient2.join();

        when(inputHandler1.getUserAnswer()).thenReturn("A");
        when(inputHandler2.getUserAnswer()).thenReturn("B");

        threadClient1 = new Thread(client1Runnable);
        threadClient2 = new Thread(client2Runnable);
        threadClient1.start();
        threadClient2.start();
        threadClient1.join();
        threadClient2.join();

        assertTimeoutPreemptively(Duration.ofMillis(100), (Executable) gameThread::join);
    }
}
