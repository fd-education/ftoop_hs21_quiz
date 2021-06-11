package ch.ffhs.quiz.client.ui.text_components;

public enum AsciiArtNumbers {
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

    AsciiArtNumbers(String ascii){
        this.ascii = ascii;
    }

    public String getAscii(){
        return ascii;
    }
}
