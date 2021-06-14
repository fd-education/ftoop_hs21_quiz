package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.client.ui.AnsiTerminal;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.client.ui.components.text.StaticTextComponent;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.messages.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameStage extends Stage{
    private String question;
    private List<String> answers;
    private boolean wasLastRound;

    private static final String RUNTIME_EX = "This exception must not occur, because inputs get checked.";

    public GameStage(Client client, Connection con, InputHandler inputHandler, UserInterface ui){
        this.inputHandler = inputHandler;
        this.client = client;
        this.serverConnection = con;
        this.ui = ui;
    }

    @Override
    protected void setupStage(){
        try {
            QuestionMessage questionMessage = serverConnection.receive(QuestionMessage.class);
            question = questionMessage.getQuestion();
            answers = questionMessage.getAnswers();
        } catch(IOException ioEx){
            throw new RuntimeException(RUNTIME_EX, ioEx);
        }
    }

    @Override
    protected void createInitialUserInterface(){
        ui.proceed();
        AnsiTerminal.clearTerminal();
        ui.countdown();

        ui.printQuestion(question, answers);

        ui.askForAnswer();
    }

    @Override
    protected void handleConversation() {
        try{
            String answer = inputHandler.getUserAnswer();
            int answerIndex = mapStringAnswerToInteger(answer.toUpperCase());

            AnsiTerminal.clearTerminal();
            ui.await();
            ui.markChosenAnswer(question, answers, answerIndex);
            ui.waiting(StaticTextComponent.WAITING_FOR_PLAYERS.getText(), 3000000);

            serverConnection.send(new AnswerMessage(answerIndex));

            FeedbackMessage feedback = serverConnection.receive(FeedbackMessage.class);
            processFeedbackMessage(feedback);

            // TODO REmove asap
            List<ScoreboardEntry> scores = new ArrayList<>(
                    List.of(new ScoreboardEntry("Fabian", 50),
                            new ScoreboardEntry("Nicola", 200),
                            new ScoreboardEntry("Sebastian", 150),
                            new ScoreboardEntry("Alexander", 0)
                            )
            );

            RoundSummaryMessage roundSummary = new RoundSummaryMessage(scores, false);

            //TODO uncomment -> correct line
            //RoundSummaryMessage roundSummary = serverConnection.receive(RoundSummaryMessage.class);
            processRoundSummaryMessage(roundSummary);
        } catch(IOException ioEx){
            throw new RuntimeException(RUNTIME_EX, ioEx);
        }
    }

    @Override
    protected void terminateStage() {
        try{
            RoundSummaryMessage scoreboardMessage = serverConnection.receive(RoundSummaryMessage.class);
            wasLastRound = scoreboardMessage.isLastRound();
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

    private void processFeedbackMessage(FeedbackMessage feedback){
        if(feedback.getWinningPlayer() == null) ui.printNooneCorrect();

        String winningPlayer = feedback.getWinningPlayer();
        if(feedback.wasCorrect() && feedback.wasFastest()){
            ui.printPlayerHasWon();
        } else if(feedback.wasCorrect()){
            ui.printPlayerOnlyWasCorrect(winningPlayer);
        } else{
            ui.printPlayerWasWrong(winningPlayer);
        }
    }

    private void processRoundSummaryMessage(RoundSummaryMessage roundSummary){
        AnsiTerminal.clearTerminal();
        ui.printScoreboard(roundSummary.getRankedPlayersList(), client.getPlayerName());

        this.wasLastRound = roundSummary.isLastRound();
        ui.await();
    }
}
