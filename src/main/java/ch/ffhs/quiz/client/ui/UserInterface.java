package ch.ffhs.quiz.client.ui;

import ch.ffhs.quiz.client.ui.text_components.AsciiArtDecorations;
import ch.ffhs.quiz.client.ui.text_components.AsciiArtNumbers;
import ch.ffhs.quiz.client.ui.text_components.AsciiArtTexts;
import ch.ffhs.quiz.client.ui.text_components.PlayerInteraction;

import java.util.List;

public class UserInterface {
    private boolean proceed;
    private Thread waitingThread;

    public void welcomeAndExplain(){
        AnsiTerminal.clearTerminal();
        AnsiTerminal.moveCursorDown(1);
        String welcome = AsciiArtTexts.WELCOME.getAscii();
        new AnsiBuilder(welcome).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).print();

        String explanation = PlayerInteraction.EXPLANATION.getInteraction();
        String formattedExplanation = new AnsiBuilder(explanation).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).create();
        UserInterfaceUtils.printLetterByLetter(formattedExplanation, UserInterfaceUtils.Delay.ZERO);

        await();
    }

    public void invalidInput(String text){
        new AnsiBuilder(text).setFont(AnsiBuilder.Color.RED, AnsiBuilder.Decoration.BOLD, true).print();
    }

    public void askForName(){
        AnsiTerminal.saveCursorPos();
        new AnsiBuilder(PlayerInteraction.ASK_FOR_NAME.getInteraction()).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).print();
    }

    public void welcomePlayerPersonally(String name){
        new AnsiBuilder(PlayerInteraction.PERSONALIZED_WELCOME.getInteraction(name)).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).print();
    }

    public void printQuestion(String question, List<String> answers){
        new AnsiBuilder(AsciiArtDecorations.TOP_LINE.getAscii()).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).print();
        AnsiTerminal.moveCursorDown(2);
        new AnsiBuilder(AsciiArtTexts.QUESTION.getAscii()).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).print();


        AnsiTerminal.moveCursorDown(2);
        AnsiTerminal.moveCursorRight(4);
        new AnsiBuilder(question).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).println();

        AnsiTerminal.moveCursorDown(1);

        answers.forEach(answer -> {
            AnsiTerminal.moveCursorRight(4);
            new AnsiBuilder(answer).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).println();
        });

        AnsiTerminal.moveCursorDown(2);
        new AnsiBuilder(AsciiArtDecorations.BOTTOM_LINE.getAscii()).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).print();
    }

    public void askForAnswer(){
        AnsiTerminal.moveCursorDown(2);
        new AnsiBuilder(PlayerInteraction.ASK_FOR_ANSWER.getInteraction()).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).print();
    }

    public void countdown(){
        try {
            for (int i = 5; i >= 0; i--) {

                new AnsiBuilder(AsciiArtDecorations.TOP_LINE.getAscii()).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).print();
                AnsiTerminal.moveCursorDown(6);

                new AnsiBuilder(AsciiArtNumbers.getAscii(i)).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).print();
                AnsiTerminal.moveCursorDown(6);

                new AnsiBuilder(AsciiArtDecorations.BOTTOM_LINE.getAscii()).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).print();
                Thread.sleep(1000);

                AnsiTerminal.clearTerminal();
            }
        }catch(InterruptedException ignored){}
    }

    public void waiting(String reason){
        waitingThread = new Thread(() -> {
            if(!proceed){
                String formattedReason = new AnsiBuilder(reason).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).create();
                System.out.print(formattedReason);


                String dots = " . . .";
                String formattedDots = new AnsiBuilder(dots).setFont(AnsiBuilder.Color.BLUE, AnsiBuilder.Decoration.BOLD, true).create();

                while(!proceed){
                    UserInterfaceUtils.printLetterByLetter(formattedDots, UserInterfaceUtils.Delay.SLOW);
                    AnsiTerminal.moveCursorLeft(6);
                    AnsiTerminal.clearRemainingOfLine();
                }
            }
        });

        waitingThread.start();
    }

    public UserInterface proceed(){
        try{
            proceed = true;
            if(this.waitingThread != null) waitingThread.join();
        } catch(InterruptedException ignored){}
        finally{
            return this;
        }
    }

    public void await(){
        proceed = false;
    }
}
