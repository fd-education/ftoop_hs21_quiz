package questions;

import java.util.*;

public class AnswerImp implements Answer {
    String answer;
    Boolean isCorrectAnswer;

    public Map<String, List<AnswerImp>> getAnswers(Map<String, List<String>> AnswerCatalog) {
        Map<String , List<AnswerImp>> mappedAnswers = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : AnswerCatalog.entrySet()) {
            List<AnswerImp> answerList = new ArrayList<>();
            for (int i = 0; i < AnswerCatalog.get(entry.getKey()).size(); i++) {
                String valueSecond = String.valueOf(AnswerCatalog.get(entry.getKey()).get(i).charAt(1));
                if (valueSecond.equals("*")) {
                    String correctAnswer = AnswerCatalog.get(entry.getKey()).get(i);
                    correctAnswer = correctAnswer.replace("*", "");
                    AnswerImp answerImp = new AnswerImp();
                    answerImp.answer = correctAnswer;
                    answerImp.isCorrectAnswer = true;
                    answerList.add(answerImp);
                } else {
                    String correctAnswer = AnswerCatalog.get(entry.getKey()).get(i);
                    AnswerImp answerImp = new AnswerImp();
                    answerImp.answer = correctAnswer;
                    answerImp.isCorrectAnswer = false;
                    answerList.add(answerImp);
                }
            }
            mappedAnswers.put(entry.getKey(), answerList);
        }
        return mappedAnswers;
    }

    @Override
    public boolean isCorrect() {
        return this.isCorrectAnswer;
    }

    @Override
    public String getAnswer() {
        return this.answer;
    }
}
