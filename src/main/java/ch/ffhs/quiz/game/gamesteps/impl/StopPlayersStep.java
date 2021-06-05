package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.gamesteps.GameStep;
import ch.ffhs.quiz.game.player.Player;

import java.io.IOException;

public class StopPlayersStep extends GameStep {
    public StopPlayersStep(GameContext gameContext) {
        super(gameContext);
    }

    @Override
    protected void handlePlayer(Player player) {
        try {
            player.stop();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't stop player %s".formatted(player.getName()));
        }
    }
}
