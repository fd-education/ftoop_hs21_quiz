package ch.ffhs.quiz.client.ui.components.ascii;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AsciiArtTable {

    public static String getTableTop(final int length){
        checkParams(new HashMap<>(Map.of("length", length)));

        String borderTemplate = "     ╔═════╦%s╦═════╗\n";
        char[] border = new char[length + 5];
        Arrays.fill(border, '═');

        return String.format(borderTemplate, new String(border));
    }

    public static String getCellBordersWithContent(final int length, final int rank, final String name, final int score){
        checkParams(new HashMap<>(Map.of("length", length, "rank", rank, "name", name, "score", score)));

        String cellTemplate = "     ║ %s ║ %s║ %s ║\n";
        char[] filler = new char[length + 5 - 1 - name.length()];
        Arrays.fill(filler, ' ');

        String rankString = formatNumberToFitInCol(rank);
        String scoreString = formatNumberToFitInCol(score);
        return String.format(cellTemplate, rankString, name + new String(filler), scoreString);
    }

    public static String getTableBottom(final int length){
        checkParams(new HashMap<>(Map.of("length", length)));

        String borderTemplate = "     ╚═════╩%s╩═════╝\n";
        char[] border = new char[length + 5];
        Arrays.fill(border, '═');

        return String.format(borderTemplate, new String(border));
    }

    private static String formatNumberToFitInCol(final int number){
        if(number < 10){
            return number + "  ";
        } else if(number < 100){
            return number + " ";
        } else{
            return String.valueOf(number);
        }
    }

    private static void checkParams(Map<String, Object> params){
        if(params.containsKey("length") && (int) params.get("length") <= 0) throw new IllegalArgumentException("length must be bigger than 0");
        if(params.containsKey("rank") && (int) params.get("rank") <= 0) throw new IllegalArgumentException("rank must be bigger than 0");
        if(params.containsKey("name") && ((String) params.get("name")).isBlank()) throw new IllegalArgumentException("name must not be empty or whitespace only");
        if(params.containsKey("score") && (int) params.get("score") < 0) throw new IllegalArgumentException("score must be positive");
    }
}
