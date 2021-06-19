package ch.ffhs.quiz.game.gamesteps;

import ch.ffhs.quiz.logger.LoggerUtils;
import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.player.Player;

import java.util.logging.Logger;

/**
 * A game step holds logic for a single, well-defined task that operates on all players.
 * The class is designed to be processed exactly once as this superclass makes no guarantees that instances can be reused.
 */
public abstract class GameStep {
    protected final GameContext gameContext;
    protected final Logger logger;

    /**
     * Instantiates a new Game step with a given game context.
     *
     * @param gameContext The game context the game step will work on
     */
    public GameStep(GameContext gameContext) {
        this.gameContext = gameContext;
        this.logger = LoggerUtils.getConsoleLogger();
    }

    /**
     * Prepare the game step.
     */
    protected void prepareStep() {}

    /**
     * Handle a player.
     *
     * @param player the player to be handled
     */
    protected void handlePlayer(Player player) {}

    /**
     * Complete the game step.
     */
    protected void completeStep() {}

    /**
     * The entry point of the game step.
     * A game step is processed in three sub steps:
     * 1. Preparation of the game step
     * 2. Handling of every player
     * 3. Completing the steps
     * All three sub steps are optional and are not required to be implemented by subclasses
     */
    public final void process() {
        logger.info("Starting game step: "+this.getClass().getSimpleName());
        logger.info("Preparing game step");
        prepareStep();
        logger.info("Handling players");
        for (Player player: gameContext.getPlayers()) {
            handlePlayer(player);
            logger.info("Handling player " + player.getId());
        }
        logger.info("Completing game step");
        completeStep();
        logger.info("Game step %s completed!".formatted(this.getClass().getSimpleName()));
    }
}
