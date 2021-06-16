package ch.ffhs.quiz.client.ui.components.text;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicTextComponentTest {

    @Test
    void getComponent_NAME_RESERVED() {
        String EXPECTED = "     Player ist bereits vergeben.";
        assertEquals(EXPECTED, DynamicTextComponent.NAME_RESERVED.getComponent("Player"));
    }

    @Test
    void getComponent_NAME_INVALID() {
        String EXPECTED = "     Me kann nicht verarbeitet werden. \n     Dein Name muss mehr als 3 Buchstaben enthalten.";
        assertEquals(EXPECTED, DynamicTextComponent.NAME_INVALID.getComponent("Me"));
    }

    @Test
    void getComponent_PERSONALIZED_WELCOME() {
        String EXPECTED = "     Hallo Player!";
        assertEquals(EXPECTED, DynamicTextComponent.PERSONALIZED_WELCOME.getComponent("Player"));
    }

    @Test
    void getComponent_TIME_ALERT() {
        String EXPECTED = "     ACHTUNG! WENIGER ALS 15 SEKUNDEN VERBLEIBEN!";
        assertEquals(EXPECTED, DynamicTextComponent.TIME_ALERT.getComponent("15"));
    }

    @Test
    void getComponent_ANSWER_INVALID() {
        String EXPECTED = "     X kann keine Antwort sein. \n     Deine Antwort muss A, B oder C lauten.";
        assertEquals(EXPECTED, DynamicTextComponent.ANSWER_INVALID.getComponent("X"));
    }

    @Test
    void getComponent_CORRECT_ANSWER_TOO_SLOW() {
        String EXPECTED = "     Schade. Deine Antwort war korrekt, aber Player war schneller";
        assertEquals(EXPECTED, DynamicTextComponent.CORRECT_ANSWER_TOO_SLOW.getComponent("Player"));
    }

    @Test
    void getComponent_WRONG_ANSWER() {
        String EXPECTED = "     Deine Antwort war falsch. Player hat gewonnen.";
        assertEquals(EXPECTED, DynamicTextComponent.WRONG_ANSWER.getComponent("Player"));
    }

    // Test handling of empty string as complement
    String EXCEPTION_MESSAGE = "complement must not be empty or consist of only whitespace";

    @Test
    void getComponent_EMPTY_STRING() {
        assertThrows(IllegalArgumentException.class, () -> DynamicTextComponent.NAME_RESERVED.getComponent(""));

        try{
            DynamicTextComponent.ANSWER_INVALID.getComponent("");
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXCEPTION_MESSAGE, iAEx.getMessage());
        }
    }

    @Test
    void getComponent_ONLY_WHITESPACE() {
        assertThrows(IllegalArgumentException.class, () -> DynamicTextComponent.WRONG_ANSWER.getComponent("     "));

        try{
            DynamicTextComponent.ANSWER_INVALID.getComponent("  ");
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXCEPTION_MESSAGE, iAEx.getMessage());
        }
    }
}