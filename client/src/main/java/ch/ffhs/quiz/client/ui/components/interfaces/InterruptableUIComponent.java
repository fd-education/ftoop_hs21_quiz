package ch.ffhs.quiz.client.ui.components.interfaces;

public abstract class InterruptableUIComponent {
    protected static boolean stop = false;

    protected void stopExecution(){
        stop = true;
    }
}

