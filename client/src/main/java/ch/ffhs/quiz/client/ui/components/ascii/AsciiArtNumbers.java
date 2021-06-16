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
            """),
    ONE("""
                                                              ██╗
                                                             ███║
                                                             ╚██║
                                                              ██║
                                                              ██║
                                                              ╚═╝
            """),
    TWO("""
                                                           ██████╗
                                                           ╚════██╗
                                                            █████╔╝
                                                           ██╔═══╝
                                                           ███████╗
                                                           ╚══════╝
            """),
    THREE("""
                                                           ██████╗
                                                           ╚════██╗
                                                            █████╔╝
                                                            ╚═══██╗
                                                           ██████╔╝
                                                           ╚═════╝
            """),
    FOUR("""
                                                           ██╗  ██╗
                                                           ██║  ██║
                                                           ███████║
                                                           ╚════██║
                                                                ██║
                                                                ╚═╝
            """),
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

    public static String getText(final int index){
        if(index < 0 || index > 5) throw new IllegalArgumentException("AsciiArtNumbers can only return numbers between  0 and 5");

        return AsciiArtNumbers.values()[index].getComponent();
    }

    public String getComponent(){
        return ascii;
    }
}
