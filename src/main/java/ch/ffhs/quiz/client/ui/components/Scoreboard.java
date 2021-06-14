package ch.ffhs.quiz.client.ui.components;

import ch.ffhs.quiz.client.ui.components.ascii.AsciiArtTable;
import ch.ffhs.quiz.messages.ScoreboardEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scoreboard {
    List<ScoreboardEntry> rankedPlayers;
    List<ScoreboardEntry> topTenPlayers;

    public Scoreboard(List<ScoreboardEntry> rankedPlayers){
        this.rankedPlayers = rankedPlayers;
        topTenPlayers = new ArrayList<>();

        findTopTen();
    }

    public String getScoreboardForPlayer(String name){
        return createScoreBoard(name);
    }

    private String createScoreBoard(String name){
        int longestName = getMaxLengthFromEntries(rankedPlayers);
        boolean playerContained = false;
        int rank = 0;

        StringBuilder sb = new StringBuilder();
        String tableTop = AsciiArtTable.getTableTop(longestName);
        String centerSpace = getWhiteSpaceToCenter(tableTop.length());

        sb.append(centerSpace).append(tableTop);

        for(ScoreboardEntry entry: topTenPlayers){
            rank++;
            String playerName = entry.getPlayerName();
            int score = entry.getScore();
            if(!playerContained) playerContained = playerName.equals(name);

            sb.append(centerSpace).append(AsciiArtTable.getCellBordersWithContent(longestName, rank, playerName, score));
        }

        if(!playerContained){
            int index = getIndexOfPlayer(name);
            int score = rankedPlayers.get(index).getScore();
            sb.append(centerSpace).append(AsciiArtTable.getCellBordersWithContent(longestName, index+1, name, score));
        }

        sb.append(centerSpace).append(AsciiArtTable.getTableBottom(longestName));

        return sb.toString();
    }

    private int getIndexOfPlayer(String name){
        for(int i = 9; i<rankedPlayers.size(); i++){
            if(rankedPlayers.get(i).getPlayerName().equals(name)) return i;
        }

        return -1;
    }

    private void findTopTen() {
        if(rankedPlayers.size() <= 10) {
            topTenPlayers = rankedPlayers;
            return;
        }

        for(int i = 0; i<10; i++){
            topTenPlayers.add(rankedPlayers.get(i));
        }
    }

    private static int getMaxLengthFromEntries(List<ScoreboardEntry> entries){
        int longest = 0;
        for(ScoreboardEntry entry: entries){
            int curr =  entry.getPlayerName().length();
            longest = Math.max(longest, curr);
        }

        return longest;
    }

    private String getWhiteSpaceToCenter(int tableLength){
        int spaceToCenter = (105-tableLength)/2 - 1;

        char[] whiteSpaces = new char[spaceToCenter];
        Arrays.fill(whiteSpaces, ' ');

        return new String(whiteSpaces);
    }
}
