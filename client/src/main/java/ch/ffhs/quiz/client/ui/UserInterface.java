package ch.ffhs.quiz.client.ui;

import ch.ffhs.quiz.client.ui.components.Scoreboard;
import ch.ffhs.quiz.client.ui.components.ascii.AsciiArtDecorations;
import ch.ffhs.quiz.client.ui.components.ascii.AsciiArtNumbers;
import ch.ffhs.quiz.client.ui.components.ascii.AsciiArtTitles;
import ch.ffhs.quiz.client.ui.components.interfaces.StaticUIComponent;
import ch.ffhs.quiz.client.ui.components.text.DynamicTextComponent;
import ch.ffhs.quiz.client.ui.components.text.StaticTextComponent;
import ch.ffhs.quiz.messages.ScoreboardEntry;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static ch.ffhs.quiz.client.ui.AnsiBuilder.Color.*;

/**
 * Class to create all the different user interface surfaces.
 */
public class UserInterface {
        private boolean proceed = true;
    private Thread waitingThread;

    private final static int MAX_TEXT_LENGTH = 95;
    private final static int FRAME_HEIGHT = 26;
    private final static int PADDING_BORDER_TITLE = 2;

    /**
     * Create the welcome screen with an explanation printed letter by letter
     * on the terminal.
     */
    public void welcomeAndExplain(){
        StaticUIComponent welcome = AsciiArtTitles.FACADE_QUIZ;
        StaticUIComponent explanation = StaticTextComponent.EXPLANATION;

        frameContent(welcome);

        String formattedExplanation = UserInterfaceUtils.createWithDefaultStyle(explanation.getComponent());

        UserInterfaceUtils.printLetterByLetter(formattedExplanation, UserInterfaceUtils.Delay.FAST);
    }

    /**
     * Print a request for the player to enter his name.
     */
    public void askForName(){
        requestUserInput(StaticTextComponent.ASK_FOR_NAME);
    }

    /**
     * Print a red alert on the console, if the name input was invalid.
     *
     * @param name the name that is invalid
     */
    // no testing for param "name" as the method is supposed to return especially INVALID names to the user for correction
    public void alertInvalidName(final String name){
        alertInvalidInput(DynamicTextComponent.NAME_INVALID, name);
    }


    /**
     * Print a red alert on the console, if the name input is reserved (= used by another player).
     *
     * @param name the name that is reserved
     */
    public void alertNameReserved(final String name){
        if(name.isBlank()) throw new IllegalArgumentException("name must not be empty or consist of only whitespace");

        alertInvalidInput(DynamicTextComponent.NAME_RESERVED, name);
    }

    /**
     * Welcome the player with his name
     *
     * @param name the players name
     */
    public void welcomePlayerPersonally(String name){
        if(name.isBlank()) throw new IllegalArgumentException("name must not be empty or consist of only whitespace");

        AnsiTerminal.positionCursor(22, 0);
        AnsiTerminal.clearNumberOfLines(4);
        UserInterfaceUtils.printWithDefaultStyle(DynamicTextComponent.PERSONALIZED_WELCOME.getComponent(name));
    }

    /**
     * Print a 5-second countdown from 5 to 0 to get the players attention before the game starts
     */
    public void countdown(){
        emptyTerminal();

        try {
            for (int i = 5; i >= 0; i--) {
                frameContent(null);
                AnsiTerminal.moveCursorDown(9);
                UserInterfaceUtils.printWithDefaultStyle(AsciiArtNumbers.getText(i));
                Thread.sleep(1000);
            }
        } catch(InterruptedException ignored){}
    }

    /**
     * Print a question and its answers to the screen.
     *
     * @param question the question String
     * @param answers  the answers List
     */
    public void printQuestion(final String question, final List<String> answers){
        printQuestion(question, answers, -1);
    }

    /**
     * Print a request for the player to enter his answer
     */
    public void askForAnswer(){
        requestUserInput(StaticTextComponent.ASK_FOR_ANSWER);
    }

    /**
     * Print a red alert on the console, if the answer input is invalid (something other than a, A, b, B, c, C).
     *
     * @param answer the answer that was invalid
     */
    public void alertInvalidAnswer(final String answer){
        alertInvalidInput(DynamicTextComponent.ANSWER_INVALID, answer);
    }

    /**
     * Prints a new question screen with the chosen answer being marked with a different background
     *
     * @param question     the question
     * @param answers      the answers
     * @param chosenAnswer the chosen answer
     */
    public void markChosenAnswer(final String question, List<String> answers, int chosenAnswer){
        if(!List.of(0, 1, 2).contains(chosenAnswer)) throw new IllegalArgumentException("chosenAnswer must be 0, 1 or 2");

        printQuestion(question, answers, chosenAnswer);
    }

