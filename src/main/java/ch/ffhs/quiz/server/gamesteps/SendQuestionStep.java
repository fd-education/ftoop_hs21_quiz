package ch.ffhs.quiz.server.gamesteps;

import ch.ffhs.quiz.server.GameContext;
import ch.ffhs.quiz.server.player.Player;

public class SendQuestionStep extends GameStep {

    public SendQuestionStep(GameContext gameContext) {
        super(gameContext);
    }

    @Override
    protected void handlePlayer(Player player) {
        player.send(gameContext.getRoundContext().getCurrentQuestion());
    }
}
