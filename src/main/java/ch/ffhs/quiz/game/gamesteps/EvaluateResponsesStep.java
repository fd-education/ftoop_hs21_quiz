package ch.ffhs.quiz.game.gamesteps;

import ch.ffhs.quiz.messages.AnswerMessage;
import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.player.Player;

import java.time.Instant;

public class EvaluateResponsesStep extends GameStep {
    private Instant earliestAnswerTimestamp = Instant.MAX;

    public EvaluateResponsesStep(GameContext gameContext) {
        super(gameContext);
    }

    @Override
    protected void handlePlayer(Player player) {
        AnswerMessage answer = roundContext.getPlayerAnswer(player);
        int chosenAnswer = answer.getChosenAnswer();
        Instant timestamp = answer.getTimeStamp();
        if (roundContext.getCurrentQuestion().checkAnswer(chosenAnswer)) {
            roundContext.addCorrectPlayer(player);
            if (timestamp.isBefore(earliestAnswerTimestamp)) {
                earliestAnswerTimestamp = timestamp;
                roundContext.setWinningPlayer(player);
            }
        }
    }

}
