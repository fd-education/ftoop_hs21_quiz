package ch.ffhs.quiz.game;

import ch.ffhs.quiz.messages.AnswerMessage;
import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.game.player.Player;

import java.util.*;

public class RoundContext {
    private Player winningPlayer;
    private final Set<Player> correctPlayers = new HashSet<>();
    private final Map<Player, AnswerMessage> playerAnswersMap = new HashMap<>();
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

    public void setPlayerAnswer(Player player, AnswerMessage answerMessage) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(answerMessage);
        playerAnswersMap.put(player, answerMessage);
    }

    public AnswerMessage getPlayerAnswer(Player player) {
        return playerAnswersMap.get(player);
    }

    public Player getWinningPlayer() {
        return winningPlayer;
    }

    public void setWinningPlayer(Player winningPlayer) {
        Objects.requireNonNull(winningPlayer);
        if (this.winningPlayer != null) throw new IllegalStateException("Winning player has already been set");
        this.winningPlayer = winningPlayer;
        addCorrectPlayer(winningPlayer);
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
