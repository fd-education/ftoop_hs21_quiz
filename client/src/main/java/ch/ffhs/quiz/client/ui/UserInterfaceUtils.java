package ch.ffhs.quiz.client.ui;

import java.util.Objects;

public class UserInterfaceUtils {

    private UserInterfaceUtils(){}

    public static void printWithDefaultStyle(final String text){
        if(text.isBlank()) throw new IllegalArgumentException("text must not be empty or whitespace only");

        new AnsiBuilder(text).setFont(AnsiBuilder.Color.BLUE, false).print();
    }

    public static String createWithDefaultStyle(final String text){
        if(text.isBlank()) throw new IllegalArgumentException("text must not be empty or whitespace only");

        return new AnsiBuilder(text).setFont(AnsiBuilder.Color.BLUE, true).create();
    }

    public static void printLetterByLetter(final String text, Delay delay){
        if(text.isBlank()) throw new IllegalArgumentException("text must not be empty or whitespace only");
        Objects.requireNonNull(delay, "delay must not be null");

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

    public static String splitPhraseAtSpace(final String phrase, final int maxLineLength){
        if(phrase.isBlank()) throw new IllegalArgumentException("phrase must not be empty or whitespace only");
        if(maxLineLength <= 0) throw new IllegalArgumentException("maxLineLength must be greater than zero");

        if(phrase.length() <= maxLineLength) return phrase;

        StringBuilder sb = new StringBuilder();

        String firstRoughSplit = phrase.substring(0, maxLineLength);
        String secondRoughSplit = phrase.substring(maxLineLength);
        int lastSpaceIndex = (firstRoughSplit.lastIndexOf(" ") != -1 )? firstRoughSplit.lastIndexOf(" "): firstRoughSplit.length() + secondRoughSplit.indexOf(" ");

        String firstPart =  phrase.substring(0, lastSpaceIndex);
        String secondPart = phrase.substring(lastSpaceIndex + 1);

        sb.append(firstPart).append("\n");
        sb.append(secondPart.length() > maxLineLength ? splitPhraseAtSpace(secondPart, maxLineLength): secondPart);

        return sb.toString();
    }

    enum Delay{
        FAST(20L),
        SLOW(100L);

        public final Long delay;

        Delay(Long delay){this.delay = delay;}

        public Long getDuration(){return delay;}
    }
}
