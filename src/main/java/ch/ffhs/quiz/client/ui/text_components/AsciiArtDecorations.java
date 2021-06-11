package ch.ffhs.quiz.client.ui.text_components;

public enum AsciiArtDecorations {
    TOP_LINE("""
          ╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
          ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
            """),
    BOTTOM_LINE("""
          ╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
          ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝  
            """);

    private final String ascii;

    AsciiArtDecorations(String ascii){
        this.ascii = ascii;
    }

    public String getAscii(){
        return ascii;
    }
}
