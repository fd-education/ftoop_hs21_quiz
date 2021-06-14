package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.gamesteps.GameStep;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.ConfirmedNameMessage;
import ch.ffhs.quiz.messages.NameMessage;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Negotiates names with all players until every player has a unique name.
 */
public class ConfirmNamesStep extends GameStep {

    public ConfirmNamesStep(GameContext gameContext) {
        super(gameContext);
    }

    // Negotiates the names and rethrows unexpected Exceptions as Runtime Exceptions
    @Override
    protected void prepareStep() {
        try {
            confirmPlayerNames(gameContext.getPlayers());
        } catch (IOException e) {
            throw new RuntimeException(String.format("Receiving names from players failed with exception %s%n", e.getCause()));
        }
    }

    // Receives names from players and confirms them if they are unique and requests a new name otherwise
    private void confirmPlayerNames(List<Player> players) throws IOException {
        List<String> takenNames = new ArrayList<>();

        boolean allPlayersHaveName = false;
        while (!allPlayersHaveName) {
            Map<Player, NameMessage> playerMessageMap = receiveNewNameMessages(players);
            List<Entry<Player, NameMessage>> sortedPlayerMessageEntries = sortMessagesByTimestamp(playerMessageMap);

            for (Entry<Player, NameMessage> entry : sortedPlayerMessageEntries) {
                String name = entry.getValue().getText();
                Player player = entry.getKey();

                NameMessage nameMessage;
                if (!takenNames.contains(name)) {
                    takenNames.add(name);
                    player.setName(name);
                    nameMessage = new ConfirmedNameMessage(name);
                } else {
                    nameMessage = new NameMessage(name);
                }
                player.send(nameMessage);
            }
            allPlayersHaveName = takenNames.size() == players.size();
        }
    }

    private List<Entry<Player, NameMessage>> sortMessagesByTimestamp(Map<Player, NameMessage> playerMessageMap) {
        final Comparator<Entry<Player, NameMessage>> byEarliestMessage = Comparator.comparing(
                entry -> entry.getValue().getTimeStamp()
        );
        return playerMessageMap
                .entrySet().stream()
                .sorted(byEarliestMessage)
                .collect(Collectors.toList());
    }

    // Receives a name message for every unnamed player with an incoming message
    private Map<Player, NameMessage> receiveNewNameMessages(List<Player> players) throws IOException {
        Map<Player, NameMessage> playerAnswerMap = new HashMap<>();
        for (Player player : players) {
            if (player.getName().isEmpty() && player.hasMessage()) {
                NameMessage answer = player.receive(NameMessage.class);
                playerAnswerMap.put(player, answer);
            }
        }
        return playerAnswerMap;
    }

}
