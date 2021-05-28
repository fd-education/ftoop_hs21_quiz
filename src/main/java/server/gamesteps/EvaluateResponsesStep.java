package server.gamesteps;

import server.GameContext;
import server.player.Player;
import server.RoundContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EvaluateResponsesStep extends GameStep {
    private final Map<Player, Long> timestampMap = new HashMap<>();

    @Override
    protected void handlePlayer(GameContext gameContext, Player player) {
        try {
            int answer = Integer.parseInt(player.receive());
            Long timestamp = Long.valueOf(player.receive());
            final RoundContext roundContext = gameContext.getRoundContext();
            if (roundContext.getCurrentQuestion().checkAnswer(answer)) {
                roundContext.addCorrectPlayer(player);
                timestampMap.put(player, timestamp);
            }
        } catch (NumberFormatException | IOException e) {
            System.err.printf("Receiving data from player %s failed with exception %s%n", player.getId(), e.getCause());
        }
    }

    @Override
    protected void postHook(GameContext gameContext) {
        timestampMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .findFirst()
                .ifPresent(player -> gameContext.getRoundContext().setWinningPlayer(player));
    }
}
