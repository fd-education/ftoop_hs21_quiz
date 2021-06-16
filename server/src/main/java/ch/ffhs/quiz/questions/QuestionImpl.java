package ch.ffhs.quiz.questions;

import java.util.List;

public class QuestionImpl implements Question {
    private final List<Answer> answers;
    private final String question;
    private final int correctAnswerNumber;

    /**
     * Instantiates a new Question.
     *
     * @param answers  the answers of the question
     * @param question the question
     */
    public QuestionImpl(String question, List<Answer> answers) {
        this.answers = answers;
        this.question = question;
        int correctAnswerNumber = -1;
        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).isCorrect()) {
                correctAnswerNumber = i;
                break;
            }
        }
        this.correctAnswerNumber = correctAnswerNumber;
    }

    @Override
    public int getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }

    @Override
    public boolean checkAnswer(int questionNumber) {
        return questionNumber == correctAnswerNumber;
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
