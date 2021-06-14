package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.gamesteps.GameStep;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.RoundSummaryMessage;
import ch.ffhs.quiz.messages.ScoreboardEntry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Sends a summary for the round to every player,
 * including a scoreboard and information on whether this has been the last round of the game.
 */
public class RoundSummaryStep extends GameStep {
    private RoundSummaryMessage summaryMessage;

    public RoundSummaryStep(GameContext gameContext) {
        super(gameContext);
    }

    // Builds the summary message
    @Override
    protected void prepareStep() {
        List<ScoreboardEntry> scoreboardEntries = buildScoreboardEntries(gameContext.getPlayers());
        summaryMessage = new RoundSummaryMessage(scoreboardEntries, gameContext.isFinished());
    }

    // Builds the scoreboard with the given players.
    private List<ScoreboardEntry> buildScoreboardEntries(List<Player> players) {
        List<Player> rankedPlayersList = new ArrayList<>(players);
        final Comparator<Player> maxScoreComparator = Comparator.comparing(Player::getScore).reversed();
        rankedPlayersList.sort(maxScoreComparator.thenComparing(Player::getId));
        List<ScoreboardEntry> playerScoreEntries = new ArrayList<>();
        for (Player player : rankedPlayersList) {
            playerScoreEntries.add(new ScoreboardEntry(player.getName(), player.getScore()));
        }
        return playerScoreEntries;
    }

    // Sends the built summary message to the given player
    @Override
    protected void handlePlayer(Player player) {
        player.send(summaryMessage);
    }
}
