package server.gamesteps;

import server.GameContext;
import server.player.Player;

public class SendQuestionStep extends GameStep {

    @Override
    protected void handlePlayer(GameContext gameContext, Player player) {
        player.send(gameContext.getRoundContext().getCurrentQuestion());
    }
}
