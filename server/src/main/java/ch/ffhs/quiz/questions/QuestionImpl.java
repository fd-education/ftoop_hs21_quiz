package ch.ffhs.quiz.questions;

import java.util.List;

public class QuestionImpl implements Question {
    List<Answer> answers;
    String question;
    Integer correctAnswer;

    /**
     * Instantiates a new Question.
     *
     * @param answers       the answers of the question
     * @param question      the question
     */
    public QuestionImpl(String question, List<Answer> answers) {
        this.answers = answers;
        this.question = question;
    }

    @Override
    public boolean checkAnswer(int questionNumber) {
        if(questionNumber < 0 || questionNumber >= answers.size()) return false;
        return answers.get(questionNumber).isCorrect();
    }

    @Override
    public List<Answer> getAnswers() {
        return this.answers;
    }

    @Override
    public String getQuestionText() {
        return question;
    }
}
