package ch.ffhs.quiz.client.ui.components.ascii;

import ch.ffhs.quiz.client.ui.components.interfaces.StaticUIComponent;

public enum AsciiArtTitles implements StaticUIComponent {
    FACADE_QUIZ("""
                        ███████╗ █████╗  ██████╗ █████╗ ██████╗ ███████╗     ██████╗ ██╗   ██╗██╗███████╗
                        ██╔════╝██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝    ██╔═══██╗██║   ██║██║╚══███╔╝
                        █████╗  ███████║██║     ███████║██║  ██║█████╗      ██║   ██║██║   ██║██║  ███╔╝
                        ██╔══╝  ██╔══██║██║     ██╔══██║██║  ██║██╔══╝      ██║▄▄ ██║██║   ██║██║ ███╔╝
                        ██║     ██║  ██║╚██████╗██║  ██║██████╔╝███████╗    ╚██████╔╝╚██████╔╝██║███████╗
                        ╚═╝     ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═════╝ ╚══════╝     ╚══▀▀═╝  ╚═════╝ ╚═╝╚══════╝
            """),
    QUESTION("""
                                            ███████╗██████╗  █████╗  ██████╗ ███████╗
                                            ██╔════╝██╔══██╗██╔══██╗██╔════╝ ██╔════╝
                                            █████╗  ██████╔╝███████║██║  ███╗█████╗
                                            ██╔══╝  ██╔══██╗██╔══██║██║   ██║██╔══╝
                                            ██║     ██║  ██║██║  ██║╚██████╔╝███████╗
                                            ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝
            """),
    SCORE("""
                                            ███████╗ ██████╗ ██████╗ ██████╗ ███████╗
                                            ██╔════╝██╔════╝██╔═══██╗██╔══██╗██╔════╝
                                            ███████╗██║     ██║   ██║██████╔╝█████╗
                                            ╚════██║██║     ██║   ██║██╔══██╗██╔══╝
                                            ███████║╚██████╗╚██████╔╝██║  ██║███████╗
                                            ╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
            """),
    END("""
                                               ███████╗███╗   ██╗██████╗ ███████╗
                                               ██╔════╝████╗  ██║██╔══██╗██╔════╝
                                               █████╗  ██╔██╗ ██║██║  ██║█████╗
                                               ██╔══╝  ██║╚██╗██║██║  ██║██╔══╝
                                               ███████╗██║ ╚████║██████╔╝███████╗
                                               ╚══════╝╚═╝  ╚═══╝╚═════╝ ╚══════╝
            """);

    private final String ascii;

    AsciiArtTitles(String ascii){
        this.ascii = ascii;
    }

    public String getText(){
        return ascii;
    }

}
