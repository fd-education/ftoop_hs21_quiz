package questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionImp {
    List<Answer> answers;
    String question;
    Integer correctAnswer;
    public QuestionImp(String question){
        this.question = question;
    }
    public Map<Integer, String> getQuestions(Map<String, List<String>> QuestionsCatalog) {
        Map<Integer, String> question = new HashMap<>();
        ArrayList<QuestionImp> questionImps = new ArrayList<QuestionImp>();
        int index = 0;
        for (String key : QuestionsCatalog.keySet()) {
            question.put(index, key);
            questionImps.add(new QuestionImp(key));
            index++;
        }
        System.out.println(question);
        return question;
    }


}
