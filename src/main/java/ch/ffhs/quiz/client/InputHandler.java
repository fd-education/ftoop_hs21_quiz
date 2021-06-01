package ch.ffhs.quiz.client;

import java.io.*;
import java.util.*;

/**
 * Class to get and validate user input.
 */
public class InputHandler {
    private final static ArrayList<String> VALID_ANSWERS = new ArrayList<>(List.of("A", "B", "C"));

    public InputHandler(){ }

    /**
     * Ask for the user's answer and validate the input.
     * Repeat until answer is valid (valid = A, B or C)
     * @return user's answer
     */
    public String getUsersAnswer(){
        while(true){
            System.out.println("Enter your answer: ");

            String answer = getInputLine();

            if(validateAnswer(answer)) return answer;

            System.out.printf("%s is not a valid answer.\n\n", answer);
        }
    }

    /**
     * Ask for the user's name.
     * Anything longer than 2 characters is allowed.
     * @return user's name
     */
    public String getUsersName(){
        while(true){
            System.out.println("Enter your name: ");

            String name = getInputLine();

            if(validateName(name))  return name;

            System.out.println("Your name must contain at least three characters.");
        }
    }

    // Get and return a user input.
    private String getInputLine(){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    // Validate user's name (must be longer than 2 chars)
    private boolean validateName(final String name){
        return !name.isBlank() && name.length() > 2;
    }

    // Validate user's answer (must be A/a, B/b or C/c)
    private boolean validateAnswer(final String answer){
        return VALID_ANSWERS.contains(answer.toUpperCase());
    }
}
