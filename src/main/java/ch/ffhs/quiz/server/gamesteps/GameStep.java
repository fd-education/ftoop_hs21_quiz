package ch.ffhs.quiz.server.gamesteps;

import ch.ffhs.quiz.server.GameContext;
import ch.ffhs.quiz.server.RoundContext;
import ch.ffhs.quiz.server.player.Player;

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
        prepareStep();
        for (Player player: gameContext.getPlayers())
            handlePlayer(player);
        completeStep();
    }
}
