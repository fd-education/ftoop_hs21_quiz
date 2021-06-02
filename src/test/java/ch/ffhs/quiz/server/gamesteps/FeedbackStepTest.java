package ch.ffhs.quiz.server.gamesteps;

import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.server.GameContext;
import ch.ffhs.quiz.server.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FeedbackStepTest {
    GameContext gameContext;
    @Mock
    Player player1;
    @Mock
    Player player2;
    FeedbackStep feedbackStep;
    List<Question> questions;


    @BeforeEach
    void setUp() {
        questions = new ArrayList<>();
        when(player1.getId()).thenReturn(0);
        when(player2.getId()).thenReturn(1);
        gameContext = new GameContext(List.of(player1, player2), questions);
        feedbackStep = new FeedbackStep(gameContext);
    }

    @Test
    void process_positive_simple() {

    }
}