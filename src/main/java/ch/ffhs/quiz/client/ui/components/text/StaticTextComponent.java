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
            """),
    WAITING_FOR_PLAYERS("     Warte auf weitere Spieler"),

    ASK_FOR_NAME("     Tippe einen Namen ein: "),

    ASK_FOR_ANSWER("     Tippe deine Antwort ein: "),
    PLAYER_WON("     Super! Du hast diese Runde gewonnen!"),
    NO_PLAYER_CORRECT("     Niemand wusste die richtige Antwort."),
    THANKS("                           Danke für die Teilnahme an diesem Spiel ! ~N.S.F.");

    private final String interaction;

    StaticTextComponent(String interaction){
        this.interaction = interaction;
    }

    public String getComponent(){
        return interaction;
    }
}
