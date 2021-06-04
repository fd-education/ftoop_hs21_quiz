package ch.ffhs.quiz.game.gamesteps;

import ch.ffhs.quiz.messages.ScoreboardMessage;
import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.player.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreboardStep extends GameStep {
    private String scoreboard;

    public ScoreboardStep(GameContext gameContext) {
        super(gameContext);
    }

    @Override
    protected void prepareStep() {
        List<Player> players = new ArrayList<>(gameContext.getPlayers());
        final Comparator<Player> maxScoreComparator = Comparator.comparing(Player::getScore).reversed();
        players.sort(maxScoreComparator);
        StringBuilder scoreboardSB = new StringBuilder("Scoreboard for round %s:\n".formatted(roundContext.getRoundNumber()));
        int nthPlace = 1;
        for (Player player : players) {
            scoreboardSB.append(String.format("%d. Place: Player %d with %d Points\n", nthPlace, player.getId(), player.getScore()));
            nthPlace++;
        }
        scoreboard = scoreboardSB.toString();
    }

    @Override
    protected void handlePlayer(Player player) {
        if (scoreboard == null) throw new IllegalStateException("Scoreboard has not been created.");
        player.send(new ScoreboardMessage(scoreboard));
    }
}
