package ch.ffhs.quiz.client.ui.components.ascii;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to provide methods to create a table.
 * Only used for scoreboard.
 */
public class AsciiArtTable {
    private static final int TABLE_PADDING = 5;

    private AsciiArtTable(){}

    /**
     * Get the top border of the table as a String,
     * with a center content cell according to a specified length.
     *
     * @param length the length of the center content cell
     * @return the string containing the tables top border
     */
    public static String getTableTop(final int length){
        checkParams(new HashMap<>(Map.of("length", length)));

        String borderTemplate = "     ╔═════╦═%s╦═════╗\n";
        char[] border = new char[length + TABLE_PADDING];
        Arrays.fill(border, '═');

        return String.format(borderTemplate, new String(border));
    }

    /**
     * Get the cell elements of the table as a String,
     * with a center content cell according to a specified length.
     * The provided contents will be filled into the table
     *
     * @param length the length of the center content cell
     * @param rank   the rank
     * @param name   the name
     * @param score  the score
     * @return the string containing the table element
     */
    public static String getCellBordersWithContent(final int length, final int rank, final String name, final int score){
        checkParams(new HashMap<>(Map.of("length", length, "rank", rank, "name", name, "score", score)));

        String cellTemplate = "     ║ %s ║%s║ %s ║\n";
        char[] filler = new char[length + TABLE_PADDING - name.length()];
        Arrays.fill(filler, ' ');

        String rankString = formatNumberToFitInCol(rank);
        String scoreString = formatNumberToFitInCol(score);
        String nameString = String.format(" %s%s", name, new String(filler));
        return String.format(cellTemplate, rankString, nameString, scoreString);
    }

    /**
     * Get the bottom border of the table as a String,
     * with a center content cell according to a specified length.
     *
     * @param length the length of the center content cell
     * @return the string containing the tables bottom border
     */
    public static String getTableBottom(final int length){
        checkParams(new HashMap<>(Map.of("length", length)));

        String borderTemplate = "     ╚═════╩═%s╩═════╝\n";
        char[] border = new char[length + TABLE_PADDING];
        Arrays.fill(border, '═');

        return String.format(borderTemplate, new String(border));
    }

    // format the given number to line up in the same column
    // and to take up even space (fill with whitespace)
    private static String formatNumberToFitInCol(final int number){
        if(number < 10){
            return number + "  ";
        } else if(number < 100){
            return number + " ";
        } else{
            return String.valueOf(number);
        }
    }

    // helper method to check the params of the table methods
    private static void checkParams(Map<String, Object> params){
        if(params.containsKey("length") && (int) params.get("length") <= 0) throw new IllegalArgumentException("length must be greater than 0");
        if(params.containsKey("rank") && (int) params.get("rank") <= 0) throw new IllegalArgumentException("rank must be greater than 0");
        if(params.containsKey("name") && ((String) params.get("name")).isBlank()) throw new IllegalArgumentException("name must not be empty or whitespace only");
        if(params.containsKey("score") && (int) params.get("score") < 0) throw new IllegalArgumentException("score must be positive");
    }
}
