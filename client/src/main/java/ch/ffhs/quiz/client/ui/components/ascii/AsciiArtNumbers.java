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
        return AsciiArtNumbers.values()[index].getComponent();
    }

    public String getComponent(){
        return ascii;
    }
}
