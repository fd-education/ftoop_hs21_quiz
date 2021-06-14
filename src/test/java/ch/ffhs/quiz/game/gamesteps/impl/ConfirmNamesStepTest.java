package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.ConfirmedNameMessage;
import ch.ffhs.quiz.messages.NameMessage;
import ch.ffhs.quiz.questions.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfirmNamesStepTest {
    @Mock
    Player player1;
    @Mock
    Player player2;
    ConfirmNamesStep confirmNameSteps;

    @BeforeEach
    void setUp() {
        GameContext gameContext = new GameContext(List.of(player1, player2), List.of(mock(Question.class)));
        confirmNameSteps = new ConfirmNamesStep(gameContext);
    }

    @Test
    void process_positive_simple() throws IOException {
        when(player1.getName()).thenReturn("");
        when(player2.getName()).thenReturn("");
        when(player1.receive(NameMessage.class)).thenReturn(new NameMessage("Name 1"));
        when(player2.receive(NameMessage.class)).thenReturn(new NameMessage("Name 2"));
        when(player1.hasMessage()).thenReturn(true);
        when(player2.hasMessage()).thenReturn(true);

        confirmNameSteps.process();

        NameMessage expectedNameMessage = new ConfirmedNameMessage("Name 1");
        verify(player1).send(expectedNameMessage);
        expectedNameMessage = new ConfirmedNameMessage("Name 2");
        verify(player2).send(expectedNameMessage);
    }

    @Test
    void process_positive_nameIsAlreadyTaken() throws InterruptedException, IOException {
        when(player1.getName()).thenReturn("");
        when(player2.getName()).thenReturn("");
        when(player2.receive(NameMessage.class)).thenReturn(new NameMessage("SameName"));
        TimeUnit.NANOSECONDS.sleep(2);
        when(player1.receive(NameMessage.class)).thenReturn(
                new NameMessage("SameName"),
                new NameMessage("OtherName")
        );
        when(player1.hasMessage()).thenReturn(true, true);
        when(player2.hasMessage()).thenReturn(true, false);

        confirmNameSteps.process();

        NameMessage expectedNameMessage = new ConfirmedNameMessage("SameName");
        verify(player2).send(expectedNameMessage);

        expectedNameMessage = new NameMessage("SameName");
        verify(player1).send(expectedNameMessage);
        expectedNameMessage = new ConfirmedNameMessage("OtherName");
        verify(player1).send(expectedNameMessage);
    }
}