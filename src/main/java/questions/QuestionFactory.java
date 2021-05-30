package questions;


import java.io.*;
import java.util.*;
public class QuestionFactory {


    public static void main(String[] args) {
        String fileName1 = "fragenkataloge/fragenkatalog_2019.txt";
        String fileName2 = "fragenkataloge/fragenkatalog_2020.txt";
        String fileName3 = "fragenkataloge/fragenkatalog_2021.txt";
        readFile(fileName1);
        //readFile(fileName2);
        //readFile(fileName3);
    }
    private static /*Map<String, List<String>>*/ void readFile(String fileName) {
        String question = "";
        String answerA = "";
        String answerB = "";
        String answerC = "";
        ArrayList<ArrayList<String>> allQuestions = new ArrayList<>();
        Map<String, List<String>> questions = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line
                if (line.length() > 0) {
                    String valueFirst = String.valueOf(line.charAt(0));
                    String valueSecond = String.valueOf(line.charAt(1));
                    String valueLast = String.valueOf(line.charAt(line.length()-1));
                    //boolean isAnswer = (valueFirst.equals("A") || valueFirst.equals("B") || valueFirst.equals("C"));
                    boolean isCorrect = (valueSecond.equals("*"));
                    boolean isQuestion = (valueLast.equals("?"));
                    boolean isQuestionfull = ((question.length()>1) && (answerA.length()>1) && (answerB.length()>1) && (answerC.length()>1));


                    // All the questions in a array
                    if (valueLast.equals("?")) {
                        question = line;

                    }

                    // All the answers are in a array
                    if (valueFirst.equals("A") && !(isQuestion)) {
                        answerA = line;


                    }
                    if (valueFirst.equals("B") && !(isQuestion)) {
                        answerB = line;

                    }
                    if (valueFirst.equals("C") && !(isQuestion)) {
                        answerC = line;

                    }

                    if (isQuestionfull) {
                        ArrayList<String> oneQuestion = new ArrayList<>(Arrays.asList("Q", "A", "B", "C"));
                        List<String> answer = new ArrayList<>(Arrays.asList("A", "B", "C"));
                        oneQuestion.set(0, question);
                        oneQuestion.set(1, answerA);
                        oneQuestion.set(2, answerB);
                        oneQuestion.set(3, answerC);

                        answer.set(0, answerA);
                        answer.set(1, answerB);
                        answer.set(2, answerC);

                        allQuestions.add(oneQuestion);

                        questions.put(question, answer);



                        question = "";
                        answerA = "";
                        answerB = "";
                        answerC = "";
                    }

                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(allQuestions);
        System.out.println(questions);
        //return questions;
    }


}
