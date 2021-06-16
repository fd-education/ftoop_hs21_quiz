package ch.ffhs.quiz.client.ui;

import org.junit.jupiter.api.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.*;
import static ch.ffhs.quiz.client.ui.AnsiTerminal.*;

class AnsiTerminalTest {
    String ANSI_PREFIX = "\033[";

    @Test
    void moveCursorDownTest() throws Exception{
        String EXPECTED = ANSI_PREFIX + "2E";

        String output = tapSystemOutNormalized(() -> moveCursorDown(2));

        assertEquals(EXPECTED, output);
    }

    @Test
    void moveCursorDownTest_Negative(){
        String EXPECTED = "lines must be greater than 0";
        assertThrows(IllegalArgumentException.class, () -> moveCursorDown(0));
        assertThrows(IllegalArgumentException.class, () -> moveCursorDown(-1));

        try{
            moveCursorDown(0);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }

        try{
            moveCursorDown(-1);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }
    }

    @Test
    void moveCursorLeftTest() throws Exception{
        String EXPECTED = ANSI_PREFIX + "3D";

        String output = tapSystemOutNormalized(() -> moveCursorLeft(3));

        assertEquals(EXPECTED, output);
    }

    @Test
    void moveCursorLeftTest_Negative(){
        String EXPECTED = "columns must be greater than 0; use moveCursorRight instead of negative offset";
        assertThrows(IllegalArgumentException.class, () -> moveCursorLeft(0));
        assertThrows(IllegalArgumentException.class, () -> moveCursorLeft(-1));

        try{
            moveCursorLeft(0);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }

        try{
            moveCursorLeft(-1);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }
    }

    @Test
    void moveCursorRightTest() throws Exception{
        String EXPECTED = ANSI_PREFIX + "4C";

        String output = tapSystemOutNormalized(() -> {moveCursorRight(4);});

        assertEquals(EXPECTED, output);
    }

    @Test
    void moveCursorRightTest_Negative(){
        String EXPECTED = "columns must be bigger than 0; use moveCursorLeft instead of negative offset";
        assertThrows(IllegalArgumentException.class, () -> moveCursorRight(0));
        assertThrows(IllegalArgumentException.class, () -> moveCursorRight(-1));

        try{
            moveCursorRight(0);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }

        try{
            moveCursorRight(-1);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }
    }

    @Test
    void positionCursorTest() throws Exception {
        String EXPECTED = ANSI_PREFIX + "4;5H";

        String output = tapSystemOutNormalized(() -> {positionCursor(4,5);});

        assertEquals(EXPECTED, output);
    }

    @Test
    void positionCursorTest_Negative_Line(){
        String EXPECTED = "line must be a positive value";
        assertThrows(IllegalArgumentException.class, () -> positionCursor(-1,5));

        try{
            positionCursor(-1, 5);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }
    }

    @Test
    void positionCursorTest_Negative_Column(){
        String EXPECTED = "column must be a positive value";
        assertThrows(IllegalArgumentException.class, () -> positionCursor(5,-1));

        try{
            positionCursor(5, -1);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }
    }

    @Test
    void saveCursorPosTest() throws Exception {
        String EXPECTED = ANSI_PREFIX + "s";

        String output = tapSystemOutNormalized(() -> saveCursorPos());

        assertEquals(EXPECTED, output);
    }

    @Test
    void restoreCursorPosTest() throws Exception {
        String EXPECTED = ANSI_PREFIX + "u";

        String output = tapSystemOutNormalized(() -> restoreCursorPos());

        assertEquals(EXPECTED, output);
    }

    @Test
    void clearTerminalTest() throws Exception {
        String EXPECTED = ANSI_PREFIX + "H" + ANSI_PREFIX + "2J";

        String output = tapSystemOutNormalized(() -> clearTerminal());

        assertEquals(EXPECTED, output);
    }

    @Test
    void clearLineTest() throws Exception {
        String EXPECTED = ANSI_PREFIX + "2K";

        String output = tapSystemOutNormalized(() -> clearLine());

        assertEquals(EXPECTED, output);
    }

    @Test
    void clearRemainingOfLineTest() throws Exception {
        String EXPECTED = ANSI_PREFIX + "K";

        String output = tapSystemOutNormalized(() -> clearRemainingOfLine());

        assertEquals(EXPECTED, output);
    }

    @Test
    void clearNumberOfLinesTest() throws Exception {
        String EXPECTED = "\u001B[s\u001B[2K\u001B[1E\u001B[2K\u001B[1E\u001B[2K\u001B[1E\u001B[2K\u001B[1E\u001B[2K\u001B[1E\u001B[u";

        String output = tapSystemOutNormalized(() -> clearNumberOfLines(5));

        assertEquals(EXPECTED, output);
    }

    @Test
    void clearNumberOfLinesTest_Negative(){
        String EXPECTED = "number must be a positive value";
        assertThrows(IllegalArgumentException.class, () -> clearNumberOfLines(-1));

        try{
            clearNumberOfLines(-1);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }
    }
}