package ch.ffhs.quiz.server.gamesteps;

import ch.ffhs.quiz.logger.LoggerUtils;
import ch.ffhs.quiz.server.GameContext;
import ch.ffhs.quiz.server.RoundContext;
import ch.ffhs.quiz.server.player.Player;

import java.util.logging.Logger;

public abstract class GameStep {
    protected final GameContext gameContext;
    protected final RoundContext roundContext;

    public GameStep(GameContext gameContext) {
        this.gameContext = gameContext;
        this.roundContext = gameContext.getRoundContext();
    }
    protected void prepareStep() {}
    protected abstract void handlePlayer(Player player);
    protected void completeStep() {}
    public final void process() {
        Logger logger = LoggerUtils.getLogger();
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
        logger.info("Game step completed!");
    }
}
