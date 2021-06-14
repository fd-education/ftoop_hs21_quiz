package ch.ffhs.quiz.client.ui.components.text;

import ch.ffhs.quiz.client.ui.components.interfaces.DynamicUIComponent;

public enum DynamicTextComponent implements DynamicUIComponent {
    NAME_RESERVED("     %s ist bereits vergeben."),
    NAME_INVALID("     %s kann nicht verarbeitet werden. \n     Dein Name muss mehr als 3 Buchstaben enthalten."),
    PERSONALIZED_WELCOME("     Hallo %s!"),

    TIME_ALERT("     ACHTUNG! WENIGER ALS %s SEKUNDEN VERBLEIBEN!"),

    ANSWER_INVALID("     %s kann keine Antwort sein. \n     Deine Antwort muss A, B oder C lauten."),
    CORRECT_ANSWER("     Schade. Deine Antwort war korrekt, aber %s war schneller"),
    WRONG_ANSWER("     Deine Antwort war falsch. %s hat gewonnen.");

    private final String interaction;

    DynamicTextComponent(String interaction){
        this.interaction = interaction;
    }

    public String getText(String text){
        return String.format(interaction, text);
    }
}
