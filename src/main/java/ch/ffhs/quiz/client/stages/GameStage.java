package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.client.textinterface.TextInterface;
import ch.ffhs.quiz.client.ui.AnsiBuilder;
import ch.ffhs.quiz.client.ui.AnsiBuilder.Color;
import ch.ffhs.quiz.client.ui.AnsiBuilder.Decoration;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.messages.*;

import java.io.IOException;
import java.util.List;

public class GameStage extends Stage{
    private String question;
    private List<String> answers;
    private boolean wasLastRound;

    private static final String RUNTIME_EX = "This exception must not occur, because inputs get checked.";

    public GameStage(Client client, Connection con, InputHandler inputHandler){
        this.inputHandler = inputHandler;
        this.client = client;
        this.serverConnection = con;
    }

    @Override
    protected void setupStage(){
        try {
            QuestionMessage questionMessage = serverConnection.receive(QuestionMessage.class);
            question = questionMessage.getText();
            answers = questionMessage.getAnswers();
        } catch(IOException ioEx){
            throw new RuntimeException(RUNTIME_EX, ioEx);
        }
    }

    @Override
    protected void createUserInterface(){
        String questionTitle = TextInterface.QUESTION.getComponent();
        new AnsiBuilder(questionTitle).font(Color.BLUE, Decoration.BOLD, true).print();

        System.out.println(question);
        answers.forEach(System.out::println);
        // TODO: implement
        // TextInterface.printQuestion(question, answers);
    }

    @Override
    protected void handleConversation() {
        try{
            String answer = inputHandler.getUserAnswer();
            serverConnection.send(new AnswerMessage(mapStringAnswerToInteger(answer)));

            FeedbackMessage feedbackMessage = serverConnection.receive(FeedbackMessage.class);
            System.out.println(feedbackMessage.getText());// Handle correct output
        } catch(IOException ioEx){
            throw new RuntimeException(RUNTIME_EX, ioEx);
        }
    }

    @Override
    protected void terminateStage() {
        try{
            RoundSummaryMessage scoreboardMessage = serverConnection.receive(RoundSummaryMessage.class);
            wasLastRound = scoreboardMessage.isLastRound();
            System.out.println("Scoreboard");
        } catch(IOException ioEx){
            ioEx.printStackTrace();
            throw new RuntimeException(RUNTIME_EX, ioEx);
        }
    }

    public boolean wasLastRound(){return wasLastRound;}

    private int mapStringAnswerToInteger(String answer){
        return switch (answer) {
            case "A" -> 0;
            case "B" -> 1;
            case "C" -> 2;
            default -> -1;
        };
    }
}
