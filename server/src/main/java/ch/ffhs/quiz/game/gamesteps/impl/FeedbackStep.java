package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.RoundContext;
import ch.ffhs.quiz.game.gamesteps.GameStep;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.FeedbackMessage;

/**
 * Sends a feedback to every player.
 * This message informs every player if his answer was correct and if he won the round.
 * Every feedback also contains the name of the winning player, which is empty when no player won.
 */
public class FeedbackStep extends GameStep {

    private final RoundContext roundContext;
    private Player winningPlayer;
    private String winningPlayerName;
    private boolean winningPlayerExists;

    public FeedbackStep(GameContext gameContext) {
        super(gameContext);
        this.roundContext = gameContext.getRoundContext();
    }

    // Sets up the static information on the winning player
    @Override
    protected void prepareStep() {
        winningPlayer = roundContext.getWinningPlayer();
        if (winningPlayer == null) {
            winningPlayerName = "";
            winningPlayerExists = false;
        } else {
            winningPlayerName = winningPlayer.getName();
            winningPlayerExists = true;
        }
    }

    // Builds and sends the feedback
    @Override
    protected void handlePlayer(Player player) {
        final boolean wasCorrect = roundContext.wasPlayerCorrect(player);

        boolean isFastestPlayer = false;
        if (winningPlayerExists && winningPlayer.equals(player)) {
            player.reward();
            isFastestPlayer = true;
        }
        player.send(new FeedbackMessage(wasCorrect, isFastestPlayer, winningPlayerName, roundContext.getQuestion().getCorrectAnswerNumber()));
    }
}
