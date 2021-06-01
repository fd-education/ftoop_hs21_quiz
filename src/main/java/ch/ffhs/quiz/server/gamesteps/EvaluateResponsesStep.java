package ch.ffhs.quiz.server.gamesteps;

import ch.ffhs.quiz.messages.AnswerMessage;
import ch.ffhs.quiz.messages.MessageUtils;
import ch.ffhs.quiz.server.GameContext;
import ch.ffhs.quiz.server.player.Player;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class EvaluateResponsesStep extends GameStep {
    private final Map<Player, Instant> timestampPlayerMap = new HashMap<>();

    public EvaluateResponsesStep(GameContext gameContext) {
        super(gameContext);
    }

    @Override
    protected void handlePlayer(Player player) {
        try {
            isCorrectPlayer(player);
        } catch (NullPointerException | IllegalArgumentException | IOException e) {
            System.err.printf("Receiving data from player %s failed with exception %s%n", player.getId(), e.getCause());
        }
    }

    private void isCorrectPlayer(Player player) throws IOException {
        AnswerMessage answer = MessageUtils.parse(player.receive(), AnswerMessage.class);
        Objects.requireNonNull(answer.getTimeStamp());
        int chosenAnswer = answer.getChosenAnswer();
        Instant timestamp = answer.getTimeStamp();
        if (roundContext.getCurrentQuestion().checkAnswer(chosenAnswer)) {
            roundContext.addCorrectPlayer(player);
            timestampPlayerMap.put(player, timestamp);
        }
    }

    @Override
    protected void completeStep() {
        final Optional<Player> fastestCorrectPlayer = findFastestCorrectPlayer();
        fastestCorrectPlayer.ifPresent(player -> gameContext.getRoundContext().setWinningPlayer(player));
    }

    private Optional<Player> findFastestCorrectPlayer() {
        return timestampPlayerMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .findFirst();
    }
}
