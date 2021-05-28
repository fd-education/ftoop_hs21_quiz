package server.gamesteps;

import server.GameContext;
import server.player.Player;
import server.RoundContext;

public class FeedbackStep extends GameStep {

    @Override
    protected void handlePlayer(GameContext gameContext, Player player) {
        final RoundContext roundContext = gameContext.getRoundContext();
        final String feedback;
        final Player winningPlayer = roundContext.getWinningPlayer();
        if (roundContext.wasPlayerCorrect(player)) {
            if (winningPlayer.equals(player)) {
                player.reward();
                feedback = "You have won this round!";
            } else {
                feedback = "Your answer was correct, but Player %s was faster!".formatted(player.getId());
            }
        } else {
            if (winningPlayer != null)
                feedback = "Your answer was not correct. Player %s won the round!".formatted(winningPlayer.getId());
            else
                feedback = "Nobody's answer was correct.";
        }
        player.send(feedback);
    }
}
