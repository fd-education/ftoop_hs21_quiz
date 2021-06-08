package ch.ffhs.quiz.game.gamesteps;

import ch.ffhs.quiz.logger.LoggerUtils;
import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.RoundContext;
import ch.ffhs.quiz.game.player.Player;

import java.util.logging.Logger;

public abstract class GameStep {
    protected final GameContext gameContext;

    public GameStep(GameContext gameContext) {
        this.gameContext = gameContext;
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