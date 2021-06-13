package ch.ffhs.quiz.client.ui.components.text;

import ch.ffhs.quiz.client.ui.components.interfaces.StaticUIComponent;

public enum StaticTextComponent implements StaticUIComponent {
    EXPLANATION("""
                 Ein ganz normales Quiz... wie schwer kann das schon sein.
                 Ziel ist es, die richtige Antwort so schnell wie möglich zu finden 
                 und den entsprechenden Buchstaben einzutippen.
                 Vorsicht: Die erste Antwort zählt. Überleg dir also gut, ob du dir dabei sicher bist!
    
                 Sollte niemand die Antwort wissen, geht die Runde nach 60 Sekunden vorbei.
    
                 Viel Spass (und Glück, falls du's nötig hast)!
            """, 8),
    WAITING_FOR_PLAYERS("     Warte auf weitere Spieler", 1),

    ASK_FOR_NAME("     Tippe einen Namen ein: ", 1),

    ASK_FOR_ANSWER("     Tippe deine Antwort ein: ", 1),
    PLAYER_WON("     Super! Du hast diese Runde gewonnen!", 1),
    NO_PLAYER_CORRECT("     Niemand wusste die richtige Antwort.", 1);

    private final String interaction;
    private final int linesOfContent;

    StaticTextComponent(String interaction, int linesOfContent){
        this.interaction = interaction;
        this.linesOfContent = linesOfContent;
    }

    public String getText(){
        return interaction;
    }

    public int getLinesOfContent(){return linesOfContent;}
}
