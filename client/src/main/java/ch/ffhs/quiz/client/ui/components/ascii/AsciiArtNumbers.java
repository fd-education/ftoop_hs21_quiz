package ch.ffhs.quiz.client.ui.components.ascii;

import ch.ffhs.quiz.client.ui.components.interfaces.StaticUIComponent;

/**
 * Enum for Ascii art numbers.
 */
public enum AsciiArtNumbers implements StaticUIComponent {
    /**
     * Ascii zero
     */
    ZERO("""
                                                            ██████╗
                                                           ██╔═████╗
                                                           ██║██╔██║
                                                           ████╔╝██║
                                                           ╚██████╔╝
                                                            ╚═════╝
            """),
    /**
     * Ascii one
     */
    ONE("""
                                                              ██╗
                                                             ███║
                                                             ╚██║
                                                              ██║
                                                              ██║
                                                              ╚═╝
            """),
    /**
     * Ascii two
     */
    TWO("""
                                                           ██████╗
                                                           ╚════██╗
                                                            █████╔╝
                                                           ██╔═══╝
                                                           ███████╗
                                                           ╚══════╝
            """),
    /**
     * Ascii three
     */
    THREE("""
                                                           ██████╗
                                                           ╚════██╗
                                                            █████╔╝
                                                            ╚═══██╗
                                                           ██████╔╝
                                                           ╚═════╝
            """),
    /**
     * Ascii four
     */
    FOUR("""
                                                           ██╗  ██╗
                                                           ██║  ██║
                                                           ███████║
                                                           ╚════██║
                                                                ██║
                                                                ╚═╝
            """),
    /**
     * Ascii five
     */
    FIVE("""
                                                           ███████╗
                                                           ██╔════╝
                                                           ███████╗
                                                           ╚════██║
                                                           ███████║
                                                           ╚══════╝
            """);

    private final String ascii;

    AsciiArtNumbers(final String ascii){
        this.ascii = ascii;
    }

    /**
     * Get the Ascii art number as a string,
     * matching its numeric index
     *
     * @param index the index (the number to get)
     * @return the Ascii number String
     */
    public static String getText(final int index){
        if(index < 0 || index > 5) throw new IllegalArgumentException("AsciiArtNumbers can only return numbers between  0 and 5");

        return AsciiArtNumbers.values()[index].getComponent();
    }

    /**
     * Get the component in a String representation
     * @return component
     */
    public String getComponent(){
        return ascii;
    }
}
