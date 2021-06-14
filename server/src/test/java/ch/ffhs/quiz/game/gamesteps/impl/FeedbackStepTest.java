package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.RoundContext;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.FeedbackMessage;
import ch.ffhs.quiz.questions.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackStepTest {
    GameContext gameContext;
    RoundContext roundContext;
    @Mock
    Player player1;
    @Mock
    Player player2;
    FeedbackStep feedbackStep;
    List<Question> questions;


    @BeforeEach
    void setUp() {
        questions = new ArrayList<>();
        questions.add(mock(Question.class));
        when(player1.getId()).thenReturn(0);
        when(player2.getId()).thenReturn(1);
//        when(player2.getName()).thenReturn("Player 2");
        gameContext = new GameContext(List.of(player1, player2), questions);
        gameContext.nextRound();
        roundContext = gameContext.getRoundContext();

        feedbackStep = new FeedbackStep(gameContext);
    }

    @Test
    void process_positive_singleCorrectPlayer() {
        when(player1.getName()).thenReturn("Player 1");
        roundContext.setWinningPlayer(player1);

        feedbackStep.process();

        verify(player1).reward();
        verify(player1).send(new FeedbackMessage(true, true, "Player 1"));
        verify(player2).send(new FeedbackMessage(false, false, "Player 1"));
    }

    @Test
    void process_positive_noWinners() {
        feedbackStep.process();

        final FeedbackMessage expectedMessage = new FeedbackMessage(false, false, "");
        verify(player1).send(expectedMessage);
        verify(player2).send(expectedMessage);
    }

    @Test
    void process_positive_twoCorrectOneWinningPlayer() {
        when(player1.getName()).thenReturn("Player 1");
        roundContext.setWinningPlayer(player1);
        roundContext.addCorrectPlayer(player2);

        feedbackStep.process();
        verify(player1).reward();
        verify(player1).send(new FeedbackMessage(true, true, "Player 1"));
        verify(player2).send(new FeedbackMessage(true, false, "Player 1"));
    }
}