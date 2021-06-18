package ch.ffhs.quiz.client.ui.components;

import ch.ffhs.quiz.messages.ScoreboardEntry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScoreboardTest {
    static Scoreboard scoreboardLong, scoreboardShort;
    static List<ScoreboardEntry> scoresLong, scoresShort;
    static final Class<IllegalArgumentException> EXPECTED_EXCEPTION = IllegalArgumentException.class;

    @BeforeAll
    static void setup(){
        scoresLong = new ArrayList<>(
                List.of(new ScoreboardEntry("Player1", 950),
                        new ScoreboardEntry("Player2", 900),
                        new ScoreboardEntry("Player3", 800),
                        new ScoreboardEntry("Player4", 700),
                        new ScoreboardEntry("Player5", 600),
                        new ScoreboardEntry("Player6", 500),
                        new ScoreboardEntry("Player7", 400),
                        new ScoreboardEntry("Player8", 300),
                        new ScoreboardEntry("Player9", 200),
                        new ScoreboardEntry("Player10", 100),
                        new ScoreboardEntry("Player11", 90),
                        new ScoreboardEntry("Player12", 80),
                        new ScoreboardEntry("Player13", 70),
                        new ScoreboardEntry("Player14", 60),
                        new ScoreboardEntry("Player15", 50)
                )
        );

        scoresShort = new ArrayList<>(
                List.of(new ScoreboardEntry("Player1", 950),
                        new ScoreboardEntry("Player2", 900),
                        new ScoreboardEntry("Player3", 800),
                        new ScoreboardEntry("Player4", 700),
                        new ScoreboardEntry("Player5", 600),
                        new ScoreboardEntry("Player6", 500)
                )
        );

        scoreboardLong = new Scoreboard(scoresLong);
        scoreboardShort = new Scoreboard(scoresShort);
    }

    @Test
    void getScoreboardForPlayer_LONG_PLAYERON() {
        String EXPECTED = """
                ╔═════╦══════════════╦═════╗
                ║ 1   ║ Player1      ║ 950 ║
                ║ 2   ║ Player2      ║ 900 ║
                ║ 3   ║ Player3      ║ 800 ║
                ║ 4   ║ Player4      ║ 700 ║
                ║ 5   ║ Player5      ║ 600 ║
                ║ 6   ║ Player6      ║ 500 ║
                ║ 7   ║ Player7      ║ 400 ║
                ║ 8   ║ Player8      ║ 300 ║
                ║ 9   ║ Player9      ║ 200 ║
                ║ 10  ║ Player10     ║ 100 ║
                ╚═════╩══════════════╩═════╝
                 """;

        assertEquals(EXPECTED.indent(39), scoreboardLong.getScoreboardForPlayer("Player1"));
    }

    @Test
    void getScoreboardForPlayer_LONG_PLAYEROFF() {
        String EXPECTED = """
                ╔═════╦══════════════╦═════╗
                ║ 1   ║ Player1      ║ 950 ║
                ║ 2   ║ Player2      ║ 900 ║
                ║ 3   ║ Player3      ║ 800 ║
                ║ 4   ║ Player4      ║ 700 ║
                ║ 5   ║ Player5      ║ 600 ║
                ║ 6   ║ Player6      ║ 500 ║
                ║ 7   ║ Player7      ║ 400 ║
                ║ 8   ║ Player8      ║ 300 ║
                ║ 9   ║ Player9      ║ 200 ║
                ║ 10  ║ Player10     ║ 100 ║
                ║ 15  ║ Player15     ║ 50  ║
                ╚═════╩══════════════╩═════╝
                """;

        assertEquals(EXPECTED.indent(39), scoreboardLong.getScoreboardForPlayer("Player15"));
    }

    @Test
    void getScoreboardForPlayer_SHORT() {
        String EXPECTED = """
                ╔═════╦═════════════╦═════╗
                ║ 1   ║ Player1     ║ 950 ║
                ║ 2   ║ Player2     ║ 900 ║
                ║ 3   ║ Player3     ║ 800 ║
                ║ 4   ║ Player4     ║ 700 ║
                ║ 5   ║ Player5     ║ 600 ║
                ║ 6   ║ Player6     ║ 500 ║
                ╚═════╩═════════════╩═════╝
                """;

        assertEquals(EXPECTED.indent(40), scoreboardShort.getScoreboardForPlayer("Player1"));
    }

    @Test
    void getScoreboardForPlayer_NAME_EMPTY() {
        String EXCEPTION_MESSAGE = "name must contain letters and not only whitespace";

        assertThrows(EXPECTED_EXCEPTION, () -> scoreboardLong.getScoreboardForPlayer(""));

        try{
            scoreboardLong.getScoreboardForPlayer("");
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXCEPTION_MESSAGE, iAEx.getMessage());
        }
    }

    @Test
    void getScoreboardForPlayer_NAME_ONLYWHITESPACE() {
        String EXCEPTION_MESSAGE = "name must contain letters and not only whitespace";

        assertThrows(EXPECTED_EXCEPTION, () -> scoreboardLong.getScoreboardForPlayer("    "));

        try{
            scoreboardLong.getScoreboardForPlayer("    ");
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXCEPTION_MESSAGE, iAEx.getMessage());
        }
    }

    @Test
    void getScoreboardForPlayer_PLAYER_NOTPARTICIPANT() {
        String EXCEPTION_MESSAGE = "Player100 does not seem to be a player in this game";

        assertThrows(EXPECTED_EXCEPTION, () -> scoreboardLong.getScoreboardForPlayer("Player100"));

        try{
            scoreboardLong.getScoreboardForPlayer("Player100");
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXCEPTION_MESSAGE, iAEx.getMessage());
        }
    }
}