package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.gamesteps.MainGameStep;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.AnswerMessage;

import java.io.IOException;
import java.util.Objects;

public class ReceiveResponsesStep extends MainGameStep {

    public ReceiveResponsesStep(GameContext gameContext) {
        super(gameContext);
    }

    @Override
    protected void handlePlayer(Player player) {
        try {
            AnswerMessage answer = player.receive(AnswerMessage.class);
            Objects.requireNonNull(answer.getTimeStamp());
            roundContext.setPlayerAnswer(player, answer);
        } catch (NullPointerException | ClassCastException | IOException e) {
            throw new RuntimeException(String.format("Receiving data from player %s failed with exception %s%n", player.getId(), e.getCause()));
        }
    }
}
