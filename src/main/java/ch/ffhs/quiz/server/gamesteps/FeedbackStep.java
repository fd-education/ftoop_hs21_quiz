package ch.ffhs.quiz.server.gamesteps;

import ch.ffhs.quiz.messages.FeedbackMessage;
import ch.ffhs.quiz.messages.MessageUtils;
import ch.ffhs.quiz.server.GameContext;
import ch.ffhs.quiz.server.RoundContext;
import ch.ffhs.quiz.server.player.Player;

public class FeedbackStep extends GameStep {

    public FeedbackStep(GameContext gameContext) {
        super(gameContext);
    }

    @Override
    protected void handlePlayer(Player player) {
        final RoundContext roundContext = gameContext.getRoundContext();
        final String feedback;
        final Player winningPlayer = roundContext.getWinningPlayer();
        final boolean wasCorrect = roundContext.wasPlayerCorrect(player);
        if (wasCorrect) {
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
        player.send(new FeedbackMessage(feedback, wasCorrect));
    }
}
