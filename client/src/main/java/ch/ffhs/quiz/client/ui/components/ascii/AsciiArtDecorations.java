package ch.ffhs.quiz.client.ui.components.ascii;

import ch.ffhs.quiz.client.ui.components.interfaces.StaticUIComponent;

/**
 * Enum for AsciiArt Decoration elements.
 */
public enum AsciiArtDecorations implements StaticUIComponent {
    /**
     * Top line ascii art.
     */
    TOP_LINE("""
          ╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
          ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
          """),
    /**
     * Bottom line ascii art.
     */
    BOTTOM_LINE("""
          ╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
          ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝
          """);

    private final String ascii;

    AsciiArtDecorations(final String ascii){
        this.ascii = ascii;
    }

    /**
     * Get the component in a String representation
     * @return component
     */
    public String getComponent(){
        return ascii;
    }

}
