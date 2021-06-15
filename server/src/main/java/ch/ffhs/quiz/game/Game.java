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

/**
 * The game class is used to define what steps are played in a game and to then actually play the game.
 * <br>
 * With the game class, three parts of the game can be defined:
 * <ul>
 * <li>Setup steps are run once before the game</li>
 * <li>Round steps are run for every round played</li>
 * <li>Teardown steps are run after all rounds are played</li>
 * </ul>
 */
public class Game {
    private final GameStepsHolder setupSteps;
    private final GameStepsHolder roundSteps;
    private final GameStepsHolder teardownSteps;

    private Game(GameBuilder gameBuilder) {
        this.setupSteps = gameBuilder.setupSteps;
        this.roundSteps = gameBuilder.roundSteps;
        this.teardownSteps = gameBuilder.teardownSteps;
    }

    /**
     * Returns a game builder.
     *
     * @return the game builder
     */
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

    /**
     * Play the game with the given players and questions.<br>
     * Here, the game steps will be run as follows:
     * <ul>
     * <li>Setup steps are run once before the game</li>
     * <li>Main steps are run for every round played</li>
     * <li>Teardown steps are run after all rounds are played</li>
     * </ul>
     *
     * @param players   the players
     * @param questions the questions
     */
    public void play(List<Player> players, List<Question> questions) {
        Objects.requireNonNull(questions);
        Objects.requireNonNull(players);


        GameContext gameContext = new GameContext(players, questions);

        setupSteps.processAll(gameContext);
        while (!gameContext.isFinished()) {
            gameContext.nextRound();
            roundSteps.processAll(gameContext);
        }
        teardownSteps.processAll(gameContext);
    }

    /**
     * The Game builder is used to define the steps in one go without needing to use game step holders explicitly.
     */
    public static class GameBuilder {
        private GameStepsHolder setupSteps = GameStepsHolder.emptyHolder();
        private GameStepsHolder roundSteps = GameStepsHolder.emptyHolder();
        private GameStepsHolder teardownSteps = GameStepsHolder.emptyHolder();

        /**
         * Use these steps for the setup.
         *
         * @param gameStepClassArray the setup steps
         * @return this builder instance
         */
        @SafeVarargs
        public final GameBuilder withSetupSteps(final Class<? extends GameStep>... gameStepClassArray) {
            Objects.requireNonNull(gameStepClassArray);
            setupSteps = GameStepsHolder.of(gameStepClassArray);
            return this;
        }

        /**
         * Use these steps for every round in the game.
         *
         * @param gameStepClassArray the round steps
         * @return this builder instance
         */
        @SafeVarargs
        public final GameBuilder withRoundSteps(final Class<? extends GameStep>... gameStepClassArray) {
            Objects.requireNonNull(gameStepClassArray);
            roundSteps = GameStepsHolder.of(gameStepClassArray);
            return this;
        }

        /**
         * Use these steps for the teardown of the game.
         *
         * @param gameStepClassArray the teardown steps
         * @return this builder instance
         */
        @SafeVarargs
        public final GameBuilder withTeardownSteps(final Class<? extends GameStep>... gameStepClassArray) {
            Objects.requireNonNull(gameStepClassArray);
            teardownSteps = GameStepsHolder.of(gameStepClassArray);
            return this;
        }

        /**
         * Use this game step holder for the setup.
         *
         * @param setupSteps the setup steps holder
         * @return this builder instance
         */
        public final GameBuilder withSetupSteps(GameStepsHolder setupSteps) {
            Objects.requireNonNull(setupSteps);
            this.setupSteps = setupSteps;
            return this;
        }

        /**
         * Use this game step holder for every round.
         *
         * @param roundSteps the round steps holder
         * @return this builder instance
         */
        public final GameBuilder withRoundSteps(GameStepsHolder roundSteps) {
            Objects.requireNonNull(roundSteps);
            this.roundSteps = roundSteps;
            return this;
        }

        /**
         * Use this game step holder for the teardown.
         *
         * @param teardownSteps the teardown steps holder
         * @return this builder instance
         */
        public final GameBuilder withTeardownSteps(GameStepsHolder teardownSteps) {
            Objects.requireNonNull(teardownSteps);
            this.teardownSteps = teardownSteps;
            return this;
        }

        /**
         * Build and return the game instance.
         *
         * @return the game instance
         */
        public Game build() {
            return new Game(this);
        }
    }
}
