package questions;

import java.util.List;

public interface Question {
    boolean checkAnswer(int questionNumber);
    List<Answer> getAnswers();
}
