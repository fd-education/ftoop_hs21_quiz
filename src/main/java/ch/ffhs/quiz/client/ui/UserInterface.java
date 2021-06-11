package ch.ffhs.quiz.client.ui;

import ch.ffhs.quiz.client.textinterface.TextComponents;

public class UserInterface {
    private boolean proceed;
    private Thread waitingThread;

    public void welcomeAndExplain(){
        new AnsiTerminal().clearTerminal();
        String welcome = TextComponents.WELCOME.getComponent();
        new AnsiBuilder(welcome).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).print();

        String explanation = TextComponents.EXPLANATION.getComponent();
        String formattedExplanation = new AnsiBuilder(explanation).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).create();
        UserInterfaceUtils.printLetterByLetter(formattedExplanation, UserInterfaceUtils.Delay.FAST);

        await();
        waiting(TextComponents.WAITING_FOR_PLAYERS.getComponent());
    }

    public void waiting(String reason){
        waitingThread = new Thread(() -> {
            if(!proceed){
                String formattedReason = new AnsiBuilder(reason).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).create();
                System.out.print(formattedReason);


                String dots = " . . .";
                String formattedDots = new AnsiBuilder(dots).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).create();

                while(!proceed){
                    UserInterfaceUtils.printLetterByLetter(formattedDots, UserInterfaceUtils.Delay.ZERO);
                    new AnsiTerminal().moveCursorLeft(6);
                    new AnsiTerminal().clearRemainingOfLine();
                }
            }
        });

        waitingThread.start();
    }

    public void proceed(){
        try{
            proceed = true;
            waitingThread.join();
        } catch(InterruptedException ignored){}
    }

    public void await(){
        proceed = false;
    }
}
