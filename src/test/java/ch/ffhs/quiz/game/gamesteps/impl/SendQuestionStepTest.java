package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.QuestionMessage;
import ch.ffhs.quiz.questions.Answer;
import ch.ffhs.quiz.questions.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendQuestionStepTest {
    @Mock
    Question question;
    @Mock
    Player player1;
    @Mock
    Player player2;
    @Mock
    Answer answer1;
    @Mock
    Answer answer2;

    SendQuestionStep sendQuestionStep;

    @BeforeEach
    void setUp() {
        when(question.getAnswers()).thenReturn(List.of(answer1, answer2));
        GameContext gameContext = new GameContext(List.of(player1, player2), List.of(question));
        gameContext.nextRound();
        sendQuestionStep = new SendQuestionStep(gameContext);
    }
    @Test
    void process_positive_simple() {
        when(answer1.toString()).thenReturn("A");
        when(answer2.toString()).thenReturn("B");
        when(question.getQuestionText()).thenReturn("Is it... ?");

        sendQuestionStep.process();

        QuestionMessage expectedMessage = new QuestionMessage("Is it... ?", List.of("A", "B"));
        verify(player1).send(expectedMessage);
        verify(player2).send(expectedMessage);
    }
}