    /**
     * Prints alerts at 30, 15, 10 and 5 seconds to inform the player about the running clock
     */
    public void alertTimeMinute(){
        ScheduledExecutorService esSchedule = Executors.newSingleThreadScheduledExecutor();

        esSchedule.schedule(() -> alertTime("30"), 30, TimeUnit.SECONDS);
        esSchedule.schedule(() -> alertTime("15"), 45, TimeUnit.SECONDS);
        esSchedule.schedule(() -> alertTime("10"), 50, TimeUnit.SECONDS);
        esSchedule.schedule(() -> alertTime("5"), 55, TimeUnit.SECONDS);

        esSchedule.shutdown();
        try{
            if(!esSchedule.awaitTermination(800, TimeUnit.MILLISECONDS)){
                esSchedule.shutdownNow();
            }
        } catch(InterruptedException iEx){
            esSchedule.shutdownNow();
        }
    }

    /**
     * Wait for a defined amount of milliseconds.
     *
     * @param millis the milliseconds to wait for
     */
    public void sleepSave(final int millis){
        try{
            Thread.sleep(millis);
        } catch(InterruptedException ignored){}
    }

    /**
     * Wait with a specified reason and a graphical output, with three blinking dots.
     * The waiting can be stopped from outside by calling the proceed() method.
     * The graphical output will be placed at a fixed position, cursor will return
     * to its initial position, after the waiting has ended.
     *
     * @param reason the reason that will be used for the graphical output
     */
    public void waiting(String reason){
        if(waitingThread != null && waitingThread.isAlive()) return;
        if(proceed) proceed = false;

        waitingThread = new Thread(() -> {
            AnsiTerminal.saveCursorPos();
            AnsiTerminal.positionCursor(25, 32);
            UserInterfaceUtils.printWithDefaultStyle(reason);

            while (!proceed) {
                UserInterfaceUtils.printLetterByLetter(UserInterfaceUtils.createWithDefaultStyle(" . . ."), UserInterfaceUtils.Delay.SLOW);
                AnsiTerminal.moveCursorLeft(6);
                AnsiTerminal.clearRemainingOfLine();
            }

            AnsiTerminal.clearLine();
            AnsiTerminal.restoreCursorPos();
        });

        waitingThread.start();
    }

    /**
     * Print a green string to tell the player, that he won this round
     */
    public void printPlayerHasWon(){
        AnsiTerminal.moveCursorDown(2);
        new AnsiBuilder(StaticTextComponent.PLAYER_WON.getComponent()).setFont(GREEN, true).print();
    }

    /**
     * Print a yellow string to inform the player, that his answer was right, but too late.
     *
     * @param winningPlayer the winning player
     */
    public void printPlayerOnlyWasCorrect(final String winningPlayer){
        AnsiTerminal.moveCursorDown(2);
        new AnsiBuilder(DynamicTextComponent.CORRECT_ANSWER_TOO_SLOW.getComponent(winningPlayer)).setFont(YELLOW, false).print();
    }

    /**
     * Print a red string to inform the player, that his answer was wrong and who won the round.
     *
     * @param winningPlayer the winning player
     */
    public void printPlayerWasWrong(final String winningPlayer){
        AnsiTerminal.moveCursorDown(2);
        new AnsiBuilder(DynamicTextComponent.WRONG_ANSWER.getComponent(winningPlayer)).setFont(RED, false).print();
    }

    /**
     * Print a red string to inform the player, that no one knew the correct answer
     */
    public void printNooneCorrect(){
        AnsiTerminal.moveCursorDown(2);
        new AnsiBuilder(StaticTextComponent.NO_PLAYER_CORRECT.getComponent()).setFont(RED, false).print();
    }

    /**
     * Create and print the scoreboard screen.
     *
     * @param scoreboardEntries the scoreboard entries list
     * @param name              the name of the player whose scoreboard to print
     */
    public void printScoreboard(List<ScoreboardEntry> scoreboardEntries, String name){
        frameContent(AsciiArtTitles.SCORE);

        AnsiTerminal.moveCursorDown(1);
        Scoreboard sb = new Scoreboard(scoreboardEntries);
        UserInterfaceUtils.printWithDefaultStyle(sb.getScoreboardForPlayer(name));
    }

