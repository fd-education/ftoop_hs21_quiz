package ch.ffhs.quiz.client.ui.components.ascii;

import ch.ffhs.quiz.client.ui.components.interfaces.StaticUIComponent;

public enum AsciiArtDecorations implements StaticUIComponent {
    TOP_LINE("""
          ╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
          ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
          """, 2),
    BOTTOM_LINE("""
          ╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
          ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝  
          """, 2);

    private final String ascii;
    private final int linesOfContent;

    AsciiArtDecorations(String ascii, int linesOfContent){
        this.ascii = ascii;
        this.linesOfContent = linesOfContent;
    }

    public String getText(){
        return ascii;
    }

    public int getLinesOfContent() {
        return linesOfContent;
    }
}
