package ch.ffhs.quiz.game.gamesteps;

import ch.ffhs.quiz.messages.Message;
import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoreboardStepTest {
    GameContext gameContext;
    @Mock
    Player player1;
    @Mock
    Player player2;
    ScoreboardStep scoreboardStep;
    List<Question> questions;

    @BeforeEach
    void setUp() {
        questions = new ArrayList<>();
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

        final String expectedScoreboard = "Scoreboard for round 0:\n1. Place: Player 1 with 1 Points\n2. Place: Player 0 with 0 Points\n";
        verify(player1).send(argThat(isScoreboard(expectedScoreboard)));
        verify(player2).send(argThat(isScoreboard(expectedScoreboard)));
    }

    private ArgumentMatcher<Message> isScoreboard(String expectedScoreboard) {
        return message -> message.getText().equals(expectedScoreboard);
    }

    @Test
    void process_positive_noPoints() {
        scoreboardStep.process();

        final String expectedScoreboard = """
                Scoreboard for round 0:
                1. Place: Player 0 with 0 Points
                2. Place: Player 1 with 0 Points
                """;
        verify(player1).send(argThat(isScoreboard(expectedScoreboard)));
        verify(player2).send(argThat(isScoreboard(expectedScoreboard)));
    }
}