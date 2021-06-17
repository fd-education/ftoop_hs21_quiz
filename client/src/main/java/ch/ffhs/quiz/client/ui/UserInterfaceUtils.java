package ch.ffhs.quiz.client.ui;

import java.util.Objects;

/**
 * Utility class for the user interface
 */
public class UserInterfaceUtils {

    private UserInterfaceUtils(){}

    /**
     * Print a text with default style (blue, bold)
     *
     * @param text the text
     */
    public static void printWithDefaultStyle(final String text){
        if(text.isBlank()) throw new IllegalArgumentException("text must not be empty or whitespace only");

        new AnsiBuilder(text).setFont(AnsiBuilder.Color.BLUE, false).print();
    }

    /**
     * Return a String with default style (blue, bold)
     *
     * @param text the text
     * @return the styled string
     */
    public static String createWithDefaultStyle(final String text){
        if(text.isBlank()) throw new IllegalArgumentException("text must not be empty or whitespace only");

        return new AnsiBuilder(text).setFont(AnsiBuilder.Color.BLUE, true).create();
    }

    /**
     * Print a provided text to the terminal letter by letter
     *
     * @param text  the text
     * @param delay the delay (FAST, SLOW)
     */
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

    /**
     * Look for an ideal location to split a string,
     * if it is longer, than the provided maxLineLength
     *
     * @param phrase        the phrase
     * @param maxLineLength the max line length
     * @return the formatted string
     */
    public static String splitPhraseAtSpace(final String phrase, final int maxLineLength){
        if(phrase.isBlank()) throw new IllegalArgumentException("phrase must not be empty or whitespace only");
        if(maxLineLength <= 0) throw new IllegalArgumentException("maxLineLength must be greater than zero");

        // if the phrase is shorter than maxLineLength, return it unprocessed
        if(phrase.length() <= maxLineLength) return phrase;

        StringBuilder sb = new StringBuilder();

        // split the string at the exact maxLineLength without caring for context
        String firstRoughSplit = phrase.substring(0, maxLineLength);
        String secondRoughSplit = phrase.substring(maxLineLength);

        // use the rough splits to find the whitespace closest to the maxLineLength
        int lastSpaceIndex = (firstRoughSplit.lastIndexOf(" ") != -1 )? firstRoughSplit.lastIndexOf(" "): firstRoughSplit.length() + secondRoughSplit.indexOf(" ");

        // split the string at the found whitespace location
        String firstPart =  phrase.substring(0, lastSpaceIndex);
        String secondPart = phrase.substring(lastSpaceIndex + 1);

        // append the first good split to the string builder, then append a line break
        sb.append(firstPart).append("\n");
        // if the second part of the good split it longer than maxLineLength, call method recursively, else append it to the string builder
        sb.append(secondPart.length() > maxLineLength ? splitPhraseAtSpace(secondPart, maxLineLength): secondPart);

        return sb.toString();
    }

    /**
     * Enum to specify the delays for printLetterByLetter.
     */
    enum Delay{
        /**
         * Fast delay.
         */
        FAST(20L),
        /**
         * Slow delay.
         */
        SLOW(100L);

        public final Long delay;

        Delay(Long delay){this.delay = delay;}

        /**
         * Get the desired duration as long.
         *
         * @return the duration as long
         */
        public Long getDuration(){return delay;}
    }
}
