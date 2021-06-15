package ch.ffhs.quiz.game;

import ch.ffhs.quiz.game.gamesteps.GameStep;
import ch.ffhs.quiz.game.gamesteps.GameStepsHolder;
import ch.ffhs.quiz.game.gamesteps.impl.*;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.game.player.PlayerFactory;
import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.questions.QuestionFactory;
import ch.ffhs.quiz.server.Server;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static java.lang.Integer.parseInt;

public class Game {
    private final GameStepsHolder setupSteps;
    private final GameStepsHolder mainSteps;
    private final GameStepsHolder teardownSteps;

    private Game(GameBuilder gameBuilder) {
        this.setupSteps = gameBuilder.setupSteps;
        this.mainSteps = gameBuilder.mainSteps;
        this.teardownSteps = gameBuilder.teardownSteps;
    }

    public static GameBuilder builder() {
        return new GameBuilder();
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("No filepath for the questions was specified. Stopping...");
            return;
        }
        String questionCatalogFilename = args[0];

        int playerCount = parseInt(System.getProperty("playerCount", "2"));
        int questionCount = parseInt(System.getProperty("questionCount", "5"));
        int port = parseInt(System.getProperty("port", "3141"));

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
                        DisconnectPlayersStep.class
                ).build();

        List<Question> questions = QuestionFactory.questionBuilder(questionCatalogFilename);
        if (questions.size() < questionCount) {
            System.err.printf("%d questions wanted, but only %d found in %s. Stopping...", questionCount, questions.size(), questionCatalogFilename);
            return;
        }
        questions = questions.subList(0, questionCount);

        Server server = new Server(port);
        List<Player> players = PlayerFactory.connectPlayers(server, playerCount);
        game.play(players, questions);
    }

    public void play(List<Player> players, List<Question> questions) {
        Objects.requireNonNull(questions);
        Objects.requireNonNull(players);


        GameContext gameContext = new GameContext(players, questions);

        setupSteps.processAll(gameContext);
        while (!gameContext.isFinished()) {
            gameContext.nextRound();
            mainSteps.processAll(gameContext);
        }
        teardownSteps.processAll(gameContext);
    }

    public static class GameBuilder {
        private GameStepsHolder setupSteps = GameStepsHolder.emptyHolder();
        private GameStepsHolder mainSteps = GameStepsHolder.emptyHolder();
        private GameStepsHolder teardownSteps = GameStepsHolder.emptyHolder();

        @SafeVarargs
        public final GameBuilder withSetupSteps(final Class<? extends GameStep>... gameStepClassArray) {
            Objects.requireNonNull(gameStepClassArray);
            setupSteps = GameStepsHolder.of(gameStepClassArray);
            return this;
        }

        @SafeVarargs
        public final GameBuilder withMainSteps(final Class<? extends GameStep>... gameStepClassArray) {
            Objects.requireNonNull(gameStepClassArray);
            mainSteps = GameStepsHolder.of(gameStepClassArray);
            return this;
        }

        @SafeVarargs
        public final GameBuilder withTeardownSteps(final Class<? extends GameStep>... gameStepClassArray) {
            Objects.requireNonNull(gameStepClassArray);
            teardownSteps = GameStepsHolder.of(gameStepClassArray);
            return this;
        }

        public final GameBuilder withSetupSteps(GameStepsHolder setupSteps) {
            Objects.requireNonNull(setupSteps);
            this.setupSteps = setupSteps;
            return this;
        }

        public final GameBuilder withMainSteps(GameStepsHolder mainSteps) {
            Objects.requireNonNull(mainSteps);
            this.mainSteps = mainSteps;
            return this;
        }

        public final GameBuilder withTeardownSteps(GameStepsHolder teardownSteps) {
            Objects.requireNonNull(teardownSteps);
            this.teardownSteps = teardownSteps;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }
}
