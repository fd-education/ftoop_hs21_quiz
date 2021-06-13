package ch.ffhs.quiz.client;

import ch.ffhs.quiz.client.ui.UserInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to get and validate user input.
 */
public class InputHandler {
    private final static ArrayList<String> VALID_ANSWERS = new ArrayList<>(List.of("A", "B", "C"));
    private final UserInterface ui;

    public InputHandler(){
        ui = new UserInterface();
    }

    /**
     * Ask for the user's answer and validate the input.
     * Repeat until answer is valid (valid = A, B or C)
     * @return user's answer
     */
    public String getUserAnswer(){
        while(true){
            String answer = getInputLine();

            if(validateAnswer(answer)) return answer;

            ui.alertInvalidAnswer(answer);
        }
    }

    /**
     * Ask for the user's name.
     * Anything longer than 2 characters is allowed.
     * @return user's name
     */
    public String getUserName(){
        while(true){
            String name = getInputLine();

            if(validateName(name))  return name;

            ui.alertInvalidName(name);
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
