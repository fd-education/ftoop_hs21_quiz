package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.RoundContext;
import ch.ffhs.quiz.game.gamesteps.GameStep;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.AnswerMessage;

import java.io.IOException;

/**
 * Waits for a response from every player and stores them in the game context
 */
public class ReceiveResponsesStep extends GameStep {

    private final RoundContext roundContext;

    public ReceiveResponsesStep(GameContext gameContext) {
        super(gameContext);
        this.roundContext = gameContext.getRoundContext();
    }

    // Receive an answer for the given player and store in the round context.
    @Override
    protected void handlePlayer(Player player) {
        try {
            AnswerMessage answer = player.receive(AnswerMessage.class);
            roundContext.setPlayerAnswer(player, answer);
        } catch (NullPointerException | IOException e) {
            throw new RuntimeException(String.format("Receiving data from player %s failed with exception %s%n", player.getId(), e.getCause()));
        }
    }
}
