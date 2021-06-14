package ch.ffhs.quiz.messages;

import java.util.List;
import java.util.Objects;

/**
 * Contains a question and the corresponding answers.
 */
public class QuestionMessage extends Message {
    private final List<String> answers;
    private final String question;

    /**
     * Instantiates a new Question message with the given question and the available answers.
     *
     * @param question the question string
     * @param answers  the answer strings
     */
    public QuestionMessage(String question, List<String> answers) {
        Objects.requireNonNull(question);
        Objects.requireNonNull(answers);
        this.question = question;
        this.answers = answers;

    }

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

    /**
     * Gets a list of the answer strings.
     *
     * @return the list of answers
     */
    public List<String> getAnswers() {
        return answers;
    }

    /**
     * Gets the question string.
     *
     * @return the question string
     */
    public String getQuestion() {
        return question;
    }
}
