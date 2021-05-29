package ch.ffhs.quiz.server.gamesteps;

import ch.ffhs.quiz.server.GameContext;
import ch.ffhs.quiz.server.player.Player;

public class SendQuestionStep extends GameStep {

    @Override
    protected void handlePlayer(GameContext gameContext, Player player) {
        player.send(gameContext.getRoundContext().getCurrentQuestion());
    }
}
