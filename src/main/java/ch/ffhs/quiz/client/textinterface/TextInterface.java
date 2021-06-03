package ch.ffhs.quiz.client.textinterface;

public enum TextInterface {
    WELCOME("""
            
            ███████╗ █████╗  ██████╗ █████╗ ██████╗ ███████╗     ██████╗ ██╗   ██╗██╗███████╗
            ██╔════╝██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝    ██╔═══██╗██║   ██║██║╚══███╔╝
            █████╗  ███████║██║     ███████║██║  ██║█████╗      ██║   ██║██║   ██║██║  ███╔╝
            ██╔══╝  ██╔══██║██║     ██╔══██║██║  ██║██╔══╝      ██║▄▄ ██║██║   ██║██║ ███╔╝
            ██║     ██║  ██║╚██████╗██║  ██║██████╔╝███████╗    ╚██████╔╝╚██████╔╝██║███████╗
            ╚═╝     ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═════╝ ╚══════╝     ╚══▀▀═╝  ╚═════╝ ╚═╝╚══════╝
            """),
    QUESTION("""
             ██████╗ ██╗   ██╗███████╗███████╗████████╗██╗ ██████╗ ███╗   ██╗
            ██╔═══██╗██║   ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
            ██║   ██║██║   ██║█████╗  ███████╗   ██║   ██║██║   ██║██╔██╗ ██║
            ██║▄▄ ██║██║   ██║██╔══╝  ╚════██║   ██║   ██║██║   ██║██║╚██╗██║
            ╚██████╔╝╚██████╔╝███████╗███████║   ██║   ██║╚██████╔╝██║ ╚████║
             ╚══▀▀═╝  ╚═════╝ ╚══════╝╚══════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
            """),
    END("""
            ███████╗███╗   ██╗██████╗
            ██╔════╝████╗  ██║██╔══██╗
            █████╗  ██╔██╗ ██║██║  ██║
            ██╔══╝  ██║╚██╗██║██║  ██║
            ███████╗██║ ╚████║██████╔╝
            ╚══════╝╚═╝  ╚═══╝╚═════╝
            """),
    EXPLANATION("""
            Ein ganz normales Quiz... wie schwer kann das schon sein.
            Ziel ist es, die richtige Antwort so schnell wie möglich zu finden 
            und den entsprechenden Buchstaben einzutippen.
            Vorsicht: Die erste Antwort zählt. Überleg dir also gut, ob du dir dabei sicher bist !
    
            Sollte niemand die Antwort wissen, geht die Runde nach 60 Sekunden vorbei. \u23F1
    
            Viel Spass (und Glück, falls du's nötig hast \uD83D\uDE09)!
            """);

    private final String component;

    TextInterface(String component){
        this.component = component;
    }

    public String getComponent(){
        return component;
    }
}
