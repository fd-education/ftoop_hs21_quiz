package ch.ffhs.quiz.game.gamesteps;

import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
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
        gameContext = new GameContext(List.of(player1, player2), questions);
        feedbackStep = new FeedbackStep(gameContext);
    }

    @Test
    void process_positive_simple() {

    }
}