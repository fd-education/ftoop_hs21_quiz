package ch.ffhs.quiz.client.ui.components.text;

import ch.ffhs.quiz.client.ui.components.interfaces.DynamicUIComponent;

public enum DynamicTextComponent implements DynamicUIComponent {
    NAME_RESERVED("     %s ist bereits vergeben.", 1),
    NAME_INVALID("     %s kann nicht verarbeitet werden. \n     Dein Name muss mehr als 3 Buchstaben enthalten.", 2),
    PERSONALIZED_WELCOME("     Hallo %s!", 1),

    ANSWER_INVALID("     %s kann keine Antwort sein. \n     Deine Antwort muss A, B oder C lauten.", 3),
    CORRECT_ANSWER("     Schade. Deine Antwort war korrekt, aber %s war schneller", 1),
    WRONG_ANSWER("     Deine Antwort war falsch. %s hat gewonnen.", 1);

    private final String interaction;
    private final int linesOfContent;

    DynamicTextComponent(String interaction, int linesOfContent){
        this.interaction = interaction;
        this.linesOfContent = linesOfContent;
    }

    public String getText(String text){
        return String.format(interaction, text);
    }

    public int getLinesOfContent(){return linesOfContent;}
}
