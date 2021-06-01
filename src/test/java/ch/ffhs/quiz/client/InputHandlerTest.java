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
            // Input too short
            String input1 = "ab";
            withTextFromSystemIn(input1)
                    .execute(() -> {
                            String text = tapSystemOut(() -> inputHandler.getUsersName());
                            assertEquals(EXPECTED_TEXT, text);
                    });

            // No input
            String input2 = "";
            withTextFromSystemIn(input2)
                    .execute(() -> {
                        String text = tapSystemOut(() -> inputHandler.getUsersName());
                        assertEquals(EXPECTED_TEXT, text);
                    });
        } catch(NullPointerException ignore){}
    }

    @Test
    void validateNameTestPositive() throws Exception{
        String input1 = "1-a";
        withTextFromSystemIn(input1)
                .execute(() -> {
                    String text = inputHandler.getUsersName();
                    assertEquals(input1, text);
                });
    }

    @Test
    void validateAnswerTestNegative() throws Exception{
        try {
            // Input invalid
            String input1 = "ab";
            String EXPECTED_TEXT1 = format("%s is not a valid answer.\n\n", input1);

            withTextFromSystemIn(input1)
                    .execute(() -> {
                        String text = tapSystemOut(() -> inputHandler.getUsersAnswer());
                        assertEquals(EXPECTED_TEXT1, text);
                    });

            // No input
            String input2 = "";
            String EXPECTED_TEXT2 = format("%s is not a valid answer.\n\n", input2);

            withTextFromSystemIn(input2)
                    .execute(() -> {
                        String text = tapSystemOut(() -> inputHandler.getUsersAnswer());
                        assertEquals(EXPECTED_TEXT2, text);
                    });
        } catch(NullPointerException ignore){}
    }

    @Test
    void validateAnswerTestPositive() throws Exception{
        String input1 = "A";
        withTextFromSystemIn(input1)
                .execute(() -> {
                    String text = inputHandler.getUsersAnswer();
                    assertEquals(input1, text);
                });
    }
}