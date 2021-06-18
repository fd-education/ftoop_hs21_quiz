package ch.ffhs.quiz.client.ui.components;

import ch.ffhs.quiz.client.ui.AnsiBuilder;
import ch.ffhs.quiz.client.ui.AnsiTerminal;
import ch.ffhs.quiz.client.ui.components.text.DynamicTextComponent;

import static ch.ffhs.quiz.client.ui.AnsiBuilder.Color.RED;

/**
 * Class to represent a time alert
 */
public class TimeAlertRunnable implements Runnable{
    private final String alertMessage;

    /**
     * Constructor takes an integer value for how many seconds remain.
     * @param secondsLeft how many seconds remain
     */
    public TimeAlertRunnable(int secondsLeft){
        if(secondsLeft <= 0) throw new IllegalArgumentException("secondsLeft must be greater than 0");

        this.alertMessage = DynamicTextComponent.TIME_ALERT.getComponent(Integer.toString(secondsLeft));
    }

    /**
     * Prints out the alert message in red color three lines below the current cursor position
     */
    @Override
    public void run(){
        AnsiTerminal.saveCursorPos();
        AnsiTerminal.moveCursorDown(3);
        new AnsiBuilder(alertMessage)
                .setFont(RED, true)
                .print();
        AnsiTerminal.restoreCursorPos();
    }
}
