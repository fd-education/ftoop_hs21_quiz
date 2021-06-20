package ch.ffhs.quiz.client.ui.components.ascii;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AsciiArtTableTest {

    @Test
    void getTableTopTest_LENGTH15() {
        String EXPECTED_LENGTH15 = "     ╔═════╦═════════════════════╦═════╗\n";
        assertEquals(EXPECTED_LENGTH15, AsciiArtTable.getTableTop(15));
    }


    @Test
    void getTableBottomTest_LENGTH15() {
        String EXPECTED_LENGTH15 = "     ╚═════╩═════════════════════╩═════╝\n";
        assertEquals(EXPECTED_LENGTH15, AsciiArtTable.getTableBottom(15));
    }

    @Test
    void getCellBordersWithContentTest_RANK1_SCORE1() {
        String EXPECTED_RANK1_SCORE1 = "     ║ 1.  ║ Player     ║ 1   ║\n";
        assertEquals(EXPECTED_RANK1_SCORE1, AsciiArtTable.getCellBordersWithContent(6, 1, "Player", 1));
    }

    @Test
    void getCellBordersWithContentTest_RANK10_SCORE10() {
        String EXPECTED_RANK10_SCORE10 = "     ║ 10. ║ Player     ║ 10  ║\n";
        assertEquals(EXPECTED_RANK10_SCORE10, AsciiArtTable.getCellBordersWithContent(6, 10, "Player", 10));
    }

    @Test
    void getCellBordersWithContentTest_RANK10_SCORE100() {
        String EXPECTED_RANK10_SCORE100 = "     ║ 10. ║ Player     ║ 100 ║\n";
        assertEquals(EXPECTED_RANK10_SCORE100, AsciiArtTable.getCellBordersWithContent(6, 10, "Player", 100));
    }

    // AsciiArtTable has a helper method to check all params of every method.
    // Therefore we only test the error handling with one method (getCellBordersWithContent()),
    // that takes and checks all the params that the others require as well.
    final Class<IllegalArgumentException> EXPECTED_EXCEPTION = IllegalArgumentException.class;

    // Test for invalid cell lengths (<= 0)
    @Test
    void getCellBordersWithContent_INVALID_LENGTH(){
        String EXPECTED_LENGTH_MESSAGE = "length must be greater than 0";

        assertThrows(EXPECTED_EXCEPTION, () -> AsciiArtTable.getCellBordersWithContent(0, 1, "Player", 1));

        try{
            AsciiArtTable.getCellBordersWithContent(0, 1, "Player", 1);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED_LENGTH_MESSAGE, iAEx.getMessage());
        }

        assertThrows(EXPECTED_EXCEPTION, () -> AsciiArtTable.getCellBordersWithContent(-1, 1, "Player", 1));

        try{
            AsciiArtTable.getCellBordersWithContent(-1, 1, "Player", 1);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED_LENGTH_MESSAGE, iAEx.getMessage());
        }
    }

    // Test for invalid ranks (<= 0)
    @Test
    void getCellBordersWithContent_INVALID_RANK(){
        String EXPECTED_RANK_MESSAGE = "rank must be greater than 0";

        assertThrows(EXPECTED_EXCEPTION, () -> AsciiArtTable.getCellBordersWithContent(1, 0, "Player", 1));

        try{
            AsciiArtTable.getCellBordersWithContent(1, 0, "Player", 1);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED_RANK_MESSAGE, iAEx.getMessage());
        }

        assertThrows(EXPECTED_EXCEPTION, () -> AsciiArtTable.getCellBordersWithContent(1, -1, "Player", 1));

        try{
            AsciiArtTable.getCellBordersWithContent(1, -1, "Player", 1);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED_RANK_MESSAGE, iAEx.getMessage());
        }
    }

    // Test for invalid names (empty or only whitespace)
    @Test
    void getCellBordersWithContent_INVALID_NAME(){
        String EXPECTED_NAME_MESSAGE = "name must not be empty or whitespace only";

        assertThrows(EXPECTED_EXCEPTION, () -> AsciiArtTable.getCellBordersWithContent(1, 1, "", 1));

        try{
            AsciiArtTable.getCellBordersWithContent(1, 1, "", 1);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED_NAME_MESSAGE, iAEx.getMessage());
        }

        assertThrows(EXPECTED_EXCEPTION, () -> AsciiArtTable.getCellBordersWithContent(1, 1, "   ", 1));

        try{
            AsciiArtTable.getCellBordersWithContent(1, 1, "  ", 1);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED_NAME_MESSAGE, iAEx.getMessage());
        }

        String EXPECTED_NAMENULL_MESSAGE = "name must not be null";
        assertThrows(NullPointerException.class, () -> AsciiArtTable.getCellBordersWithContent(1, -1, null, 1));

        try{
            AsciiArtTable.getCellBordersWithContent(1, 1, null, 1);
        } catch(NullPointerException npe){
            assertEquals(EXPECTED_NAMENULL_MESSAGE, npe.getMessage());
        }
    }

    // Test for invalid scores (<0)
    @Test
    void getCellBordersWithContent_INVALID_SCORE(){
        String EXPECTED_SCORE_MESSAGE = "score must be positive";

        assertThrows(EXPECTED_EXCEPTION, () -> AsciiArtTable.getCellBordersWithContent(1, -1, "Player", -1));

        try{
            AsciiArtTable.getCellBordersWithContent(1, 1, "Player", -1);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED_SCORE_MESSAGE, iAEx.getMessage());
        }
    }
}