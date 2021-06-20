package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.messages.RoundSummaryMessage;
import ch.ffhs.quiz.messages.ScoreboardEntry;
import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoundSummaryStepTest {
    GameContext gameContext;
    @Mock
    Player player1;
    @Mock
    Player player2;
    RoundSummaryStep roundSummaryStep;
    List<Question> questions;

    @BeforeEach
    void setUp() {
        questions = List.of(mock(Question.class));
        when(player1.getId()).thenReturn(0);
        when(player2.getId()).thenReturn(1);
        gameContext = new GameContext(List.of(player1, player2), questions);
        gameContext.nextRound();
        roundSummaryStep = new RoundSummaryStep(gameContext);
    }

    @Test
    void process_positive_simple() {
        when(player1.getScore()).thenReturn(0);
        when(player2.getScore()).thenReturn(1);
        when(player1.getName()).thenReturn("Player 1");
        when(player2.getName()).thenReturn("Player 2");

        roundSummaryStep.process();
        RoundSummaryMessage expectedMessage = new RoundSummaryMessage(List.of(new ScoreboardEntry("Player 2", 1), new ScoreboardEntry("Player 1", 0)), true);
        verify(player1).send(expectedMessage);
        verify(player2).send(expectedMessage);
    }

    @Test
    void process_positive_noPoints() {
        when(player1.getName()).thenReturn("Player 1");
        when(player2.getName()).thenReturn("Player 2");
        roundSummaryStep.process();
        // TODO:
        RoundSummaryMessage expectedMessage = new RoundSummaryMessage(List.of(new ScoreboardEntry("Player 1", 0), new ScoreboardEntry("Player 2", 0)), true);
        verify(player1).send(expectedMessage);
        verify(player2).send(expectedMessage);
    }
}