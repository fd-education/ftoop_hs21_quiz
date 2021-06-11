package ch.ffhs.quiz.client.textinterface;

public enum TextComponents {
    WELCOME("""
            
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
            """),
    EXPLANATION("""
            Ein ganz normales Quiz... wie schwer kann das schon sein.
            Ziel ist es, die richtige Antwort so schnell wie möglich zu finden 
            und den entsprechenden Buchstaben einzutippen.
            Vorsicht: Die erste Antwort zählt. Überleg dir also gut, ob du dir dabei sicher bist !
    
            Sollte niemand die Antwort wissen, geht die Runde nach 60 Sekunden vorbei.
    
            Viel Spass (und Glück, falls du's nötig hast)!
            """),
    WAITING_FOR_PLAYERS("Warte auf andere Spieler"),

    ASK_FOR_NAME("Tippe einen Namen ein :"),
    NAME_RESERVED("Dieser Name ist bereits vergeben."),
    NAME_INVALID("Dein Name muss mehr als 3 Buchstaben enthalten"),

    ASK_FOR_ANSWER("Tippe deine Antwort ein :"),
    ANSWER_INVALID("Deine Antwort muss A, B oder C lauten."),
    PLAYER_WON("Super! Du hast diese Runde gewonnen!"),
    CORRECT_ANSWER("Schade. Deine Antwort war korrekt, aber %s war schneller"),
    WRONG_ANSWER("Deine Antwort war falsch. %s hat gewonnen."),
    NO_PLAYER_CORRECT("Niemand wusste die richtige Antwort.");

    private final String component;

    TextComponents(String component){
        this.component = component;
    }

    public String getComponent(){
        return component;
    }
}
