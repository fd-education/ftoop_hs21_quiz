package ch.ffhs.quiz.messages;

import java.util.List;
import java.util.Objects;

public class QuestionMessage extends Message {
    private final List<String> answers;
    private final String question;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionMessage that = (QuestionMessage) o;

        if (!answers.equals(that.answers)) return false;
        return question.equals(that.question);
    }

    @Override
    public int hashCode() {
        int result = answers.hashCode();
        result = 31 * result + question.hashCode();
        return result;
    }

    public QuestionMessage(String question, List<String> answers){
        Objects.requireNonNull(question);
        Objects.requireNonNull(answers);
        this.question = question;
        this.answers = answers;

    }

    public List<String> getAnswers(){return answers;}

    public String getQuestion() {
        return question;
    }
}
