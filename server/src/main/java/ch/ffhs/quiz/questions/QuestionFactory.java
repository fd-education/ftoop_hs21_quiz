package ch.ffhs.quiz.questions;


import java.io.*;
import java.util.*;
public class QuestionFactory {

    public static void main(String[] args) {
        List<Question> quiz2019 = questionBuilder("fragenkataloge/fragenkatalog_2019.txt");
        List<Question> quiz2020 = questionBuilder("fragenkataloge/fragenkatalog_2020.txt");
        List<Question> quiz2021 = questionBuilder("fragenkataloge/fragenkatalog_2021.txt");
        System.out.println(quiz2019);
        System.out.println(quiz2020);
        System.out.println(quiz2021);
    }

    private static Map<String, List<String>> loadFromFile(String fileName) {
        String question = "";
        String answerA = "";
        String answerB = "";
        String answerC = "";
        Map<String, List<String>> questions = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line
                if (line.length() > 0) {
                    String valueFirst = String.valueOf(line.charAt(0));
                    String valueLast = String.valueOf(line.charAt(line.length()-1));
                    boolean isQuestion = (valueLast.equals("?"));
                    boolean isQuestionComplete = ((question.length()>1) && (answerA.length()>1) && (answerB.length()>1) && (answerC.length()>1));

                    // evaluate the question
                    if (valueLast.equals("?")) {
                        question = line;
                    }

                    // evaluate the answer
                    if (valueFirst.equals("A") && !(isQuestion)) {
                        answerA = line;
                    }
                    if (valueFirst.equals("B") && !(isQuestion)) {
                        answerB = line;
                    }
                    if (valueFirst.equals("C") && !(isQuestion)) {
                        answerC = line;
                    }

                    if (isQuestionComplete) {
                        List<String> answers = new ArrayList<>(Arrays.asList("A", "B", "C"));
                        answers.set(0, answerA);
                        answers.set(1, answerB);
                        answers.set(2, answerC);

                        questions.put(question, answers);

                        question = "";
                        answerA = "";
                        answerB = "";
                        answerC = "";
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public static List<Question> questionBuilder(String filename) {
        Map<String, List<String>> quiz = loadFromFile(filename);

        List<Question> quizList = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : quiz.entrySet()) {
            String question = entry.getKey();
            List<Answer> answerList = new ArrayList<>();
            for (int i = 0; i < quiz.get(entry.getKey()).size(); i++) {
                String valueSecond = String.valueOf(quiz.get(entry.getKey()).get(i).charAt(1));
                if (valueSecond.equals("*")) {
                    String correctAnswer = quiz.get(entry.getKey()).get(i);
                    correctAnswer = correctAnswer.replace("*", "");
                    answerList.add(new AnswerImpl(correctAnswer, true));
                } else {
                    String incorrectAnswer = quiz.get(entry.getKey()).get(i);
                    answerList.add(new AnswerImpl(incorrectAnswer, false));
                }
            }
            Question questionImpl = new QuestionImpl(question, answerList);
            quizList.add(questionImpl);
        }
        return quizList;
    }


}
