package ch.ffhs.quiz.game.gamesteps;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.RoundContext;
import ch.ffhs.quiz.game.gamesteps.impl.FeedbackStep;
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
import static org.mockito.Mockito.when;

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
        gameContext = new GameContext(List.of(player1, player2), questions);
        roundContext = gameContext.getRoundContext();
        gameContext.nextRound();

        feedbackStep = new FeedbackStep(gameContext);
    }

    @Test
    void process_positive_singleCorrectPlayer() {
        roundContext.setWinningPlayer(player1);
        roundContext.addCorrectPlayer(player1);

        feedbackStep.process();

        verify(player1).reward();
        verify(player1).send(new FeedbackMessage("You have won this round!", true));
        verify(player2).send(new FeedbackMessage("Your answer was not correct. Player 0 won the round!", false));
    }

    @Test
    void process_positive_noWinners() {
        feedbackStep.process();

        final FeedbackMessage expectedMessage = new FeedbackMessage("Nobody's answer was correct.", false);
        verify(player1).send(expectedMessage);
        verify(player2).send(expectedMessage);
    }

    @Test
    void process_positive_twoCorrectOneWinningPlayer() {
        roundContext.setWinningPlayer(player1);
        roundContext.addCorrectPlayer(player2);

        feedbackStep.process();

        verify(player1).reward();
        verify(player1).send(new FeedbackMessage("You have won this round!", true));
        verify(player2).send(new FeedbackMessage("Your answer was correct, but Player 0 was faster!", true));
    }
}