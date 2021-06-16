package ch.ffhs.quiz.client.ui.components.ascii;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AsciiArtNumbersTest {

    @Test
    void getTextByIndexTest_positive() {
        String EXPECTED_ZERO = """
                                                            ██████╗
                                                           ██╔═████╗
                                                           ██║██╔██║
                                                           ████╔╝██║
                                                           ╚██████╔╝
                                                            ╚═════╝
            """;

        String EXPECTED_FIVE = """
                                                           ███████╗
                                                           ██╔════╝
                                                           ███████╗
                                                           ╚════██║
                                                           ███████║
                                                           ╚══════╝
            """;

        assertEquals(EXPECTED_ZERO, AsciiArtNumbers.getText(0));
        assertEquals(EXPECTED_FIVE, AsciiArtNumbers.getText(5));
    }

    @Test
    void getTextByIndexTest_negative() throws Exception{
        assertThrows(IllegalArgumentException.class, () -> AsciiArtNumbers.getText(-1));
        assertThrows(IllegalArgumentException.class, () -> AsciiArtNumbers.getText(6));

        String EXPECTED_EXCEPTION_TEXT = "AsciiArtNumbers can only return numbers between  0 and 5";

        try{
            AsciiArtNumbers.getText(-1);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED_EXCEPTION_TEXT, iAEx.getMessage());
        }
        try{
            AsciiArtNumbers.getText(6);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED_EXCEPTION_TEXT, iAEx.getMessage());
        }
    }
}