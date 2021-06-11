package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.RoundContext;
import ch.ffhs.quiz.game.gamesteps.GameStep;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.AnswerMessage;

import java.time.Instant;

public class EvaluateResponsesStep extends GameStep {
    private final RoundContext roundContext;
    private Instant earliestAnswerTimestamp = Instant.MAX;

    public EvaluateResponsesStep(GameContext gameContext) {
        super(gameContext);
        this.roundContext = gameContext.getRoundContext();
    }

    @Override
    protected void handlePlayer(Player player) {
        AnswerMessage answer;
        try {
            answer = roundContext.getPlayerAnswer(player);
        } catch (IllegalArgumentException e) {
            logger.warning("Answer for player %s wasn't found, probably because it is missing.".formatted(player.getName()));
            return;
        }
        int chosenAnswer = answer.getChosenAnswer();
        Instant timestamp = answer.getTimeStamp();
        if (roundContext.getQuestion().checkAnswer(chosenAnswer)) {
            roundContext.addCorrectPlayer(player);
            if (timestamp.isBefore(earliestAnswerTimestamp)) {
                earliestAnswerTimestamp = timestamp;
                roundContext.setWinningPlayer(player);
            }
        }
    }

}
