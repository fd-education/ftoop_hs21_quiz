package ch.ffhs.quiz.server.gamesteps;

import ch.ffhs.quiz.messages.MessageUtils;
import ch.ffhs.quiz.messages.QuestionMessage;
import ch.ffhs.quiz.questions.Answer;
import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.server.GameContext;
import ch.ffhs.quiz.server.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SendQuestionStep extends GameStep {
    String messageJson;

    public SendQuestionStep(GameContext gameContext) {
        super(gameContext);
    }

    @Override
    protected void prepareStep() {
        final Question question = roundContext.getCurrentQuestion();
        final String questionText = question.getQuestionText();
        final List<Answer> answers = question.getAnswers();
        final List<String> answerTexts = new ArrayList<>();
        int correctAnswer = 0;
        for (int i = 0; i < answers.size(); i++) {
            Answer answer = answers.get(i);
            answerTexts.add(answer.toString());
            if (answer.isCorrect())
                correctAnswer = i;
        }
        final QuestionMessage questionMessage = new QuestionMessage(questionText, answerTexts, correctAnswer);
        messageJson = MessageUtils.serialize(questionMessage);
    }

    @Override
    protected void handlePlayer(Player player) {
        player.send(messageJson);
    }
}
