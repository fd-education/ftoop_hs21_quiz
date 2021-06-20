package ch.ffhs.quiz.client.ui;

import org.junit.jupiter.api.Test;

import static ch.ffhs.quiz.client.ui.UserInterfaceUtils.*;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceUtilsTest {

    @Test
    void printWithDefaultStyleTest() throws Exception {
        String EXPECTED = "\u001B[1;34mTest\u001B[0m";

        String output = tapSystemOutNormalized(()->printWithDefaultStyle("Test"));
        assertEquals(EXPECTED, output);
    }

    @Test
    void printWithDefaultStyleTest_Negative_TextNull(){
        String EXPECTED = "text must not be null";
        assertThrows(NullPointerException.class, () -> printWithDefaultStyle(null));

        try{
            printWithDefaultStyle(null);
        } catch(NullPointerException npe){
            assertEquals(EXPECTED, npe.getMessage());
        }
    }

    @Test
    void printWithDefaultStyleTest_Negative_TextEmpty(){
        String EXPECTED = "text must not be empty or whitespace only";
        assertThrows(IllegalArgumentException.class, () -> printWithDefaultStyle(" "));

        try{
            printWithDefaultStyle(" ");
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }
    }

    @Test
    void createWithDefaultStyleTest(){
        String EXPECTED = "\u001B[1;94mTest\u001B[0m";

        String output = createWithDefaultStyle("Test");
        assertEquals(EXPECTED, output);
    }

    @Test
    void createWithDefaultStyleTest_Negative_TextNull(){
        String EXPECTED = "text must not be null";
        assertThrows(NullPointerException.class, () -> createWithDefaultStyle(null));

        try{
            createWithDefaultStyle(null);
        } catch(NullPointerException npe){
            assertEquals(EXPECTED, npe.getMessage());
        }
    }

    @Test
    void createWithDefaultStyleTest_Negative_TextEmpty(){
        String EXPECTED = "text must not be empty or whitespace only";
        assertThrows(IllegalArgumentException.class, () -> createWithDefaultStyle("  "));

        try{
            createWithDefaultStyle("  ");
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }
    }

    @Test
    void printLetterByLetterTest() throws Exception {
        String EXPECTED = "Test";

        String output = tapSystemOutNormalized(()->printLetterByLetter("Test", Delay.SLOW));
        assertEquals(EXPECTED, output);
    }

    @Test
    void printLetterByLetterTest_Negative_TextNull(){
        String EXPECTED = "text must not be null";
        assertThrows(NullPointerException.class, () -> printLetterByLetter(null, Delay.FAST));

        try{
            printLetterByLetter(null, Delay.FAST);
        } catch(NullPointerException npe){
            assertEquals(EXPECTED, npe.getMessage());
        }
    }

    @Test
    void printLetterByLetterTest_Negative_TextEmpty(){
        String EXPECTED = "text must not be empty or whitespace only";
        assertThrows(IllegalArgumentException.class, () -> printLetterByLetter("  ", Delay.FAST));
        assertThrows(IllegalArgumentException.class, () -> printLetterByLetter("", Delay.FAST));

        try{
            printLetterByLetter("  ", Delay.FAST);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }

        try{
            printLetterByLetter("", Delay.FAST);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }
    }

    @Test
    void printLetterByLetterTest_Negative_Delay(){
        String EXPECTED = "delay must not be null";
        assertThrows(NullPointerException.class, () -> printLetterByLetter("Test", null));

        try{
            printLetterByLetter("Test", null);
        } catch(NullPointerException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }
    }

    @Test
    void splitPhraseTest(){
        String EXPECTED = """
                Cut the phrase into several lines that are at
                around a desired length without breaking a word -
                clean so to say - to guarantee a clean looking
                user interface.""";

        String input = "Cut the phrase into several lines that are at around a desired length without breaking a word - clean so to say - to guarantee a clean looking user interface.";

        String output = splitPhraseAtSpace(input, 50);
        assertEquals(EXPECTED, output);
    }

    @Test
    void splitPhraseTest_Negative_PhraseNull(){
        String EXPECTED = "phrase must not be null";
        assertThrows(NullPointerException.class, () -> splitPhraseAtSpace(null, 1));

        try{
            splitPhraseAtSpace(null, 1);
        } catch(NullPointerException npe){
            assertEquals(EXPECTED, npe.getMessage());
        }
    }

    @Test
    void splitPhraseTest_Negative_PhraseEmpty(){
        String EXPECTED = "phrase must not be empty or whitespace only";
        assertThrows(IllegalArgumentException.class, () -> splitPhraseAtSpace("  ", 50));
        assertThrows(IllegalArgumentException.class, () -> splitPhraseAtSpace("", 50));

        try{
            splitPhraseAtSpace("  ", 50);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }

        try{
            splitPhraseAtSpace("", 50);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }
    }

    @Test
    void splitPhraseTest_Negative_LineLength(){
        String EXPECTED = "maxLineLength must be greater than zero";
        assertThrows(IllegalArgumentException.class, () -> splitPhraseAtSpace("Test", -1));
        assertThrows(IllegalArgumentException.class, () -> splitPhraseAtSpace("Test", 0));

        try{
            splitPhraseAtSpace("Test", -1);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }

        try{
            splitPhraseAtSpace("Test", 0);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }
    }
}