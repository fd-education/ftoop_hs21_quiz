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

import static ch.ffhs.quiz.client.ui.AnsiBuilder.Color.*;
import static ch.ffhs.quiz.client.ui.AnsiBuilder.Decoration.BOLD;

public class UserInterface {
    private boolean proceed;
    private Thread waitingThread;
    private final static int MAX_TEXT_LENGTH = 95;
    private final static int FRAME_HEIGHT = 26;
    private final static int PADDING_BORDER_TITLE = 2;

    public void welcomeAndExplain(){
        StaticUIComponent welcome = AsciiArtTitles.FACADE_QUIZ;
        StaticUIComponent explanation = StaticTextComponent.EXPLANATION;

        frameContent(welcome);

        String formattedExplanation = UserInterfaceUtils.createWithDefaultStyle(explanation.getText());

        // TODO: Change delay to be fast
        UserInterfaceUtils.printLetterByLetter(formattedExplanation, UserInterfaceUtils.Delay.ZERO);
    }

    public void alertInvalidName(String name){
        alertInvalidInput(DynamicTextComponent.NAME_INVALID, name);
    }

    public void askForName(){
        askForInput(StaticTextComponent.ASK_FOR_NAME);
    }

    public void alertNameReserved(String name){
        alertInvalidInput(DynamicTextComponent.NAME_RESERVED, name);
    }

    public void welcomePlayerPersonally(String name){
        AnsiTerminal.positionCursor(23, 0);
        AnsiTerminal.clearNumberOfLines(4);
        UserInterfaceUtils.printWithDefaultStyle(DynamicTextComponent.PERSONALIZED_WELCOME.getText(name));
    }

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

    public void printQuestion(String question, List<String> answers){
        printQuestion(question, answers, -1);
    }

    public void alertInvalidAnswer(String answer){
        alertInvalidInput(DynamicTextComponent.ANSWER_INVALID, answer);
    }

    public void markChosenAnswer(String question, List<String> answers, int chosenAnswer){
        printQuestion(question, answers, chosenAnswer);
    }

    public void askForAnswer(){
        askForInput(StaticTextComponent.ASK_FOR_ANSWER);
    }

    public void waiting(String reason){
        waiting(reason, 0);
    }

    public void waiting(String reason, int duration){
        waitingThread = new Thread(() -> {
            try {
                Thread.sleep(duration);

                if (!proceed) {
                    AnsiTerminal.positionCursor(25, 35);
                    UserInterfaceUtils.printWithDefaultStyle(reason);

                    while (!proceed) {
                        UserInterfaceUtils.printLetterByLetter(UserInterfaceUtils.createWithDefaultStyle(" . . ."), UserInterfaceUtils.Delay.SLOW);
                        AnsiTerminal.moveCursorLeft(6);
                        AnsiTerminal.clearRemainingOfLine();
                    }
                }
            }catch(InterruptedException ignored){}
        });

        waitingThread.start();
    }

    public void printPlayerHasWon(){
        new AnsiBuilder(StaticTextComponent.PLAYER_WON.getText()).setFont(GREEN, BOLD, true).print();
    }

    public void printPlayerOnlyWasCorrect(String winningPlayer){
        new AnsiBuilder(DynamicTextComponent.CORRECT_ANSWER.getText(winningPlayer)).setFont(YELLOW, BOLD, false).print();
    }

    public void printPlayerWasWrong(String winningPlayer){
        new AnsiBuilder(DynamicTextComponent.WRONG_ANSWER.getText(winningPlayer)).setFont(RED, BOLD, false).print();
    }

    public void printNooneCorrect(){
        new AnsiBuilder(StaticTextComponent.NO_PLAYER_CORRECT.getText()).setFont(RED, BOLD, false).print();
    }

    public void printScoreboard(List<ScoreboardEntry> scoreboardEntries, String name){

        frameContent(AsciiArtTitles.SCORE);

        Scoreboard sb = new Scoreboard(scoreboardEntries);
        UserInterfaceUtils.printWithDefaultStyle(sb.getScoreboardForPlayer(name));
    }

    public UserInterface proceed(){
        try{
            proceed = true;
            if(this.waitingThread != null) waitingThread.join();
        } catch(InterruptedException ignored){}

        return this;
    }

    public void await(){
        proceed = false;
    }

    private static void emptyTerminal() {
        AnsiTerminal.clearTerminal();
        AnsiTerminal.moveCursorDown(1);
    }

    private static void frameContent(StaticUIComponent title){
        emptyTerminal();
        StaticUIComponent topFrame = AsciiArtDecorations.TOP_LINE;
        StaticUIComponent bottomFrame = AsciiArtDecorations.BOTTOM_LINE;

        UserInterfaceUtils.printWithDefaultStyle(topFrame.getText());

        AnsiTerminal.saveCursorPos();
        AnsiTerminal.moveCursorDown(FRAME_HEIGHT);
        UserInterfaceUtils.printWithDefaultStyle(bottomFrame.getText());

        AnsiTerminal.restoreCursorPos();
        AnsiTerminal.moveCursorDown(1);

        if(title != null) {
            UserInterfaceUtils.printWithDefaultStyle(title.getText());
            AnsiTerminal.moveCursorDown(PADDING_BORDER_TITLE);
        }
    }

    private void printQuestion(String question, List<String> answers, int answerIndex){
        StaticUIComponent title = AsciiArtTitles.QUESTION;
        frameContent(title);

        AnsiTerminal.moveCursorDown(2);
        AnsiTerminal.moveCursorRight(5);
        new AnsiBuilder(UserInterfaceUtils.splitPhrase(question, MAX_TEXT_LENGTH))
                .setFont(BLUE, BOLD, true)
                .println();

        AnsiTerminal.moveCursorDown(1);

        for(int i = 0; i<answers.size(); i++){
            String answer = answers.get(i);
            AnsiTerminal.moveCursorRight(5);

            if(i == answerIndex){
                new AnsiBuilder(UserInterfaceUtils.splitPhrase(answer, MAX_TEXT_LENGTH))
                        .setFont(BLUE, BOLD, true)
                        .setBackground(YELLOW, true)
                        .println();

                continue;
            }

            new AnsiBuilder(UserInterfaceUtils.splitPhrase(answer, MAX_TEXT_LENGTH))
                    .setFont(BLUE, BOLD, true)
                    .println();
        }
    }

    private void alertInvalidInput(DynamicTextComponent alert, String input){
        AnsiTerminal.moveCursorDown(1);

        new AnsiBuilder(alert.getText(input))
                .setFont(RED, BOLD, true)
                .print();

        AnsiTerminal.restoreCursorPos();
        AnsiTerminal.clearRemainingOfLine();
    }

    private void askForInput(StaticTextComponent ask){
        AnsiTerminal.moveCursorDown(2);
        UserInterfaceUtils.printWithDefaultStyle(ask.getText());
        AnsiTerminal.saveCursorPos();
    }
}