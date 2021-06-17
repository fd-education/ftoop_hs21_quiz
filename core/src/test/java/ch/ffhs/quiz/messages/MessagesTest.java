package ch.ffhs.quiz.messages;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessagesTest {

    @Test
    void equals_positive_simple() {
        assertEquals(new AnswerMessage(0, Duration.ZERO), new AnswerMessage(0, Duration.ZERO));
        assertEquals(new MessageMock("test"), new MessageMock("test"));
        assertEquals(new NameMessage("test"), new NameMessage("test"));
        assertEquals(new QuestionMessage("question", List.of("answer")), new QuestionMessage("question", List.of("answer")));
        assertEquals(new ScoreboardEntry("name", 0), new ScoreboardEntry("name", 0));
        assertEquals(new RoundSummaryMessage(List.of(new ScoreboardEntry("name", 0)), false), new RoundSummaryMessage(List.of(new ScoreboardEntry("name", 0)), false));
        assertEquals(new FeedbackMessage(false, false, "name", 0), new FeedbackMessage(false, false, "name", 0));
    }
}