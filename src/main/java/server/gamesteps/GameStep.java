package server.gamesteps;

import server.GameContext;
import server.player.Player;

public abstract class GameStep {
    protected void prepare(GameContext gameContext) {}
    protected abstract void handlePlayer(GameContext gameContext, Player player);
    protected void postHook(GameContext gameContext) {}
    public final void process(GameContext gameContext) {
        prepare(gameContext);
        for (Player player: gameContext.getPlayers())
            handlePlayer(gameContext, player);
        postHook(gameContext);
    }
}
