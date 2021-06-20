package ch.ffhs.quiz.client;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputHandlerTest {
    private static InputHandler inputHandler;

    @BeforeAll
    static void setup() {
        inputHandler = new InputHandler();
    }

    @Test
    void validateNameTest_negative_shortNamesNotAccepted() throws Exception {
        String EXPECTED_TEXT = """
                [1E[1;91m     ab kann nicht verarbeitet werden.\s
                     Dein Name muss mindestens 3 Buchstaben enthalten.[0m[u[K""";
        String input1 = "ab";
        withTextFromSystemIn(input1)
                .execute(() -> {
                    String text = tapSystemOutNormalized(() -> {
                        try {
                            inputHandler.getUserName();
                        } catch (NullPointerException nullPointerException) {
                            assertEquals("read name must not be null", nullPointerException.getMessage());
                        }
                    });
                    System.out.println(text);
                    assertEquals(EXPECTED_TEXT, text);
                });

        String EXPECTED_TEXT2 = """
                [1E[1;91m      kann nicht verarbeitet werden.\s
                     Dein Name muss mindestens 3 Buchstaben enthalten.[0m[u[K""";
        String input2 = "";
        withTextFromSystemIn(input2)
                .execute(() -> {
                    String text = tapSystemOutNormalized(() -> {
                        try {
                            inputHandler.getUserName();
                        } catch (NullPointerException nullPointerException) {
                            assertEquals("read name must not be null", nullPointerException.getMessage());
                        }
                    });
                    assertEquals(EXPECTED_TEXT2, text);
                });
    }

    @Test
    void validateNameTest_negative_unsupportedCharacters() throws Exception {
        String EXPECTED_TEXT = """
                [1E[1;91m    %�{ kann nicht verarbeitet werden.\s
                    Nur Zeichen von A-z, Zahlen und Bindestriche sind erlaubt.[0m[u[K""";
        String input1 = "%è{";
        withTextFromSystemIn(input1)
                .execute(() -> {
                    String text = tapSystemOutNormalized(() -> {
                        try {
                            inputHandler.getUserName();
                        } catch (NullPointerException nullPointerException) {
                            assertEquals("read name must not be null", nullPointerException.getMessage());
                        }
                    });
                    assertEquals(EXPECTED_TEXT, text);
                });
    }

    @Test
    void validateNameTestPositive() throws Exception {
        try {
            String input1 = "1-a";
            withTextFromSystemIn(input1)
                    .execute(() -> {
                        String text = inputHandler.getUserName();
                        assertEquals(input1, text);
                    });
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void validateAnswerTestPositive() throws Exception {
        String input1 = "A";
        int EXPECTED = 0;
        withTextFromSystemIn(input1)
                .execute(() -> {
                    int answerIndex = inputHandler.awaitUserAnswer();
                    assertEquals(EXPECTED, answerIndex);
                });
    }

    @Test
    void validateAnswerTestNegative() throws Exception {
        // invalid input to test behaviour on
        String input1 = "ab";

        // provide a valid input to avoid looping for 60"
        String input2 = "A";
        String EXPECTED = """
                [1E[1;91m     ab kann keine Antwort sein.\s
                     Deine Antwort muss A, B oder C lauten.[0m[u[K""";

        withTextFromSystemIn(input1, input2)
                .execute(() -> {
                    String text = tapSystemOutNormalized(() -> inputHandler.awaitUserAnswer());
                    assertEquals(EXPECTED, text);
                });
    }
}
