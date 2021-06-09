package ch.ffhs.quiz.questions;

import java.util.List;

public class QuestionImpl implements Question{
    private final String question;
    private final List<Answer> answers;

    public QuestionImpl(String question, List<Answer> answers){
        this.question = question;
        this.answers = answers;
    }

    @Override
    public boolean checkAnswer(int questionNumber) {
        return answers.get(questionNumber).isCorrect();
    }

    @Override
    public String getQuestionText() {
        return question;
    }

    @Override
    public List<Answer> getAnswers() {
        return answers;
    }
}
