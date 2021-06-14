package ch.ffhs.quiz.client.ui.components.ascii;

import java.util.Arrays;

public class AsciiArtTable {
    public static String getTableTop(final int length){
        String borderTemplate = "     ╔═════╦%s╦═════╗\n";
        char[] border = new char[length + 5];
        Arrays.fill(border, '═');

        return String.format(borderTemplate, new String(border));
    }

    public static String getCellBordersWithContent(final int length, final int rank, final String name, final int score){
        String cellTemplate = "     ║ %s ║ %s║ %s ║\n";
        char[] filler = new char[length + 5 - 1 - name.length()];
        Arrays.fill(filler, ' ');

        String rankString = formatIntegerToFitInCol(rank);
        String scoreString = formatIntegerToFitInCol(score);
        return String.format(cellTemplate, rankString, name + new String(filler), scoreString);
    }

    public static String getTableBottom(final int length){
        String borderTemplate = "     ╚═════╩%s╩═════╝\n";
        char[] border = new char[length + 5];
        Arrays.fill(border, '═');

        return String.format(borderTemplate, new String(border));
    }

    private static String formatIntegerToFitInCol(final int integer){
        if(integer < 10){
            return integer + "  ";
        } else if(integer < 100){
            return integer + " ";
        } else{
            return String.valueOf(integer);
        }
    }
}
