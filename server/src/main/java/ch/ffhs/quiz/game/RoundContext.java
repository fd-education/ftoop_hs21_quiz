package ch.ffhs.quiz.game;

import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.AnswerMessage;
import ch.ffhs.quiz.questions.Question;

import java.util.*;

/**
 * The round context stores information on the current round.
 */
public class RoundContext {
    private final Set<Player> correctPlayers = new HashSet<>();
    private final Map<Player, AnswerMessage> playerAnswersMap = new HashMap<>();
    private Player winningPlayer;
    private final Question question;

    /**
     * Instantiates a new Round context.
     *
     * @param question the current question
     */
    public RoundContext(Question question) {
        Objects.requireNonNull(question);
        this.question = question;
        winningPlayer = null;
    }

    /**
     * Gets the current question.
     *
     * @return the current question
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * Stores the {@linkplain AnswerMessage answer message} sent by a player
     *
     * @param player        the player
     * @param answerMessage the answer message from this player
     */
    public void setPlayerAnswer(Player player, AnswerMessage answerMessage) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(answerMessage);
        playerAnswersMap.put(player, answerMessage);
    }

    /**
     * Gets the {@linkplain AnswerMessage answer message} sent by a player
     *
     * @param player the player
     * @return the answer message from this player
     * @throws IllegalArgumentException if this player has not sent a message
     */
    public AnswerMessage getPlayerAnswer(Player player) throws IllegalArgumentException {
        Objects.requireNonNull(player);
        if (!playerAnswersMap.containsKey(player))
            throw new IllegalArgumentException("There is no answer from this player");
        return playerAnswersMap.get(player);
    }

    /**
     * Gets the player who won the round.
     *
     * @return the winning player
     */
    public Player getWinningPlayer() {
        return winningPlayer;
    }

    /**
     * Sets the player who won the round.
     *
     * @param winningPlayer the winning player
     * @throws IllegalStateException if a winning player has already been set
     */
    public void setWinningPlayer(Player winningPlayer) throws IllegalStateException {
        Objects.requireNonNull(winningPlayer);
        if (this.winningPlayer != null) throw new IllegalStateException("Winning player has already been set");
        this.winningPlayer = winningPlayer;
        addCorrectPlayer(winningPlayer);
    }

    /**
     * Adds the given player to the list of correct players.
     *
     * @param player the player
     */
    public void addCorrectPlayer(Player player) {
        Objects.requireNonNull(player);
        correctPlayers.add(player);
    }

    /**
     * Whether the given player was correct.
     *
     * @param player the player
     * @return true if the player was correct, false otherwise
     */
    public boolean wasPlayerCorrect(Player player) {
        Objects.requireNonNull(player);
        return correctPlayers.contains(player);
    }
}
