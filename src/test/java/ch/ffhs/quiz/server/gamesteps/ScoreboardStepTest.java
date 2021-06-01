package ch.ffhs.quiz.server.gamesteps;

import ch.ffhs.quiz.server.gamesteps.ScoreboardStep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.server.GameContext;
import ch.ffhs.quiz.server.player.Player;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class ScoreboardStepTest {
    GameContext gameContext;
    private List<Question> questions;
    Player player1;
    Player player2;
    ScoreboardStep scoreboardStep;

    @BeforeEach
    void setUp() {
        questions = new ArrayList<>();
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        when(player1.getId()).thenReturn(0);
        when(player2.getId()).thenReturn(1);
        gameContext = new GameContext(List.of(player1, player2), questions);
        scoreboardStep = new ScoreboardStep(gameContext);
    }

    @Test
    void process_positive_simple() {
        when(player1.getScore()).thenReturn(0);
        when(player2.getScore()).thenReturn(1);

        scoreboardStep.process();

        final String expectedScoreboard = """
                Scoreboard for round 0:
                1. Place: Player 1 with 1 Points
                2. Place: Player 0 with 0 Points
                """;
        verify(player1).send(expectedScoreboard);
        verify(player2).send(expectedScoreboard);
    }

    @Test
    void process_positive_noPoints() {
        scoreboardStep.process();

        final String expectedScoreboard = """
                Scoreboard for round 0:
                1. Place: Player 0 with 0 Points
                2. Place: Player 1 with 0 Points
                """;
        verify(player1).send(expectedScoreboard);
        verify(player2).send(expectedScoreboard);
    }
}