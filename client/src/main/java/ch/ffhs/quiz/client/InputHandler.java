package ch.ffhs.quiz.client;

import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.client.ui.components.TimeAlertRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Class to get and validate user input.
 */
public class InputHandler {
    private final static ArrayList<String> VALID_ANSWERS = new ArrayList<>(List.of("A", "B", "C"));
    private final UserInterface ui;

    /**
     * Instantiates a new Input handler.
     * Injects user interface object to alert invalid inputs
     */
    public InputHandler(){
        ui = new UserInterface();
    }

    /**
     * Await user answer and map it to an int for the serverside to work with.
     *
     * @return the index of the players answer
     */
    public int awaitUserAnswer(){
        int answerIndex = -1;

        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Integer> index = es.submit(() -> mapStringAnswerToInteger(getUserAnswer()));

        // alert the running time with a graphical ui output
        ScheduledExecutorService esSchedule = Executors.newSingleThreadScheduledExecutor();
        esSchedule.schedule(new TimeAlertRunnable(30),30L, TimeUnit.SECONDS);
        esSchedule.schedule(new TimeAlertRunnable(15),45L, TimeUnit.SECONDS);
        esSchedule.schedule(new TimeAlertRunnable(10),50L, TimeUnit.SECONDS);
        esSchedule.schedule(new TimeAlertRunnable(5),55L, TimeUnit.SECONDS);


        try {
            answerIndex = index.get(1, TimeUnit.MINUTES);
        } catch(TimeoutException | InterruptedException | ExecutionException ignored){}

        esSchedule.shutdownNow();
        es.shutdown();
        try{
            if(!es.awaitTermination(800, TimeUnit.MILLISECONDS)){
                es.shutdownNow();
            }
        } catch(InterruptedException iEx){
            es.shutdownNow();
        }

        return answerIndex;
    }

    /**
     * Ask for the user's answer and validate the input.
     * Repeat until answer is valid (valid = A, B or C)
     * @return user's answer
     */
    private String getUserAnswer(){
        while(true){
            String answer = getInputLine();

            if(validateAnswer(answer)) return answer;

            ui.alertInvalidAnswer(answer);
        }
    }

    /**
     * Ask for the user's name.
     * Anything longer than 2 characters is allowed.
     *
     * @return user 's name
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

    // Map an entered letter to the corresponding index
    private int mapStringAnswerToInteger(final String answer){
        if(answer.isBlank()) throw new IllegalArgumentException("answer must contain a letter");

        return switch (answer.toUpperCase()) {
            case "A" -> 0;
            case "B" -> 1;
            case "C" -> 2;

            // may never be reached, bc inputs may only be a, A, b, B, c, C
            default -> -1;
        };
    }

    // Validate user's name (must be longer than 2 chars)
    private boolean validateName(final String name){
        return !name.isBlank() && name.length() > 2;
    }

    // Validate user's answer (must be A/a, B/b or C/c)
    private boolean validateAnswer(final String answer){
        if(answer == null) return false;
        return VALID_ANSWERS.contains(answer.toUpperCase());
    }
}
