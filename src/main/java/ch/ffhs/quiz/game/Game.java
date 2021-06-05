package ch.ffhs.quiz.game;

import ch.ffhs.quiz.game.gamesteps.GameStepsHolder;
import ch.ffhs.quiz.game.gamesteps.impl.*;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.game.player.PlayerFactory;
import ch.ffhs.quiz.questions.AnswerImpl;
import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.questions.QuestionImpl;
import ch.ffhs.quiz.server.Server;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Game {
    private final List<Player> players;
    private final List<Question> questions;
    private final GameStepsHolder setupSteps;
    private final GameStepsHolder mainSteps;
    private final GameStepsHolder teardownSteps;

    public Game(List<Question> questions, List<Player> players, GameStepsHolder setupSteps, GameStepsHolder mainSteps, GameStepsHolder teardownSteps) {
        Objects.requireNonNull(questions);
        Objects.requireNonNull(players);
        Objects.requireNonNull(setupSteps);
        Objects.requireNonNull(mainSteps);
        Objects.requireNonNull(teardownSteps);

        players = filterNullValues(players);
        if (players.size() < 1)
            throw new IllegalArgumentException("Cannot create a game with less than one player");

        this.players = players;
        this.questions = filterNullValues(questions);
        this.setupSteps = setupSteps;
        this.mainSteps = mainSteps;
        this.teardownSteps = teardownSteps;
    }

    public static void main(String[] args) throws IOException {
        GameStepsHolder setup = GameStepsHolder.of(ConfirmNamesStep.class);
        GameStepsHolder main = GameStepsHolder.of(
                SendQuestionStep.class,
                ReceiveResponsesStep.class,
                EvaluateResponsesStep.class,
                FeedbackStep.class,
                RoundSummaryStep.class
        );
        GameStepsHolder teardown = GameStepsHolder.of(
                StopPlayersStep.class
        );

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
        List<Player> players = PlayerFactory.connectPlayers(server, 2);
        new Game(List.of(question1, question2), players, setup, main, teardown).play();
    }

    private static <T> List<T> filterNullValues(List<T> list) {
        Objects.requireNonNull(list);

        return list.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void play() {
        GameContext gameContext = new GameContext(players, questions);
        setupSteps.processAll(gameContext);
        while (!gameContext.isFinished()) {
            gameContext.nextRound();
            mainSteps.processAll(gameContext);
        }
        teardownSteps.processAll(gameContext);
    }
}
