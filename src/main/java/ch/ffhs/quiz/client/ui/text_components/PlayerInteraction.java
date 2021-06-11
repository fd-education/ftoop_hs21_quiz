package ch.ffhs.quiz.client.ui.text_components;

public enum PlayerInteraction {
    EXPLANATION("""
            Ein ganz normales Quiz... wie schwer kann das schon sein.
            Ziel ist es, die richtige Antwort so schnell wie möglich zu finden 
            und den entsprechenden Buchstaben einzutippen.
            Vorsicht: Die erste Antwort zählt. Überleg dir also gut, ob du dir dabei sicher bist!
    
            Sollte niemand die Antwort wissen, geht die Runde nach 60 Sekunden vorbei.
    
            Viel Spass (und Glück, falls du's nötig hast)!
            """),
    WAITING_FOR_PLAYERS("\nWarte auf andere Spieler"),

    ASK_FOR_NAME("\n\nTippe einen Namen ein: "),
    NAME_RESERVED("%s ist bereits vergeben."),
    NAME_INVALID("\n%s kann nicht verarbeitet werden. \nDein Name muss mehr als 3 Buchstaben enthalten. \nVersuche es nochmal: "),
    PERSONALIZED_WELCOME("\n\nHallo %s!"),

    ASK_FOR_ANSWER("Tippe deine Antwort ein: "),
    ANSWER_INVALID("\n%s kann keine Antwort sein. \nDeine Antwort muss A, B oder C lauten. \nVersuche es nochmal: "),
    PLAYER_WON("Super! Du hast diese Runde gewonnen!"),
    CORRECT_ANSWER("Schade. Deine Antwort war korrekt, aber %s war schneller"),
    WRONG_ANSWER("Deine Antwort war falsch. %s hat gewonnen."),
    NO_PLAYER_CORRECT("Niemand wusste die richtige Antwort.");

    private final String interaction;

    PlayerInteraction(String interaction){
        this.interaction = interaction;
    }

    public String getInteraction(){
        return interaction;
    }

    public String getInteraction(String text){
        return String.format(interaction, text);
    }
}
