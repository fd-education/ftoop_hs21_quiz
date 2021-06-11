package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.RoundContext;
import ch.ffhs.quiz.game.gamesteps.GameStep;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.FeedbackMessage;

public class FeedbackStep extends GameStep {

    private final RoundContext roundContext;

    public FeedbackStep(GameContext gameContext) {
        super(gameContext);
        this.roundContext = gameContext.getRoundContext();
    }

    @Override
    protected void handlePlayer(Player player) {
        final String feedback;
        final Player winningPlayer = roundContext.getWinningPlayer();
        final boolean wasCorrect = roundContext.wasPlayerCorrect(player);

        boolean isFastestPlayer = false;
        String winningPlayerName = "";
        if (winningPlayer != null) {
            winningPlayerName = winningPlayer.getName();

            if (winningPlayer.equals(player)) {
                player.reward();
                isFastestPlayer = true;
            }
        }
        player.send(new FeedbackMessage(wasCorrect, isFastestPlayer, winningPlayerName));
    }
}
