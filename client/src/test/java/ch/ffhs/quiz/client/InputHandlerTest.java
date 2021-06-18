package ch.ffhs.quiz.client;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputHandlerTest {
     private static InputHandler inputHandler;

    @BeforeAll
    static void setup(){
        inputHandler = new InputHandler();
    }

    @Test
    void validateNameTestNegative() throws Exception{
        String EXPECTED_TEXT = "Your name must contain at least three characters.";
        try {
            // Input too short, NullPointerException ignored
            String input1 = "ab";
            withTextFromSystemIn(input1)
                    .execute(() -> {
                            String text = tapSystemOutNormalized(() -> inputHandler.getUserName());
                            assertEquals(EXPECTED_TEXT, text);
                    });

            // No input, NullPointerException ignored
            String input2 = "";
            withTextFromSystemIn(input2)
                    .execute(() -> {
                        String text = tapSystemOutNormalized(() -> inputHandler.getUserName());
                        assertEquals(EXPECTED_TEXT, text);
                    });
        } catch(NullPointerException ignored){}
    }

    @Test
    void validateNameTestPositive() throws Exception{
        try {
            String input1 = "1-a";
            withTextFromSystemIn(input1)
                    .execute(() -> {
                        String text = inputHandler.getUserName();
                        assertEquals(input1, text);
                    });
        } catch(NullPointerException ignored){}
    }

    @Test
    void validateAnswerTestPositive() throws Exception{
        String input1 = "A";
        int EXPECTED = 0;
        withTextFromSystemIn(input1)
                .execute(() -> {
                    int answerIndex = inputHandler.awaitUserAnswer();
                    assertEquals(EXPECTED, answerIndex);
                });
    }

    @Test
    void validateAnswerTestNegative() throws Exception{
        // invalid input to test behaviour on
        String input1 = "ab";

        // provide a valid input to avoid looping for 60"
        String input2 = "A";
        String EXPECTED = "\u001B[1E\u001B[1;91m     ab kann keine Antwort sein. \n" +
                "     Deine Antwort muss A, B oder C lauten.\u001B[0m\u001B[u\u001B[K";

        withTextFromSystemIn(input1, input2)
                .execute(() -> {
                    String text = tapSystemOutNormalized(() -> inputHandler.awaitUserAnswer());
                    assertEquals(EXPECTED, text);
                });
    }
}
