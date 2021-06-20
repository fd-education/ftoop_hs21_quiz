package ch.ffhs.quiz.client.ui.components;

import org.junit.jupiter.api.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static org.junit.jupiter.api.Assertions.*;

class TimeAlertRunnableTest {

    @Test
    void ctorTest_Negative(){
        assertThrows(IllegalArgumentException.class, () -> new TimeAlertRunnable(-1));
        assertThrows(IllegalArgumentException.class, () -> new TimeAlertRunnable(0));

        String EXPECTED = "secondsLeft must be greater than 0";

        try{
            new TimeAlertRunnable(-1);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }

        try{
            new TimeAlertRunnable(0);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }
    }

    @Test
    void runTest() throws Exception{
        String EXPECTED = """
                \u001B[s\u001B[5E\u001B[1;91m     ACHTUNG! WENIGER ALS 50 SEKUNDEN VERBLEIBEN!\u001B[0m\u001B[u""";

        TimeAlertRunnable alert = new TimeAlertRunnable(50);

        String output = tapSystemOutNormalized(alert::run);
        assertEquals(EXPECTED, output);
    }
}