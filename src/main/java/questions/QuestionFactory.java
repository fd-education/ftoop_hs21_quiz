package questions;


import java.io.*;
import java.util.ArrayList;

public class QuestionFactory {


    public static void main(String[] args) {
        String fileName = "/Users/TAASCSE7/Repositories/FFHS/FTOOP/fragenkataloge/fragenkatalog_2019.txt";
       readFile(fileName);
    }
    private static void readFile(String fileName) {
        ArrayList<String> Question = new ArrayList<String>();
        ArrayList<String> Answer = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                // process the line
                if (line.length() > 0) {
                    String valueFirst = String.valueOf(line.charAt(0));
                    String valueSecond = String.valueOf(line.charAt(1));
                    String valueLast = String.valueOf(line.charAt(line.length()-1));
                    boolean isAnswer = (valueFirst.equals("A") || valueFirst.equals("B") || valueFirst.equals("C"));
                    boolean isCorrect = (valueSecond.equals("*"));
                    boolean isQuestion = (valueLast.equals("?"));


                    if ((isAnswer) && !(isQuestion)) {
                        System.out.println(line);
                        Answer.add(line);
                    } else if (valueLast.equals("?")){
                        System.out.println(line);
                        Question.add(line);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Question);
        System.out.println(Answer);
    }


}
