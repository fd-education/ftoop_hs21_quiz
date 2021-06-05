package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.gamesteps.GameStep;
import ch.ffhs.quiz.messages.RoundSummaryMessage;
import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.ScoreboardEntry;

import java.util.*;

public class RoundSummaryStep extends GameStep {
    private RoundSummaryMessage summaryMessage;

    public RoundSummaryStep(GameContext gameContext) {
        super(gameContext);
    }

    @Override
    protected void prepareStep() {
        List<Player> rankedPlayersList = new ArrayList<>(gameContext.getPlayers());
        final Comparator<Player> maxScoreComparator = Comparator.comparing(Player::getScore).reversed();
        rankedPlayersList.sort(maxScoreComparator.thenComparing(Player::getId));
        List<ScoreboardEntry> playerScoreEntries = new ArrayList<>();
        for (Player player : rankedPlayersList) {
            playerScoreEntries.add(new ScoreboardEntry(player.getName(), player.getScore()));
        }
        summaryMessage = new RoundSummaryMessage(playerScoreEntries, gameContext.isFinished());
    }

    @Override
    protected void handlePlayer(Player player) {
        if (summaryMessage == null) throw new IllegalStateException("Scoreboard has not been created.");
        player.send(summaryMessage);
    }
}
