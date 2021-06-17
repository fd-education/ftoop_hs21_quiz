package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.InputHandler;
import ch.ffhs.quiz.client.ui.AnsiTerminal;
import ch.ffhs.quiz.client.ui.UserInterface;
import ch.ffhs.quiz.client.ui.components.text.StaticTextComponent;
import ch.ffhs.quiz.connectivity.Connection;
import ch.ffhs.quiz.messages.AnswerMessage;
import ch.ffhs.quiz.messages.FeedbackMessage;
import ch.ffhs.quiz.messages.QuestionMessage;
import ch.ffhs.quiz.messages.RoundSummaryMessage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

public class GameStage extends Stage{
    private String question;
    private List<String> answers;
    private boolean wasLastRound;

    private static final String RUNTIME_EX = "This exception must not occur, because inputs get checked.";

    public GameStage(final Client client, final Connection connection, final InputHandler inputHandler, final UserInterface ui){
        this.client = Objects.requireNonNull(client, "client must not be null");;
        this.serverConnection = Objects.requireNonNull(connection, "connection must not be null");
        this.inputHandler = Objects.requireNonNull(inputHandler, "inputHandler must not be null");
        this.ui = Objects.requireNonNull(ui, "ui must not be null");
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
            LocalDateTime before = LocalDateTime.now(ZoneId.systemDefault());
            int answerIndex = inputHandler.awaitUserAnswer();
            LocalDateTime after = LocalDateTime.now(ZoneId.systemDefault());
            Duration answerTime = Duration.between(before, after);

            AnsiTerminal.clearTerminal();
            if(answerIndex != -1) ui.markChosenAnswer(question, answers, answerIndex);
            ui.waiting(StaticTextComponent.WAITING_FOR_PLAYERS.getComponent());


            serverConnection.send(new AnswerMessage(answerIndex, answerTime));

            FeedbackMessage feedback = serverConnection.receive(FeedbackMessage.class);
            processFeedbackMessage(feedback);
        } catch(IOException ioEx){
            throw new RuntimeException(RUNTIME_EX, ioEx);
        }
    }

    @Override
    protected void terminateStage() {
        try{
            RoundSummaryMessage roundSummary = serverConnection.receive(RoundSummaryMessage.class);
            processRoundSummaryMessage(roundSummary);
        } catch(IOException ioEx){
            ioEx.printStackTrace();
            throw new RuntimeException(RUNTIME_EX, ioEx);
        }
    }

    public boolean wasLastRound(){return wasLastRound;}



    private void processFeedbackMessage(final FeedbackMessage feedback){
        Objects.requireNonNull(feedback, "feedback must not be null");

        ui.proceed();

        if(feedback.getWinningPlayer() == null) ui.printNooneCorrect();

        String winningPlayer = feedback.getWinningPlayer();
        if(feedback.wasCorrect() && feedback.wasFastest()){
            ui.printPlayerHasWon();
        } else if(feedback.wasCorrect()){
            ui.printPlayerOnlyWasCorrect(winningPlayer);
        } else{
            ui.printPlayerWasWrong(winningPlayer);
        }

        ui.sleepSave(10000);
    }

    private void processRoundSummaryMessage(final RoundSummaryMessage roundSummary){
        Objects.requireNonNull(roundSummary, "roundSummary must not be null");

        AnsiTerminal.clearTerminal();
        ui.printScoreboard(roundSummary.getRankedPlayersList(), client.getPlayerName());

        this.wasLastRound = roundSummary.isLastRound();
        ui.sleepSave(7500);
    }


}
