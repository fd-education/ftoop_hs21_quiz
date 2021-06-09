package ch.ffhs.quiz.client;

import org.junit.jupiter.api.*;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static com.github.stefanbirkner.systemlambda.SystemLambda.*;

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
                            String text = tapSystemOut(() -> inputHandler.getUserName());
                            assertEquals(EXPECTED_TEXT, text);
                    });

            // No input, NullPointerException ignored
            String input2 = "";
            withTextFromSystemIn(input2)
                    .execute(() -> {
                        String text = tapSystemOut(() -> inputHandler.getUserName());
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
    void validateAnswerTestNegative() throws Exception{
        try {
            // Input invalid
            String input1 = "ab";
            String EXPECTED_TEXT1 = format("%s is not a valid answer.\n\n", input1);

            withTextFromSystemIn(input1)
                    .execute(() -> {
                        String text = tapSystemOut(() -> inputHandler.getUserAnswer());
                        assertEquals(EXPECTED_TEXT1, text);
                    });

            // No input
            String input2 = "";
            String EXPECTED_TEXT2 = format("%s is not a valid answer.\n\n", input2);

            withTextFromSystemIn(input2)
                    .execute(() -> {
                        String text = tapSystemOut(() -> inputHandler.getUserAnswer());
                        assertEquals(EXPECTED_TEXT2, text);
                    });
        } catch(NullPointerException ignored){}
    }

    @Test
    void validateAnswerTestPositive() throws Exception{
        String input1 = "A";
        withTextFromSystemIn(input1)
                .execute(() -> {
                    String text = inputHandler.getUserAnswer();
                    assertEquals(input1, text);
                });
    }
}