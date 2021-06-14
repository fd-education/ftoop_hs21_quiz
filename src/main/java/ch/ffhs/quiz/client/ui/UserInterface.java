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

        String formattedExplanation = UserInterfaceUtils.createWithDefaultStyle(explanation.getComponent());

        UserInterfaceUtils.printLetterByLetter(formattedExplanation, UserInterfaceUtils.Delay.FAST);
    }

    public void alertInvalidName(final String name){
        alertInvalidInput(DynamicTextComponent.NAME_INVALID, name);
    }

    public void askForName(){
        askForInput(StaticTextComponent.ASK_FOR_NAME);
    }

    public void alertNameReserved(final String name){
        alertInvalidInput(DynamicTextComponent.NAME_RESERVED, name);
    }

    public void welcomePlayerPersonally(final String name){
        AnsiTerminal.positionCursor(23, 0);
        AnsiTerminal.clearNumberOfLines(4);
        UserInterfaceUtils.printWithDefaultStyle(DynamicTextComponent.PERSONALIZED_WELCOME.getComponent(name));
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

    public void printQuestion(final String question, final List<String> answers){
        printQuestion(question, answers, -1);
    }

    public void alertInvalidAnswer(final String answer){
        alertInvalidInput(DynamicTextComponent.ANSWER_INVALID, answer);
    }

    public void markChosenAnswer(final String question, List<String> answers, int chosenAnswer){
        printQuestion(question, answers, chosenAnswer);
    }

    public void askForAnswer(){
        askForInput(StaticTextComponent.ASK_FOR_ANSWER);
    }

    public void alertTime(final String secondsLeft){
        AnsiTerminal.saveCursorPos();
        AnsiTerminal.moveCursorDown(2);
        new AnsiBuilder(DynamicTextComponent.TIME_ALERT.getComponent(secondsLeft))
                .setFont(RED, true)
                .print();
        AnsiTerminal.restoreCursorPos();
    }

    public void sleepSave(final int duration){
        try{
            Thread.sleep(duration);
        } catch(InterruptedException ignored){}
    }

    public void waiting(final String reason){
        waitingThread = new Thread(() -> {

                if (!proceed) {
                    AnsiTerminal.positionCursor(25, 35);
                    UserInterfaceUtils.printWithDefaultStyle(reason);

                    while (!proceed) {
                        UserInterfaceUtils.printLetterByLetter(UserInterfaceUtils.createWithDefaultStyle(" . . ."), UserInterfaceUtils.Delay.SLOW);
                        AnsiTerminal.moveCursorLeft(6);
                        AnsiTerminal.clearRemainingOfLine();
                    }
                }

                AnsiTerminal.clearLine();
        });

        waitingThread.start();
    }

    public void printPlayerHasWon(){
        AnsiTerminal.moveCursorDown(2);
        new AnsiBuilder(StaticTextComponent.PLAYER_WON.getComponent()).setFont(GREEN, true).print();
    }

    public void printPlayerOnlyWasCorrect(final String winningPlayer){
        AnsiTerminal.moveCursorDown(2);
        new AnsiBuilder(DynamicTextComponent.CORRECT_ANSWER.getComponent(winningPlayer)).setFont(YELLOW, false).print();
    }

    public void printPlayerWasWrong(final String winningPlayer){
        AnsiTerminal.moveCursorDown(2);
        new AnsiBuilder(DynamicTextComponent.WRONG_ANSWER.getComponent(winningPlayer)).setFont(RED, false).print();
    }

    public void printNooneCorrect(){
        AnsiTerminal.moveCursorDown(2);
        new AnsiBuilder(StaticTextComponent.NO_PLAYER_CORRECT.getComponent()).setFont(RED, false).print();
    }

    public void printScoreboard(final List<ScoreboardEntry> scoreboardEntries, String name){
        frameContent(AsciiArtTitles.SCORE);

        AnsiTerminal.moveCursorDown(1);
        Scoreboard sb = new Scoreboard(scoreboardEntries);
        UserInterfaceUtils.printWithDefaultStyle(sb.getScoreboardForPlayer(name));
    }

    public void printEnd(){
        frameContent(null);
        AnsiTerminal.moveCursorDown(6);
        UserInterfaceUtils.printWithDefaultStyle(AsciiArtTitles.END.getComponent());

        AnsiTerminal.moveCursorDown(2);
        UserInterfaceUtils.printWithDefaultStyle(StaticTextComponent.THANKS.getComponent());

        sleepSave(10000);
        emptyTerminal();
    }

    public UserInterface proceed(){
        proceed = true;
        if(this.waitingThread != null) waitingThread.interrupt();

        return this;
    }

    public void await(){
        proceed = false;
    }

    private void emptyTerminal() {
        AnsiTerminal.clearTerminal();
    }

    private void frameContent(final StaticUIComponent title){
        emptyTerminal();
        StaticUIComponent topFrame = AsciiArtDecorations.TOP_LINE;
        StaticUIComponent bottomFrame = AsciiArtDecorations.BOTTOM_LINE;

        UserInterfaceUtils.printWithDefaultStyle(topFrame.getComponent());

        AnsiTerminal.saveCursorPos();
        AnsiTerminal.moveCursorDown(FRAME_HEIGHT);
        UserInterfaceUtils.printWithDefaultStyle(bottomFrame.getComponent());

        AnsiTerminal.restoreCursorPos();
        AnsiTerminal.moveCursorDown(1);

        if(title != null) {
            UserInterfaceUtils.printWithDefaultStyle(title.getComponent());
            AnsiTerminal.moveCursorDown(PADDING_BORDER_TITLE);
        }
    }

    private void printQuestion(final String question, final List<String> answers, final int answerIndex){
        StaticUIComponent title = AsciiArtTitles.QUESTION;
        frameContent(title);

        AnsiTerminal.moveCursorDown(2);
        AnsiTerminal.moveCursorRight(5);
        new AnsiBuilder(UserInterfaceUtils.splitPhrase(question, MAX_TEXT_LENGTH))
                .setFont(BLUE, true)
                .println();

        AnsiTerminal.moveCursorDown(1);

        for(int i = 0; i<answers.size(); i++){
            String answer = answers.get(i);
            AnsiTerminal.moveCursorRight(5);

            if(i == answerIndex){
                new AnsiBuilder(UserInterfaceUtils.splitPhrase(answer, MAX_TEXT_LENGTH))
                        .setFont(BLUE, true)
                        .setBackground(YELLOW, true)
                        .println();

                continue;
            }

            new AnsiBuilder(UserInterfaceUtils.splitPhrase(answer, MAX_TEXT_LENGTH))
                    .setFont(BLUE, true)
                    .println();
        }
    }

    private void alertInvalidInput(final DynamicTextComponent alert, final String input){
        AnsiTerminal.moveCursorDown(1);

        new AnsiBuilder(alert.getComponent(input))
                .setFont(RED, true)
                .print();

        AnsiTerminal.restoreCursorPos();
        AnsiTerminal.clearRemainingOfLine();
    }

    private void askForInput(final StaticTextComponent ask){
        AnsiTerminal.moveCursorDown(2);
        UserInterfaceUtils.printWithDefaultStyle(ask.getComponent());
        AnsiTerminal.saveCursorPos();
    }
}
