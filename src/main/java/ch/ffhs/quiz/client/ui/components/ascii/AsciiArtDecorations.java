package ch.ffhs.quiz.client.ui.components.ascii;

import ch.ffhs.quiz.client.ui.components.interfaces.StaticUIComponent;

public enum AsciiArtDecorations implements StaticUIComponent {
    TOP_LINE("""
          ╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
          ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
          """),
    BOTTOM_LINE("""
          ╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
          ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝
          """);

    private final String ascii;

    AsciiArtDecorations(final String ascii){
        this.ascii = ascii;
    }

    public String getComponent(){
        return ascii;
    }

}
