package ch.ffhs.quiz.game.gamesteps;

import ch.ffhs.quiz.messages.QuestionMessage;
import ch.ffhs.quiz.questions.Answer;
import ch.ffhs.quiz.questions.Question;
import ch.ffhs.quiz.game.GameContext;
import ch.ffhs.quiz.game.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SendQuestionStep extends GameStep {
    QuestionMessage questionMessage;

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
        questionMessage = new QuestionMessage(questionText, answerTexts, correctAnswer);
    }

    @Override
    protected void handlePlayer(Player player) {
        player.send(questionMessage);
    }
}
