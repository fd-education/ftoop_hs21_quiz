package ch.ffhs.quiz.client.stages;

import ch.ffhs.quiz.client.Client;
import ch.ffhs.quiz.client.ui.InputHandler;
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

/**
 * The GameStage class orchestrates the whole game process.
 * From asking the question, to await the answer, to send it to the server
 * and to handle the response.
 */
public class GameStage extends Stage{
    private String question;
    private List<String> answers;
    private boolean wasLastRound;

    /**
     * Instantiates a new Game stage.
     *
     * @param client       the client
     * @param connection   the connection
     * @param inputHandler the input handler
     * @param ui           the ui
     */
    public GameStage(final Client client, final Connection connection, final InputHandler inputHandler, final UserInterface ui){
        this.client = Objects.requireNonNull(client, "client must not be null");
        this.serverConnection = Objects.requireNonNull(connection, "connection must not be null");
        this.inputHandler = Objects.requireNonNull(inputHandler, "inputHandler must not be null");
        this.ui = Objects.requireNonNull(ui, "ui must not be null");
    }

    // Receive the question and answers from the server, save them into variables for accessibility
    @Override
    protected void setupStage(){
        try {
            QuestionMessage questionMessage = serverConnection.receive(QuestionMessage.class);
            question = questionMessage.getQuestion();
            answers = questionMessage.getAnswers();
            logger.info("Received question and answers from server.");
        } catch(IOException ioEx){
            logger.warning("IOException: Receiving questions and answers from server failed: " + ioEx.getMessage());
            ui.printErrorScreen();
            System.exit(-1);
        }
    }

    // Print a countdown from 5 to 0 and then print the question,
    // along with a request for a user input
    @Override
    protected void createInitialUserInterface(){
        ui.proceed();
        AnsiTerminal.clearTerminal();
        ui.countdown();
        ui.printQuestion(question, answers);
        ui.askForAnswer();
        logger.info("Print question and answers, ask for user to answer.");
    }

    // Await the users answer, clip the time, hand the answer on to the server
    // finally handle the servers feedback on the users answer
    @Override
    protected void handleConversation() {
        try{

            LocalDateTime before = LocalDateTime.now(ZoneId.systemDefault());
            logger.info("Awaiting user answer...");
            int chosenAnswer = inputHandler.awaitUserAnswer();

            LocalDateTime after = LocalDateTime.now(ZoneId.systemDefault());
            Duration answerTime = Duration.between(before, after);

            logger.info("Answer received, highlighting: " + chosenAnswer);
            AnsiTerminal.clearTerminal();
            if(chosenAnswer != -1) ui.markChosenAnswer(question, answers, chosenAnswer);
            ui.waiting(StaticTextComponent.WAITING_FOR_PLAYERS.getComponent());

            serverConnection.send(new AnswerMessage(chosenAnswer, answerTime));
            logger.info("Sent answer message. Waiting for feedback ... ");
            FeedbackMessage feedback = serverConnection.receive(FeedbackMessage.class);

            processFeedbackMessage(feedback, chosenAnswer);
        } catch(IOException ioEx){
            logger.warning("IOException: Receiving feedback from server failed. \n" + ioEx.getMessage());
            ui.printErrorScreen();
            System.exit(-1);
        }
    }

    // Process the round summary, that gets sent by the server
    @Override
    protected void terminateStage() {
        try{
            logger.info("Waiting for round summary ... ");
            RoundSummaryMessage roundSummary = serverConnection.receive(RoundSummaryMessage.class);
            processRoundSummaryMessage(roundSummary);

            logger.info("Waiting for next round to start/ game to end ...");
        } catch(IOException ioEx){
            logger.warning("IOException: Receiving round summary from server failed. \n" + ioEx.getMessage());
            ui.printErrorScreen();
            System.exit(-1);
        }
    }

    /**
     * Check if the currently processed round was the last one.
     *
     * @return true if it was the last round, false otherwise
     */
    public boolean wasLastRound(){return wasLastRound;}

    // Read all the information about the players and their result
    // on the previous question to adapt the user interface
    private void processFeedbackMessage(final FeedbackMessage feedback, final int chosenAnswer){
        Objects.requireNonNull(feedback, "feedback must not be null");

        logger.info("Feedback received. Now processing...");

        ui.proceed();
        ui.markCorrectAndChosenAnswer(question, answers, chosenAnswer, feedback.getCorrectAnswerNumber());

        if(feedback.getWinningPlayer().isBlank()){
            ui.printNooneCorrect();
        } else {
            handleWinningInformation(feedback);
        }

        logger.info("Gave feedback to the user.");
        ui.sleepSave(3000);
    }

    private void handleWinningInformation(FeedbackMessage feedback){
        String winningPlayer = feedback.getWinningPlayer();
        if(feedback.wasCorrect() && feedback.wasFastest()){
            ui.printPlayerHasWon();
        } else if(feedback.wasCorrect()){
            ui.printPlayerOnlyWasCorrect(winningPlayer);
        } else{
            ui.printPlayerWasWrong(winningPlayer);
        }
    }

    // Read all the information about the scores of the players to create and print
    // out a scoreboard
    private void processRoundSummaryMessage(final RoundSummaryMessage roundSummary){
        Objects.requireNonNull(roundSummary, "roundSummary must not be null");

        AnsiTerminal.clearTerminal();
        ui.printScoreboard(roundSummary.getRankedPlayersList(), client.getPlayerName());
        logger.info("Printed Scoreboard of round summary.");

        this.wasLastRound = roundSummary.isLastRound();
        ui.sleepSave(4000);
    }


}
