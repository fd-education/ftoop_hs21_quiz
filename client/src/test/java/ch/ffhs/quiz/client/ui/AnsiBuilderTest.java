package ch.ffhs.quiz.client.ui;

import org.junit.jupiter.api.Test;

import static ch.ffhs.quiz.client.ui.AnsiBuilder.Color.*;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static org.junit.jupiter.api.Assertions.*;

class AnsiBuilderTest {

    @Test
    void create_RED_BRIGHT_FONT() {
        String EXPECTED = "\u001B[1;91mTest\u001B[0m";

        assertEquals(EXPECTED, new AnsiBuilder("Test").setFont(RED, true).create());
    }

    @Test
    void print_GREEN_NONBRIGHT_FONT() throws Exception{
        String EXPECTED = "\u001B[1;32mTest\u001B[0m";

        assertEquals(EXPECTED, tapSystemOutNormalized(() -> new AnsiBuilder("Test").setFont(GREEN, false).print()));
    }

    @Test
    void println_YELLOW_BRIGHT_FONT() throws Exception {
        String EXPECTED = "\u001B[1;93mTest\u001B[0m\n";

        assertEquals(EXPECTED, tapSystemOutNormalized(() -> new AnsiBuilder("Test").setFont(YELLOW, true).println()));
    }

    @Test
    void create_BLUE_BRIGHT_BACKGROUND(){
        String EXPECTED = "\u001B[1;;104mTest\u001B[0m";

        assertEquals(EXPECTED, new AnsiBuilder("Test").setBackground(BLUE, true).create());
    }

    @Test
    void create_RED_NONBRIGHT_BACKGROUND(){
        String EXPECTED = "\u001B[1;;41mTest\u001B[0m";

        assertEquals(EXPECTED, new AnsiBuilder("Test").setBackground(RED, false).create());
    }

    @Test
    void create_RED_BRIGHT_FONT_GREEN_NONBRIGHT_BACKGROUND(){
        String EXPECTED = "\u001B[1;91;42mTest\u001B[0m";

        assertEquals(EXPECTED, new AnsiBuilder("Test").setFont(RED, true).setBackground(GREEN, false).create());
    }

    @Test
    void create_TEXT_EMPTY() {
        assertThrows(IllegalArgumentException.class, () -> new AnsiBuilder("").create());

        try {
            new AnsiBuilder("").create();
        } catch (IllegalArgumentException iAEx) {
            assertEquals("text must contain letters and not be only whitespace", iAEx.getMessage());
        }
    }

    @Test
    void create_TEXT_ONLYWHITESPACE() {
        assertThrows(IllegalArgumentException.class, () -> new AnsiBuilder("  ").create());

        try {
            new AnsiBuilder("  ").create();
        } catch (IllegalArgumentException iAEx) {
            assertEquals("text must contain letters and not be only whitespace", iAEx.getMessage());
        }
    }

    @Test
    void setBackground_NULL() {
        assertThrows(NullPointerException.class, () -> new AnsiBuilder("Test").setFont(null, true).create());

        try {
            new AnsiBuilder("Test").create();
        } catch (NullPointerException NEx) {
            assertEquals("color must not be null", NEx.getMessage());
        }
    }

    @Test
    void setFont_NULL() {
        assertThrows(NullPointerException.class, () -> new AnsiBuilder("Test").setBackground(null, true).create());

        try {
            new AnsiBuilder("Test").create();
        } catch (NullPointerException NEx) {
            assertEquals("color must not be null", NEx.getMessage());
        }
    }
}