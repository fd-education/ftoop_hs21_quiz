package ch.ffhs.quiz.server.gamesteps;

import ch.ffhs.quiz.messages.MessageUtils;
import ch.ffhs.quiz.messages.ScoreboardMessage;
import ch.ffhs.quiz.server.GameContext;
import ch.ffhs.quiz.server.player.Player;

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
        StringBuilder scoreboardSB = new StringBuilder("Scoreboard for round %s:\n".formatted(gameContext.getRoundContext().getRoundNumber()));
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
        final String scoreboardJson = MessageUtils.serialize(new ScoreboardMessage(scoreboard));
        player.send(scoreboardJson);
    }
}
