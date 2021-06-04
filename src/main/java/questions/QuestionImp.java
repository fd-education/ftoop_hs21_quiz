package questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionImp implements Question {
    List<AnswerImp> answers;
    String question;
    Integer correctAnswer;
    public ArrayList<QuestionImp> getQuestions(Map<String, List<String>> QuestionsCatalog) {
        ArrayList<QuestionImp> questionImps = new ArrayList<>();
        AnswerImp answerImp = new AnswerImp();
        QuestionImp questionImp = new QuestionImp();
        Map<String, List<AnswerImp>> mappedAnswers = answerImp.getAnswers(QuestionsCatalog);
        for(Map.Entry<String, List<AnswerImp>> entry : mappedAnswers.entrySet()) {
            String question = entry.getKey();
            List<AnswerImp> answerImpList = entry.getValue();
            int i = 1;
            while (i < (answerImpList.size()) ){
                if (answerImpList.get(i).isCorrectAnswer) {
                    questionImp.correctAnswer = i;
                }
                i++;
            }
            questionImp.question = question;
            questionImp.answers = answerImpList;
            questionImps.add(questionImp);
        }
        return questionImps;
    }

    @Override
    public boolean checkAnswer(Integer questionNumber) {
        return questionNumber.equals(this.correctAnswer);
    }

    @Override
    public List<AnswerImp> getAnswers() {
        return this.answers;
    }
}
