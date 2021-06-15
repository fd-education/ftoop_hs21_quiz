package ch.ffhs.quiz.game.gamesteps.impl;

import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.RoundContext;
import ch.ffhs.quiz.game.gamesteps.GameStep;
import ch.ffhs.quiz.game.player.Player;
import ch.ffhs.quiz.messages.QuestionMessage;
import ch.ffhs.quiz.questions.Answer;
import ch.ffhs.quiz.questions.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Sends the current question to every player.
 */
public class SendQuestionStep extends GameStep {
    private final RoundContext roundContext;
    QuestionMessage questionMessage;

    public SendQuestionStep(GameContext gameContext) {
        super(gameContext);
        this.roundContext = gameContext.getRoundContext();
    }

    // Creates the question message and stores it for later use
    @Override
    protected void prepareStep() {
        questionMessage = buildQuestionMessage(roundContext.getQuestion());
    }

    // Build a question message from a given question.
    private QuestionMessage buildQuestionMessage(Question question) {
        final String questionText = question.getQuestionText();
        final List<Answer> answers = question.getAnswers();
        final List<String> answerTexts = new ArrayList<>();
        for (Answer answer : answers) {
            answerTexts.add(answer.toString());
        }
        return new QuestionMessage(questionText, answerTexts);
    }

    // Sends the built question message to every player
    @Override
    protected void handlePlayer(Player player) {
        player.send(questionMessage);
    }
}
