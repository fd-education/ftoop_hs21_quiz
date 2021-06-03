package questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerImp {
    String answer;
    Boolean correctAnswer;
//    public AnswerImp(String answer, Boolean correctAnswer) {
//        this.answer = answer;
//        this.correctAnswer = correctAnswer;
//    }
    public Map<Integer, List<String>> getAnswers(Map<String, List<String>> AnswerCatalog) {
        Map<Integer, List<String>> mappedAnswers = new HashMap<>();
        ArrayList<List<String>> answers = new ArrayList<>();

//        for(int i = 0; i < AnswerCatalog.size(); i++) {
//            List<String> answersList = new ArrayList<>();
//            for (int j = 0; j < AnswerCatalog.get(i).size(); j++) {
//                String valueSecond = String.valueOf(AnswerCatalog.get(i).get(j).charAt(1));
//                if (valueSecond.equals("*")) {
//                    String correctAnswer = AnswerCatalog.get(i).get(j);
//                    correctAnswer = correctAnswer.replace("*", "");
//                    answersList.add(correctAnswer);
//                } else {
//                    answersList.add(AnswerCatalog.get(i).get(j));
//                }
//            }
//            mappedAnswers.put(i, answersList);
//        }

//        for(Map.Entry<String, List<String>> entry : AnswerCatalog.entrySet()) {
//            List<String> list = entry.getValue();
//            List<String> answersList = new ArrayList<>();
//            int index = 0;
//            for (int i = 0; i < list.size(); i++) {
//
//                String valueSecond = String.valueOf(list.get(i).charAt(1));
//                if (valueSecond.equals("*")) {
//                    String correctAnswer = list.get(i);
//                    correctAnswer = correctAnswer.replace("*", "");
//                    answersList.add(correctAnswer);
//                } else {
//                    answersList.add(list.get(i));
//
//                }
//            }
//
//            mappedAnswers.put(index, answersList);
//            index++;
//        }
        int index = 0;
        for (List<String> values : AnswerCatalog.values()) {
            mappedAnswers.put(index, values);
            answers.add(values);
            index++;
        }
        mapCorrectAnswers(answers);
        System.out.println(mappedAnswers);
        return mappedAnswers;
    }

    public String getAnswer(){
        return this.answer;
    }

    public boolean checkAnswer(){
        return this.correctAnswer;
    }

    public Map<Integer, String> mapCorrectAnswers(ArrayList<List<String>> answers) {
        Map<Integer, String> mappedCorrectAnswers = new HashMap<>();
        for (int i = 0; i < answers.size(); i++) {
            for (int j = 0; j < answers.get(i).size(); j++) {
                String valueSecond = String.valueOf(answers.get(i).get(j).charAt(1));
                if (valueSecond.equals("*")) {
                    String correctAnswer = answers.get(i).get(j);
                    correctAnswer = correctAnswer.replace("*", "");
                    mappedCorrectAnswers.put(i, correctAnswer);
                }
            }
        }
        //System.out.println(mappedCorrectAnswers);
        return mappedCorrectAnswers;
    }
}