    /**
     * Print an ending screen. Thank the players for their participation.
     */
    public void printEnd(){
        frameContent(null);
        AnsiTerminal.moveCursorDown(6);
        UserInterfaceUtils.printWithDefaultStyle(AsciiArtTitles.END.getComponent());

        AnsiTerminal.moveCursorDown(2);
        UserInterfaceUtils.printWithDefaultStyle(StaticTextComponent.THANKS.getComponent());

        sleepSave(10000);
        emptyTerminal();
    }

    /**
     * Proceed user interface. Can be used to chain other methods.
     * Ends the waiting of the user interface.
     *
     * @return the user interface (to allow chaining methods to proceed with)
     */
    public UserInterface proceed(){
        proceed = true;
        try {
            if (this.waitingThread != null) waitingThread.join();
        } catch(InterruptedException iEx){
            waitingThread.interrupt();
        }

        return this;
    }

    /**
     * Get the current activity state of the user interface
     *
     * @return true is waiting, false else
     */
    public boolean isWaiting(){
        return !proceed;
    }

    // print an alert to inform the player that only the specified amount of seconds
    // remains
    private void alertTime(final String secondsLeft){
        AnsiTerminal.saveCursorPos();
        AnsiTerminal.moveCursorDown(2);
        new AnsiBuilder(DynamicTextComponent.TIME_ALERT.getComponent(secondsLeft))
                .setFont(RED, true)
                .print();
        AnsiTerminal.restoreCursorPos();
    }

    // clear the whole terminal
    private void emptyTerminal() {
        AnsiTerminal.clearTerminal();
    }

    // put a fixed size frame around the content
    private void frameContent(final StaticUIComponent title){
        // clear the terminal
        emptyTerminal();

        // load the frame components
        StaticUIComponent topFrame = AsciiArtDecorations.TOP_LINE;
        StaticUIComponent bottomFrame = AsciiArtDecorations.BOTTOM_LINE;

        // print the top frame part
        UserInterfaceUtils.printWithDefaultStyle(topFrame.getComponent());

        // save the cursor position right below the top frame, move down by the specified
        // screen height and print the bottom frame part
        AnsiTerminal.saveCursorPos();
        AnsiTerminal.moveCursorDown(FRAME_HEIGHT);
        UserInterfaceUtils.printWithDefaultStyle(bottomFrame.getComponent());

        // restore the saved cursor position and move one line down from there
        AnsiTerminal.restoreCursorPos();
        AnsiTerminal.moveCursorDown(1);

        // if a title was specified, print it in the default style, before applying the specified
        // padding below the title
        if(title != null) {
            UserInterfaceUtils.printWithDefaultStyle(title.getComponent());
            AnsiTerminal.moveCursorDown(PADDING_BORDER_TITLE);
        }
    }

    // prints the question and its answers, if answerIndex matches one of the answer lists entries
    // mark the corresponding answer
    private void printQuestion(final String question, final List<String> answers, final int answerIndex){
        StaticUIComponent title = AsciiArtTitles.QUESTION;
        frameContent(title);

        // print the question 2 lines below the position of the title
        AnsiTerminal.moveCursorDown(2);
        new AnsiBuilder(UserInterfaceUtils.splitPhraseAtSpace(question, MAX_TEXT_LENGTH))
                .setFont(BLUE, true)
                .println();

        // print the answers 1 line below the question
        AnsiTerminal.moveCursorDown(1);

        for(int i = 0; i<answers.size(); i++){
            String answer = answers.get(i);
            AnsiTerminal.moveCursorRight(5);

            // if the current questions index matches the provided answer index, mark the corresponding answer and continue
            if(i == answerIndex){
                new AnsiBuilder(UserInterfaceUtils.splitPhraseAtSpace(answer, MAX_TEXT_LENGTH))
                        .setFont(BLUE, true)
                        .setBackground(YELLOW, true)
                        .println();

                continue;
            }

            // print the answer (cannot use default style, because println is required!)
            new AnsiBuilder(UserInterfaceUtils.splitPhraseAtSpace(answer, MAX_TEXT_LENGTH))
                    .setFont(BLUE, true)
                    .println();
        }
    }

    // prints a red alert with the corresponding text to the terminal
    private void alertInvalidInput(final DynamicTextComponent alert, final String input){
        AnsiTerminal.moveCursorDown(1);

        new AnsiBuilder(alert.getComponent(input))
                .setFont(RED, true)
                .print();

        AnsiTerminal.restoreCursorPos();
        AnsiTerminal.clearRemainingOfLine();
    }

    // ask the player for a specified input
    private void requestUserInput(final StaticTextComponent request){
        AnsiTerminal.moveCursorDown(2);
        UserInterfaceUtils.printWithDefaultStyle(request.getComponent());
        AnsiTerminal.saveCursorPos();
    }
}
