package ch.ffhs.quiz.client.ui.components;

import ch.ffhs.quiz.client.ui.components.ascii.AsciiArtTable;
import ch.ffhs.quiz.messages.ScoreboardEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Scoreboard Object to create a table scoreboard
 */
public class Scoreboard {
    // all the players that are in the game
    private final List<ScoreboardEntry> rankedPlayers;
    // only the top 10 players
    private final List<ScoreboardEntry> topTenPlayers;

    /**
     * Instantiates a new Scoreboard.
     * Extract the best 10 Players right away.
     *
     * @param rankedPlayers the ranked players
     */
    public Scoreboard(final List<ScoreboardEntry> rankedPlayers){
        this.rankedPlayers = Objects.requireNonNull(rankedPlayers, "rankedPlayers must not be null");
        topTenPlayers = extractTopTenPlayers();
    }

    /**
     * Get a string representation of the scoreboard relative to a certain player.
     *
     * @param name the name of the player
     * @return the scoreboard string
     */
    public String getScoreboardForPlayer(final String name){
        Objects.requireNonNull(name, "name must not be null");
        if(name.isBlank()) throw new IllegalArgumentException("name must contain letters and not only whitespace");

        //check if the passed name is a player at all
        if(!findPlayerInRankedList(name)) throw new IllegalArgumentException(name + " does not seem to be a player in this game");

        return createScoreBoard(name);
    }

    // Create a table with the center data cell being long enough for the longest name,
    // then fill in all the data (rank, name, score) and check if the current player is in
    // the top ten. If so, return the scoreboard. If not, find the players position in the
    // rankedPlayers list and append his corresponding data set to the top 10 board.
    private String createScoreBoard(final String name){

        int longestName = getMaxNameLength(rankedPlayers);
        boolean playerContained = false;
        int rank = 0;

        StringBuilder sb = new StringBuilder();
        String tableTop = AsciiArtTable.getTableTop(longestName);
        String centerSpace = getWhiteSpaceToCenter(tableTop.length());

        // fist append the table top
        sb.append(centerSpace).append(tableTop);

        // create the table content with the topTenPlayers list
        for(ScoreboardEntry entry: topTenPlayers){
            rank++;
            String playerName = entry.getPlayerName();
            int score = entry.getScore();
            // if the player is not yet in the list, check if the current player matches with the desired player
            if(!playerContained) playerContained = playerName.equals(name);

            sb.append(centerSpace).append(AsciiArtTable.getCellBordersWithContent(longestName, rank, playerName, score));
        }

        // if the player is not in the top 10 at all, find his index, and create his cell, with the data from the index
        if(!playerContained){
            int index = getIndexOfPlayerFromRanked(name);
            int score = rankedPlayers.get(index).getScore();
            sb.append(centerSpace).append(AsciiArtTable.getCellBordersWithContent(longestName, index+1, name, score));
        }

        // finally append the table bottom
        sb.append(centerSpace).append(AsciiArtTable.getTableBottom(longestName));

        return sb.toString();
    }

    // find the index of the player within the ranked list
    private int getIndexOfPlayerFromRanked(final String name){
        for(int i = 10; i<rankedPlayers.size(); i++){
            if(rankedPlayers.get(i).getPlayerName().equals(name)) return i;
        }

        // may never get to this point, as players presence gets checked before
        return -1;
    }

    // extract the first 10 players from the ranked list
    private List<ScoreboardEntry> extractTopTenPlayers() {
        if(rankedPlayers.size() <= 10) return rankedPlayers;

        List<ScoreboardEntry> topTenPlayers = new ArrayList<>();

        for(int i = 0; i<10; i++){
            topTenPlayers.add(rankedPlayers.get(i));
        }

        return topTenPlayers;
    }

    // find the longest name in a list and return its length
    private int getMaxNameLength(final List<ScoreboardEntry> rankedPlayers){

        int longest = 0;

        for(ScoreboardEntry player: rankedPlayers){
            int current =  player.getPlayerName().length();
            longest = Math.max(longest, current);
        }

        return longest;
    }

    // center the scoreboard in the screen by appending whitespace in front of each line
    private String getWhiteSpaceToCenter(final int tableLength){
        int spaceToCenter = (105-tableLength)/2 - 1;

        char[] whiteSpaces = new char[spaceToCenter];
        Arrays.fill(whiteSpaces, ' ');

        return new String(whiteSpaces);
    }

    // check if a player is in the rankedPlayers list or not
    private boolean findPlayerInRankedList(String name){
        for(ScoreboardEntry entry: rankedPlayers){
            if(entry.getPlayerName().equals(name)) return true;
        }
        return false;
    }
}
