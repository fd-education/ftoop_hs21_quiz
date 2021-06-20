package ch.ffhs.quiz.game;

import ch.ffhs.quiz.game.player.PlayerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.github.stefanbirkner.systemlambda.SystemLambda.restoreSystemProperties;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@Timeout(1)
@ExtendWith(MockitoExtension.class)
class QuestionGameServerTest {

    // PlayerFactory has to be mocked, otherwise, it will wait for players to connect
    @Test
    void play_positive_defaultArgs() {
        try (MockedStatic<PlayerFactory> ignored = Mockito.mockStatic(PlayerFactory.class)) {
            try (MockedConstruction<Game> game = mockConstruction(Game.class)) {
                QuestionGameServer.play(new String[]{"testQuestions/test_questions_1.txt"});
                verify(game.constructed().get(0)).play(anyList(), argThat(questions -> questions.size() == 5));
            }
            ignored.verify(
                    () -> PlayerFactory.connectPlayers(argThat(serverSocket -> serverSocket.getLocalPort() == 3141),
                            eq(2)));
        }
    }

    @Test
    void play_positive_validArgs() throws Exception {
        restoreSystemProperties(() -> {
            System.setProperty("playerCount", "5");
            System.setProperty("questionCount", "2");
            System.setProperty("port", "100");
            try (MockedStatic<PlayerFactory> ignored = Mockito.mockStatic(PlayerFactory.class)) {
                try (MockedConstruction<Game> game = mockConstruction(Game.class)) {
                    QuestionGameServer.play(new String[]{"testQuestions/test_questions_1.txt"});
                    verify(game.constructed().get(0)).play(any(), argThat(questions -> questions.size() == 2));
                }
                ignored.verify(
                        () -> PlayerFactory.connectPlayers(argThat(serverSocket -> serverSocket.getLocalPort() == 100),
                                eq(5)));
            }
        });
    }

    @Test
    void play_negative_invalidArgs_negativePort() throws Exception {
        String error = tapSystemErr(() ->
                restoreSystemProperties(() -> {
                            System.setProperty("port", "-1");
                            QuestionGameServer.play(new String[]{"testQuestions/test_questions_1.txt"});
                        }
                ));
        assertTrue(error.contains("Port must be between 1 and 65535, inclusive"));
    }

    @Test
    void play_negative_invalidArgs_tooHighPort() throws Exception {
        String error = tapSystemErr(() ->
                restoreSystemProperties(() -> {
                            System.setProperty("port", "100000");
                            QuestionGameServer.play(new String[]{"testQuestions/test_questions_1.txt"});
                        }
                ));
        assertTrue(error.contains("Port must be between 1 and 65535, inclusive."));
    }

    @Test
    void play_negative_invalidArgs_questionCountBelowOne() throws Exception {
        String error = tapSystemErr(() ->
                restoreSystemProperties(() -> {
                            System.setProperty("questionCount", "0");
                            QuestionGameServer.play(new String[]{"testQuestions/test_questions_1.txt"});
                        }
                ));
        assertTrue(error.contains("Question count must be between 1 and 999, inclusive."));
    }

    @Test
    void play_negative_invalidArgs_questionCountThousandOrHigher() throws Exception {
        String error = tapSystemErr(() ->
                restoreSystemProperties(() -> {
                            System.setProperty("questionCount", "1000");
                            QuestionGameServer.play(new String[]{"testQuestions/test_questions_1.txt"});
                        }
                ));
        assertTrue(error.contains("Question count must be between 1 and 999, inclusive."));
    }

    @Test
    void play_negative_invalidArgs_playerCountBelowOne() throws Exception {
        String error = tapSystemErr(() ->
                restoreSystemProperties(() -> {
                            System.setProperty("playerCount", "0");
                            QuestionGameServer.play(new String[]{"testQuestions/test_questions_1.txt"});
                        }
                ));
        assertTrue(error.contains("Player count must be between 1 and 999, inclusive."));
    }

    @Test
    void play_negative_invalidArgs_playerCountThousandOrHigher() throws Exception {
        String error = tapSystemErr(() ->
                restoreSystemProperties(() -> {
                            System.setProperty("playerCount", "1000");
                            QuestionGameServer.play(new String[]{"testQuestions/test_questions_1.txt"});
                        }
                ));
        assertTrue(error.contains("Player count must be between 1 and 999, inclusive."));
    }

    @Test
    void play_negative_invalidArgs_playerCountInvalidNumber() throws Exception {
        String error = tapSystemErr(() ->
                restoreSystemProperties(() -> {
                            System.setProperty("playerCount", "noInt");
                            QuestionGameServer.play(new String[]{"testQuestions/test_questions_1.txt"});
                        }
                ));
        assertTrue(error.contains("Player count is not a valid number."));
    }

    @Test
    void play_negative_invalidArgs_questionCountInvalidNumber() throws Exception {
        String error = tapSystemErr(() ->
                restoreSystemProperties(() -> {
                            System.setProperty("questionCount", "noInt");
                            QuestionGameServer.play(new String[]{"testQuestions/test_questions_1.txt"});
                        }
                ));
        assertTrue(error.contains("Question count is not a valid number."));
    }

    @Test
    void play_negative_invalidArgs_portInvalidNumber() throws Exception {
        String error = tapSystemErr(() ->
                restoreSystemProperties(() -> {
                            System.setProperty("port", "noInt");
                            QuestionGameServer.play(new String[]{"testQuestions/test_questions_1.txt"});
                        }
                ));
        assertTrue(error.contains("Port is not a valid number."));
    }

    @Test
    void play_negative_invalidArgs_notEnoughQuestions() throws Exception {
        String error = tapSystemErr(() ->
                QuestionGameServer.play(new String[]{"testQuestions/test_questions_2.txt"})
        );
        assertTrue(error.contains("5 questions wanted, but only 3 found in testQuestions/test_questions_2.txt."));
    }

    @Test
    void play_negative_invalidArgs_noFilepath() throws Exception {
        String error = tapSystemErr(() ->
                QuestionGameServer.play(new String[0])
        );
        assertTrue(error.contains("No filepath for the questions was specified."));
    }
}