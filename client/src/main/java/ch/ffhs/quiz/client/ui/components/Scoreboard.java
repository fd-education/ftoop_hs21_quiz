package ch.ffhs.quiz.client.ui.components;

import ch.ffhs.quiz.client.ui.components.ascii.AsciiArtTable;
import ch.ffhs.quiz.messages.ScoreboardEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Scoreboard {
    List<ScoreboardEntry> rankedPlayers;
    List<ScoreboardEntry> topTenPlayers;

    public Scoreboard(final List<ScoreboardEntry> rankedPlayers){
        this.rankedPlayers = Objects.requireNonNull(rankedPlayers, "rankedPlayers must not be null");
        topTenPlayers = extractTopTenPlayers();
    }

    public String getScoreboardForPlayer(final String name){
        if(name.isBlank()) throw new IllegalArgumentException("name must contain letters and not only whitespace");

        return createScoreBoard(name);
    }

    private String createScoreBoard(final String name){
        if(name.isBlank()) throw new IllegalArgumentException("name must contain letters and not only whitespace");

        int longestName = getMaxNameLength(rankedPlayers);
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

    private int getIndexOfPlayer(final String name){
        if(name.isBlank()) throw new IllegalArgumentException("name must contain letters and not only whitespace");

        for(int i = 9; i<rankedPlayers.size(); i++){
            if(rankedPlayers.get(i).getPlayerName().equals(name)) return i;
        }

        return -1;
    }

    private List<ScoreboardEntry> extractTopTenPlayers() {
        if(rankedPlayers.size() <= 10) return rankedPlayers;

        List<ScoreboardEntry> topTenPlayers = new ArrayList<>();

        for(int i = 0; i<10; i++){
            topTenPlayers.add(rankedPlayers.get(i));
        }

        return topTenPlayers;
    }

    private static int getMaxNameLength(final List<ScoreboardEntry> rankedPlayers){
        Objects.requireNonNull(rankedPlayers, "rankedPlayers must not be null");

        int longest = 0;

        for(ScoreboardEntry player: rankedPlayers){
            int current =  player.getPlayerName().length();
            longest = Math.max(longest, current);
        }

        return longest;
    }

    // TODO: Rebuild client to use String.indent() instead of hardcoded white space !!!
    private String getWhiteSpaceToCenter(final int tableLength){
        int spaceToCenter = (105-tableLength)/2 - 1;

        char[] whiteSpaces = new char[spaceToCenter];
        Arrays.fill(whiteSpaces, ' ');

        return new String(whiteSpaces);
    }
}
