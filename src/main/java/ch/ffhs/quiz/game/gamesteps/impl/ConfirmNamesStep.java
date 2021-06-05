package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.gamesteps.GameStep;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.NameMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            for (Player player : players) {
                if (player.getName() == null && player.hasMessage()) {
                    String name = receivePlayerName(player);
                    boolean nameIsConfirmed = !takenNames.contains(name);

                    NameMessage nameMessage = new NameMessage(name);
                    nameMessage.setConfirmed(nameIsConfirmed);
                    player.send(nameMessage);

                    if (nameIsConfirmed) {
                        takenNames.add(name);
                        player.setName(name);
                    }
                }
            }
            allPlayersHaveName = takenNames.size() == players.size();
        }
    }

    private String receivePlayerName(Player player) throws IOException {
        NameMessage nameMessage = player.receive(NameMessage.class);

        return nameMessage.getText();
    }
}
