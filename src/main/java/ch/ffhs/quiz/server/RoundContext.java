package ch.ffhs.quiz.server;

import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.server.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoundContext {
    private Player winningPlayer;
    private final List<Player> correctPlayers = new ArrayList<>();
    private int roundNumber = 0;
    private Question currentQuestion;

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void nextRound(Question currentQuestion) {
        Objects.requireNonNull(currentQuestion);
        this.currentQuestion = currentQuestion;
        roundNumber += 1;
        winningPlayer = null;
    }

    public Player getWinningPlayer() {
        return winningPlayer;
    }

    public void setWinningPlayer(Player winningPlayer) {
        Objects.requireNonNull(winningPlayer);
        if (this.winningPlayer != null) throw new IllegalStateException("Winning player has already been set");
        this.winningPlayer = winningPlayer;
    }

    public void addCorrectPlayer(Player player) {
        Objects.requireNonNull(player);
        correctPlayers.add(player);
    }

    public boolean wasPlayerCorrect(Player player) {
        return correctPlayers.contains(player);
    }

    public int getRoundNumber() {
        return roundNumber;
    }
}
