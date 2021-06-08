package ch.ffhs.quiz.game;

import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.AnswerMessage;
import ch.ffhs.quiz.questions.Question;

import java.util.*;

public class RoundContext {
    private final Set<Player> correctPlayers = new HashSet<>();
    private final Map<Player, AnswerMessage> playerAnswersMap = new HashMap<>();
    private Player winningPlayer;
    private final Question question;

    public RoundContext(Question question) {
        Objects.requireNonNull(question);
        this.question = question;
        winningPlayer = null;
    }

    public Question getQuestion() {
        return question;
    }

    public void setPlayerAnswer(Player player, AnswerMessage answerMessage) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(answerMessage);
        playerAnswersMap.put(player, answerMessage);
    }

    public AnswerMessage getPlayerAnswer(Player player) {
        Objects.requireNonNull(player);
        if (!playerAnswersMap.containsKey(player))
            throw new IllegalArgumentException("There is no answer from this player");
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
}
