package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.gamesteps.GameStep;
import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.ReadyMessage;

/**
 * Tells every player the game is ready to start
 */
public class NotifyGameStartsStep extends GameStep {
    public NotifyGameStartsStep(GameContext gameContext){
        super(gameContext);
    }

    @Override
    protected void handlePlayer(Player player) {
        player.send(new ReadyMessage(true));
    }
}
