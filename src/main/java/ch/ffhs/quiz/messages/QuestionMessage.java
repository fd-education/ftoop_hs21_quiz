package ch.ffhs.quiz.messages;

import java.util.List;
import java.util.Objects;

public class QuestionMessage extends Message {
    private final List<String> answers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        QuestionMessage that = (QuestionMessage) o;

        return answers.equals(that.answers);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + answers.hashCode();
        return result;
    }

    public QuestionMessage(String question, List<String> answers){
        Objects.requireNonNull(text);
        Objects.requireNonNull(answers);
        this.text = question;
        this.answers = answers;

    }

    public List<String> getAnswers(){return answers;}
}
