package ch.ffhs.quiz.client.ui;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

public class UserInterfaceUtils {

    public static void printWithDefaultStyle(String text){
        new AnsiBuilder(text).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).print();
    }

    public static String createWithDefaultStyle(String text){
        return new AnsiBuilder(text).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).create();
    }

    public static void printLetterByLetter(String text, Delay delay){
        char[] letters = text.toCharArray();

        for(char letter: letters){
            try {
                System.out.print(letter);
                Thread.sleep(delay.getDuration());
            } catch(InterruptedException iEx){
                Thread.currentThread().interrupt();
            }
        }
    }

    public static String splitPhrase(String phrase, int lineLength){
        if(phrase.length() <= lineLength) return phrase;

        int lastSpaceIndex;
        String roughSplit = phrase.substring(0, lineLength);
        String firstPart = roughSplit.substring(0, lastSpaceIndex = roughSplit.lastIndexOf(" "));

        String secondPart = phrase.substring(lastSpaceIndex + 1);

        return String.format("%s\n     %s", firstPart, secondPart);
    }

    public static int getMaxLengthFromSetEntries(Set<String> set){

        Comparator<String> stringLengthComparator = new Comparator<>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        };

        String longestKey = Collections.max(set, stringLengthComparator);

        return longestKey.length();
    }

    enum Delay{
        FAST(20L),
        MEDIUM(50L),
        SLOW(100L),
        ZERO(0L);

        public final Long delay;

        Delay(Long delay){this.delay = delay;}

        public Long getDuration(){return delay;}
    }
}
