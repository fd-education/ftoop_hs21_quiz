package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.gamesteps.GameStep;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.NameMessage;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ConfirmNamesStep extends GameStep {

    public ConfirmNamesStep(GameContext gameContext) {
        super(gameContext);
    }

    @Override
    protected void prepareStep() {
        try {
            confirmPlayerNames(gameContext.getPlayers());
        } catch (IOException e) {
            throw new RuntimeException(String.format("Receiving names from players failed with exception %s%n", e.getCause()));
        }
    }

    @Override
    protected void handlePlayer(Player player) {

    }

    private void confirmPlayerNames(List<Player> players) throws IOException {
        List<String> takenNames = new ArrayList<>();

        boolean allPlayersHaveName = false;
        while (!allPlayersHaveName) {
            Map<Player, NameMessage> playerMessageMap = getPlayerMessageMap(players);
            final Comparator<Entry<Player, NameMessage>> byEarliestMessage = Comparator.comparing(
                    entry -> entry.getValue().getTimeStamp()
            );
            List<Entry<Player, NameMessage>> sortedPlayerMessageEntries = playerMessageMap
                    .entrySet().stream()
                    .sorted(byEarliestMessage)
                    .collect(Collectors.toList());

            for (Entry<Player, NameMessage> entry : sortedPlayerMessageEntries) {
                String name = entry.getValue().getText();
                Player player = entry.getKey();
                boolean nameIsConfirmed = !takenNames.contains(name);

                NameMessage nameMessage = new NameMessage(name);
                nameMessage.setConfirmed(nameIsConfirmed);
                player.send(nameMessage);

                if (nameIsConfirmed) {
                    takenNames.add(name);
                    player.setName(name);
                }
            }
            allPlayersHaveName = takenNames.size() == players.size();
        }
    }

    private Map<Player, NameMessage> getPlayerMessageMap(List<Player> players) throws IOException {
        Map<Player, NameMessage> playerAnswerMap = new HashMap<>();
        for (Player player : players) {
            if (player.getName() == null && player.hasMessage()) {
                NameMessage answer = player.receive(NameMessage.class);
                playerAnswerMap.put(player, answer);
            }
        }
        return playerAnswerMap;
    }

}
