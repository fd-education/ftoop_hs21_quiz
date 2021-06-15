package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.RoundContext;
import ch.ffhs.quiz.game.gamesteps.GameStep;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.AnswerMessage;

import java.time.Duration;
import java.time.Instant;

/**
 * Evaluates the answer for every player.
 * Adds every correct player to the game context and
 * sets the fastest correct player as the winning player of the round
 */
public class EvaluateResponsesStep extends GameStep {
    private final RoundContext roundContext;
    private Duration shortestDuration = Duration.ofDays(Integer.MAX_VALUE);
    private Player winningPlayer;

    public EvaluateResponsesStep(GameContext gameContext) {
        super(gameContext);
        this.roundContext = gameContext.getRoundContext();
    }

    // Checks if the player has a correct answer and if he is currently the fastest one
    @Override
    protected void handlePlayer(Player player) {
        AnswerMessage answer;
        try {
            answer = roundContext.getPlayerAnswer(player);
        } catch (IllegalArgumentException e) {
            logger.warning("Answer for player %s wasn't found, probably because it is missing.".formatted(player.getName()));
            return;
        }
        if (isAnswerCorrect(answer)) {
            roundContext.addCorrectPlayer(player);
            if (isEarliestAnswer(answer)) {
                shortestDuration = answer.getAnswerTime();
                winningPlayer = player;
            }
        }
    }

    // Checks if the given answer was earlier than the current earliest answer
    private boolean isEarliestAnswer(AnswerMessage answer) {
        return shortestDuration.compareTo(answer.getAnswerTime()) > 0;
    }

    // Checks if the answer was correct
    private boolean isAnswerCorrect(AnswerMessage answer) {
        int chosenAnswer = answer.getChosenAnswer();
        return roundContext.getQuestion().checkAnswer(chosenAnswer);
    }

    // Finally sets the winning player
    @Override
    protected void completeStep() {
        if (winningPlayer != null) {
            roundContext.setWinningPlayer(winningPlayer);
        }
    }
}
