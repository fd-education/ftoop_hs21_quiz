package ch.ffhs.quiz.client.ui.components.text;

import ch.ffhs.quiz.client.ui.components.interfaces.DynamicUIComponent;

/**
 * Enum containing all the required, adaptable text blocks for the user interface.
 */
@SuppressWarnings("SpellCheckingInspection")
public enum DynamicTextComponent implements DynamicUIComponent {
    NAME_RESERVED("     %s ist bereits vergeben."),
    NAME_INVALID("     %s kann nicht verarbeitet werden. \n     Dein Name muss mehr als 3 Buchstaben enthalten."),
    PERSONALIZED_WELCOME("     Hallo %s!"),

    TIME_ALERT("     ACHTUNG! WENIGER ALS %s SEKUNDEN VERBLEIBEN!"),

    ANSWER_INVALID("     %s kann keine Antwort sein. \n     Deine Antwort muss A, B oder C lauten."),
    CORRECT_ANSWER_TOO_SLOW("     Schade. Deine Antwort war korrekt, aber %s war schneller"),
    WRONG_ANSWER("     Deine Antwort war falsch. %s hat gewonnen.");

    private final String component;

    DynamicTextComponent(String component){
        this.component = component;
    }

    /**
     * Get the required, adapted text component as a String
     *
     * @param complement the String to complement the desired output
     * @return a complemented String text component
     */
    public String getComponent(String complement){
        // for NAME_/ ANSWER_INVALID a validity check is not required as it is their purpose to print invalid values
        if((!this.component.equals(NAME_INVALID.component) && !this.component.equals(ANSWER_INVALID.component)) && complement.isBlank())
            throw new IllegalArgumentException("complement must not be empty or consist of only whitespace");

        return String.format(component, complement);
    }
}
