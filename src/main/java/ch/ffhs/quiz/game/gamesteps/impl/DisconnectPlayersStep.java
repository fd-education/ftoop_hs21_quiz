package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.gamesteps.GameStep;
import ch.ffhs.quiz.game.player.Player;

import java.io.IOException;

/**
 * Disconnect all players from the server
 */
public class DisconnectPlayersStep extends GameStep {
    public DisconnectPlayersStep(GameContext gameContext) {
        super(gameContext);
    }

    // Disconnect a given player from the server
    @Override
    protected void handlePlayer(Player player) {
        try {
            player.disconnect();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't stop player %s".formatted(player.getName()));
        }
    }
}
