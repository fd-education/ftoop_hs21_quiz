package ch.ffhs.quiz.messages;

import java.util.List;
import java.util.Objects;

public class QuestionMessage extends Message {
    private final List<String> answers;
    private final int correctAnswer;

    public QuestionMessage(String question, List<String> answers, int correctAnswer){
        Objects.requireNonNull(text);
        Objects.requireNonNull(answers);
        if(!(0 <= correctAnswer && correctAnswer < answers.size())) throw new IllegalArgumentException("Correct Answer may not be negative or bigger than 2");
        this.text = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;

        this.clazz = this.getClass();
    }

    public int getCorrectAnswer(){return this.correctAnswer;}

    public List<String> getAnswers(){return this.answers;}
}
