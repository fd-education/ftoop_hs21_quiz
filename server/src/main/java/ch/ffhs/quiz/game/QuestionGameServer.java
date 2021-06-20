package ch.ffhs.quiz.game;

import ch.ffhs.quiz.game.gamesteps.impl.*;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.game.player.PlayerFactory;
import ch.ffhs.quiz.logger.LoggerUtils;
import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.questions.QuestionFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;

public class QuestionGameServer {
    public static void main(String[] args) {
        play(args);
    }

    public static void play(String[] args) {
        try {
            String questionCatalogFilename = getQuestionCatalogFilename(args);

            int playerCount = getPlayerCount();
            int questionCount = getQuestionCount();
            int port = getPort();

            Game game = buildGame();

            List<Question> questions = buildQuestions(questionCatalogFilename, questionCount);

            List<Player> players = connectPlayers(playerCount, port);
            game.play(players, questions);
        } catch (Exception exception) {
            LoggerUtils.getUnnamedFileLogger().warning(
                    "Exception thrown during setup of game: %s\n%s".formatted(exception.getMessage(),
                            Arrays.toString(exception.getStackTrace())));
            System.err.println("Error: " + exception.getMessage());
            System.err.println("See more logs at C:Windows/temp/FacadeQuiz/<datum>.log");
            System.err.println("Stopping...");
        }

    }

    private static List<Player> connectPlayers(int playerCount, int port) throws IOException {
        try (ServerSocket server = new ServerSocket(port)) {
            return PlayerFactory.connectPlayers(server, playerCount);
        }
    }

    private static String getQuestionCatalogFilename(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No filepath for the questions was specified.");
        }
        return args[0];
    }

    private static List<Question> buildQuestions(String questionCatalogFilename, int questionCount) {
        List<Question> questions = QuestionFactory.questionBuilder(questionCatalogFilename);
        if (questions.size() < questionCount) {
            throw new IllegalArgumentException(
                    "%d questions wanted, but only %d found in %s.".formatted(questionCount,
                            questions.size(), questionCatalogFilename));
        }
        Collections.shuffle(questions);
        return questions.subList(0, questionCount);
    }

    private static Game buildGame() {
        return Game.builder()
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
    }

    private static int getPort() {
        final String portString = System.getProperty("port", "3141");
        final int port;
        try {
            port = parseInt(portString);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Port is not a valid number.");
        }
        if (port < 1 || port > 65535)
            throw new IllegalArgumentException("Port must be between 1 and 65535, inclusive.");
        return port;
    }

    private static int getQuestionCount() {
        final String questionCountString = System.getProperty("questionCount", "5");
        final int questionCount;
        try {
            questionCount = parseInt(questionCountString);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Question count is not a valid number.");
        }
        if (questionCount < 1 || questionCount > 999)
            throw new IllegalArgumentException("Question count must be between 1 and 999, inclusive.");
        return questionCount;
    }

    private static int getPlayerCount() {
        final String playerCountString = System.getProperty("playerCount", "2");
        final int playerCount;
        try {
            playerCount = parseInt(playerCountString);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Player count is not a valid number.");
        }
        if (playerCount < 1 || playerCount > 999)
            throw new IllegalArgumentException("Player count must be between 1 and 999, inclusive.");
        return playerCount;
    }

}
