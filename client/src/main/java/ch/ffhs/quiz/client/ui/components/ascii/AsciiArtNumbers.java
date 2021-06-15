package ch.ffhs.quiz.client.ui.components.ascii;

import ch.ffhs.quiz.client.ui.components.interfaces.StaticUIComponent;

public enum AsciiArtNumbers implements StaticUIComponent {
    ZERO("""
                                                            ██████╗
                                                           ██╔═████╗
                                                           ██║██╔██║
                                                           ████╔╝██║
                                                           ╚██████╔╝
                                                            ╚═════╝
            """, 6),
    ONE("""
                                                              ██╗
                                                             ███║
                                                             ╚██║
                                                              ██║
                                                              ██║
                                                              ╚═╝
            """, 6),
    TWO("""
                                                           ██████╗
                                                           ╚════██╗
                                                            █████╔╝
                                                           ██╔═══╝
                                                           ███████╗
                                                           ╚══════╝
            """, 6),
    THREE("""
                                                           ██████╗
                                                           ╚════██╗
                                                            █████╔╝
                                                            ╚═══██╗
                                                           ██████╔╝
                                                           ╚═════╝
            """, 6),
    FOUR("""
                                                           ██╗  ██╗
                                                           ██║  ██║
                                                           ███████║
                                                           ╚════██║
                                                                ██║
                                                                ╚═╝
            """, 6),
    FIVE("""
                                                           ███████╗
                                                           ██╔════╝
                                                           ███████╗
                                                           ╚════██║
                                                           ███████║
                                                           ╚══════╝
            """, 6);

    private final String ascii;
    private final int linesOfContent;

    AsciiArtNumbers(String ascii, int linesOfContent){
        this.ascii = ascii;
        this.linesOfContent =  linesOfContent;
    }

    public static String getText(int index){
        return AsciiArtNumbers.values()[index].getText();
    }

    public String getText(){
        return ascii;
    }

    public int getLinesOfContent(){
        return linesOfContent;
    }
}
