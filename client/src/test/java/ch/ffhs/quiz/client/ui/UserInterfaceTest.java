package ch.ffhs.quiz.client.ui;

import ch.ffhs.quiz.messages.ScoreboardEntry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceTest {
    static UserInterface ui;
    static String question;
    static List<String> answers;
    static List<ScoreboardEntry> scoresLong;


    @BeforeAll
    static void setup(){
        ui = new UserInterface();

        question = "Question 2: Some other question that is probably very easy...";
        answers = new ArrayList<>(List.of(
                "A: stupid answer one",
                "B: rather strange, but correct one",
                "C: most sensible, but wrong"));

        scoresLong = new ArrayList<>(
                List.of(new ScoreboardEntry("Player1", 950),
                        new ScoreboardEntry("Player2", 900),
                        new ScoreboardEntry("Player3", 800),
                        new ScoreboardEntry("Player4", 700),
                        new ScoreboardEntry("Player5", 600),
                        new ScoreboardEntry("Player6", 500),
                        new ScoreboardEntry("Player7", 400),
                        new ScoreboardEntry("Player8", 300),
                        new ScoreboardEntry("Player9", 200),
                        new ScoreboardEntry("Player10", 100),
                        new ScoreboardEntry("Player11", 90),
                        new ScoreboardEntry("Player12", 80),
                        new ScoreboardEntry("Player13", 70),
                        new ScoreboardEntry("Player14", 60),
                        new ScoreboardEntry("Player15", 50)
                )
        );
    }

    @Test
    void welcomeAndExplainTest() throws Exception{
        String EXPECTED = """
                [H[2J[1;34m╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
                ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
                [0m[s[26E[1;34m╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
                ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝
                [0m[u[1E[1;34m            ███████╗ █████╗  ██████╗ █████╗ ██████╗ ███████╗     ██████╗ ██╗   ██╗██╗███████╗
                            ██╔════╝██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝    ██╔═══██╗██║   ██║██║╚══███╔╝
                            █████╗  ███████║██║     ███████║██║  ██║█████╗      ██║   ██║██║   ██║██║  ███╔╝
                            ██╔══╝  ██╔══██║██║     ██╔══██║██║  ██║██╔══╝      ██║▄▄ ██║██║   ██║██║ ███╔╝
                            ██║     ██║  ██║╚██████╗██║  ██║██████╔╝███████╗    ╚██████╔╝╚██████╔╝██║███████╗
                            ╚═╝     ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═════╝ ╚══════╝     ╚══▀▀═╝  ╚═════╝ ╚═╝╚══════╝
                [0m[2E[1;94m     Ein ganz normales Quiz... wie schwer kann das schon sein.
                     Ziel ist es, die richtige Antwort so schnell wie möglich zu finden
                     und den entsprechenden Buchstaben einzutippen.
                     Vorsicht: Die erste Antwort zählt. Überleg dir also gut, ob du dir dabei sicher bist!
                                
                     Sollte niemand die Antwort wissen, geht die Runde nach 60 Sekunden vorbei.
                                
                     Viel Spass (und Glück, falls du's nötig hast)!
                [0m""";


        String output = tapSystemOutNormalized(()->ui.welcomeAndExplain());
        assertEquals(EXPECTED, output);
    }

    @Test
    void alertInvalidNameLengthTest() throws Exception{
        String EXPECTED = """
                [1E[1;91m     aaa kann nicht verarbeitet werden.\s
                     Dein Name muss mindestens 3 Buchstaben enthalten.[0m[u[K""";

        String output = tapSystemOutNormalized(()->ui.alertInvalidNameLength("aaa"));
        assertEquals(EXPECTED, output);
    }

    @Test
    void alertInvalidNameCharactersTest() throws Exception{
        String EXPECTED = """
                [1E[1;91m    ___ kann nicht verarbeitet werden.\s
                    Nur Zeichen von A-z, Zahlen und Bindestriche sind erlaubt.[0m[u[K""";

        String output = tapSystemOutNormalized(()->ui.alertInvalidNameCharacters("___"));
        assertEquals(EXPECTED, output);
    }

    @Test
    void askForNameTest() throws Exception {
        String EXPECTED = """
                [2E[1;34m     Tippe einen Namen ein: [0m[s""";

        String output = tapSystemOutNormalized(()->ui.askForName());
        assertEquals(EXPECTED, output);
    }

    @Test
    void alertNameReservedTest() throws Exception {
        String EXPECTED = """
                [1E[1;91m     Player1 ist bereits vergeben.[0m[u[K""";

        String output = tapSystemOutNormalized(()->ui.alertNameReserved("Player1"));
        assertEquals(EXPECTED, output);
    }

    @Test
    void alertNameReserved_Negative_Name() {
        String EXPECTED_EMPTY = "name must not be empty or consist of only whitespace";
        String EXPECTED_NULL = "name must not be null";

        assertThrows(IllegalArgumentException.class, () -> ui.alertNameReserved("  "));
        assertThrows(IllegalArgumentException.class, () -> ui.alertNameReserved(""));
        assertThrows(NullPointerException.class, () -> ui.alertNameReserved(null));

        try{
            ui.alertNameReserved("  ");
        }catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED_EMPTY, iAEx.getMessage());
        }

        try{
            ui.alertNameReserved("");
        }catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED_EMPTY, iAEx.getMessage());
        }

        try{
            ui.alertNameReserved(null);
        }catch(NullPointerException npe){
            assertEquals(EXPECTED_NULL, npe.getMessage());
        }
    }

    @Test
    void welcomePlayerPersonallyTest() throws Exception{
        String EXPECTED = """
                [22;0H[s[2K[1E[2K[1E[2K[1E[2K[1E[u[1;34m     Hallo Player1![0m""";

        String output = tapSystemOutNormalized(()->ui.welcomePlayerPersonally("Player1"));
        assertEquals(EXPECTED, output);
    }

    @Test
    void welcomePlayerPersonallyTest_Negative_Name(){
        String EXPECTED_EMPTY = "name must not be empty or consist of only whitespace";
        String EXPECTED_NULL = "name must not be null";

        assertThrows(IllegalArgumentException.class, () -> ui.welcomePlayerPersonally("  "));
        assertThrows(IllegalArgumentException.class, () -> ui.welcomePlayerPersonally(""));
        assertThrows(NullPointerException.class, () -> ui.alertNameReserved(null));

        try{
            ui.welcomePlayerPersonally("  ");
        }catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED_EMPTY, iAEx.getMessage());
        }

        try{
            ui.welcomePlayerPersonally("");
        }catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED_EMPTY, iAEx.getMessage());
        }

        try{
            ui.welcomePlayerPersonally(null);
        }catch(NullPointerException npe){
            assertEquals(EXPECTED_NULL, npe.getMessage());
        }
    }

    @Test
    void countdownTest() throws Exception {
        String EXPECTED = """
                [H[2J[H[2J[1;34m╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
                ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
                [0m[s[26E[1;34m╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
                ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝
                [0m[u[1E[9E[1;34m                                               ███████╗
                                                               ██╔════╝
                                                               ███████╗
                                                               ╚════██║
                                                               ███████║
                                                               ╚══════╝
                [0m[H[2J[1;34m╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
                ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
                [0m[s[26E[1;34m╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
                ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝
                [0m[u[1E[9E[1;34m                                               ██╗  ██╗
                                                               ██║  ██║
                                                               ███████║
                                                               ╚════██║
                                                                    ██║
                                                                    ╚═╝
                [0m[H[2J[1;34m╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
                ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
                [0m[s[26E[1;34m╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
                ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝
                [0m[u[1E[9E[1;34m                                               ██████╗
                                                               ╚════██╗
                                                                █████╔╝
                                                                ╚═══██╗
                                                               ██████╔╝
                                                               ╚═════╝
                [0m[H[2J[1;34m╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
                ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
                [0m[s[26E[1;34m╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
                ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝
                [0m[u[1E[9E[1;34m                                               ██████╗
                                                               ╚════██╗
                                                                █████╔╝
                                                               ██╔═══╝
                                                               ███████╗
                                                               ╚══════╝
                [0m[H[2J[1;34m╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
                ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
                [0m[s[26E[1;34m╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
                ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝
                [0m[u[1E[9E[1;34m                                                  ██╗
                                                                 ███║
                                                                 ╚██║
                                                                  ██║
                                                                  ██║
                                                                  ╚═╝
                [0m[H[2J[1;34m╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
                ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
                [0m[s[26E[1;34m╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
                ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝
                [0m[u[1E[9E[1;34m                                                ██████╗
                                                               ██╔═████╗
                                                               ██║██╔██║
                                                               ████╔╝██║
                                                               ╚██████╔╝
                                                                ╚═════╝
                [0m""";

        String output = tapSystemOutNormalized(()->ui.countdown());
        assertEquals(EXPECTED, output);
    }

    @Test
    void printQuestionTest() throws Exception{
        String EXPECTED_OUTPUT = """
                [H[2J[1;34m╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
                ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
                [0m[s[26E[1;34m╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
                ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝
                [0m[u[1E[1;34m                                ███████╗██████╗  █████╗  ██████╗ ███████╗
                                                ██╔════╝██╔══██╗██╔══██╗██╔════╝ ██╔════╝
                                                █████╗  ██████╔╝███████║██║  ███╗█████╗
                                                ██╔══╝  ██╔══██╗██╔══██║██║   ██║██╔══╝
                                                ██║     ██║  ██║██║  ██║╚██████╔╝███████╗
                                                ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝
                [0m[2E[2E[5C[1;94mQuestion 2: Some other question that is probably very easy...[0m
                [1E[5C[1;94mA: stupid answer one[0m
                [5C[1;94mB: rather strange, but correct one[0m
                [5C[1;94mC: most sensible, but wrong[0m
                """;

        String terminalOutput = tapSystemOutNormalized(()->ui.printQuestion(question, answers));
        assertEquals(EXPECTED_OUTPUT, terminalOutput );
    }

    @Test
    void printQuestion_QUESTION_NULL(){
        String EXPECTED_MESSAGE = "question must not be null";
        assertThrows(NullPointerException.class, () -> ui.printQuestion(null, answers));

        try{
            ui.printQuestion(null, answers);
        }catch(NullPointerException npe){
            assertEquals(EXPECTED_MESSAGE, npe.getMessage());
        }
    }

    @Test
    void printQuestion_QUESTION_EMPTY(){
        String EXPECTED_MESSAGE = "question must not be empty or consist of only whitespace";
        assertThrows(IllegalArgumentException.class, () -> ui.printQuestion(" ", answers));

        try{
            ui.printQuestion(" ", answers);
        }catch(IllegalArgumentException npe){
            assertEquals(EXPECTED_MESSAGE, npe.getMessage());
        }
    }

    @Test
    void printQuestion_ANSWERS_NULL(){
        String EXPECTED_MESSAGE = "answers must not be null";
        assertThrows(NullPointerException.class, () -> ui.printQuestion(question, null));

        try{
            ui.printQuestion(question, null);
        }catch(NullPointerException npe){
            assertEquals(EXPECTED_MESSAGE, npe.getMessage());
        }
    }

    @Test
    void alertInvalidAnswerTest() throws Exception {
        String EXPECTED = """
                [1E[1;91m     x kann keine Antwort sein.\s
                     Deine Antwort muss A, B oder C lauten.[0m[u[K""";

        String terminalOutput = tapSystemOutNormalized(()->ui.alertInvalidAnswer("x"));
        assertEquals(EXPECTED, terminalOutput);
    }

    @Test
    void markChosenAnswerTest() throws Exception{
        String EXPECTED = """
                [H[2J[1;34m╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
                ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
                [0m[s[26E[1;34m╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
                ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝
                [0m[u[1E[1;34m                                ███████╗██████╗  █████╗  ██████╗ ███████╗
                                                ██╔════╝██╔══██╗██╔══██╗██╔════╝ ██╔════╝
                                                █████╗  ██████╔╝███████║██║  ███╗█████╗
                                                ██╔══╝  ██╔══██╗██╔══██║██║   ██║██╔══╝
                                                ██║     ██║  ██║██║  ██║╚██████╔╝███████╗
                                                ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝
                [0m[2E[2E[5C[1;94mQuestion 2: Some other question that is probably very easy...[0m
                [1E[5C[1;94mA: stupid answer one[0m
                [5C[1;94;103mB: rather strange, but correct one[0m
                [5C[1;94mC: most sensible, but wrong[0m
                """;

        String terminalOutput = tapSystemOutNormalized(()->ui.markChosenAnswer(question, answers, 1));
        assertEquals(EXPECTED, terminalOutput);
    }

    @Test
    void markChosenAnswerTest_NEGATIVE_QUESTION_NULL(){
        String EXPECTED_MESSAGE = "question must not be null";
        assertThrows(NullPointerException.class, () -> ui.markChosenAnswer(null, answers, 1));

        try{
            ui.markChosenAnswer(null, answers, 1);
        }catch(NullPointerException npe){
            assertEquals(EXPECTED_MESSAGE, npe.getMessage());
        }
    }

    @Test
    void markChosenAnswerTest_NEGATIVE_QUESTION_EMPTY(){
        String EXPECTED_MESSAGE = "question must not be empty or consist of only whitespace";
        assertThrows(IllegalArgumentException.class, () -> ui.markChosenAnswer(" ", answers, 1));

        try{
            ui.markChosenAnswer(" ", answers, 1);
        }catch(IllegalArgumentException npe){
            assertEquals(EXPECTED_MESSAGE, npe.getMessage());
        }
    }

    @Test
    void markChosenAnswerTest_NEGATIVE_ANSWERS_NULL(){
        String EXPECTED_MESSAGE = "answers must not be null";
        assertThrows(NullPointerException.class, () -> ui.markChosenAnswer(question, null, 1));

        try{
            ui.markChosenAnswer(question, null, 1);
        }catch(NullPointerException npe){
            assertEquals(EXPECTED_MESSAGE, npe.getMessage());
        }
    }

    @Test
    void markChosenAnswerTest_Negative_ChosenAnswer(){
        String EXPECTED = "chosenAnswer must be 0, 1 or 2";

        assertThrows(IllegalArgumentException.class, () -> ui.markChosenAnswer(question, answers,-1));
        assertThrows(IllegalArgumentException.class, () -> ui.markChosenAnswer(question, answers,4));

        try{
            ui.markChosenAnswer(question, answers,-1);
        }catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }

        try{
            ui.markChosenAnswer(question, answers, 4);
        }catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }
    }

    @Test
    void markCorrectAndChosenAnswerTest_DIFFERENT_CORRECTANDCHOSEN() throws Exception{
        String EXPECTED = """
                [H[2J[1;34m╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
                ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
                [0m[s[26E[1;34m╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
                ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝
                [0m[u[1E[1;34m                                ███████╗██████╗  █████╗  ██████╗ ███████╗
                                                ██╔════╝██╔══██╗██╔══██╗██╔════╝ ██╔════╝
                                                █████╗  ██████╔╝███████║██║  ███╗█████╗
                                                ██╔══╝  ██╔══██╗██╔══██║██║   ██║██╔══╝
                                                ██║     ██║  ██║██║  ██║╚██████╔╝███████╗
                                                ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝
                [0m[2E[2E[5C[1;94mQuestion 2: Some other question that is probably very easy...[0m
                [1E[5C[1;94mA: stupid answer one[0m
                [5C[1;94;103mB: rather strange, but correct one[0m
                [5C[1;94;102mC: most sensible, but wrong[0m
                """;

        String terminalOutput = tapSystemOutNormalized(()->ui.markCorrectAndChosenAnswer(question, answers, 1, 2));
        assertEquals(EXPECTED, terminalOutput);
    }

    @Test
    void markCorrectAndChosenAnswerTest_SAME_CORRECTANDCHOSEN() throws Exception{
        String EXPECTED = """
                [H[2J[1;34m╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
                ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
                [0m[s[26E[1;34m╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
                ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝
                [0m[u[1E[1;34m                                ███████╗██████╗  █████╗  ██████╗ ███████╗
                                                ██╔════╝██╔══██╗██╔══██╗██╔════╝ ██╔════╝
                                                █████╗  ██████╔╝███████║██║  ███╗█████╗
                                                ██╔══╝  ██╔══██╗██╔══██║██║   ██║██╔══╝
                                                ██║     ██║  ██║██║  ██║╚██████╔╝███████╗
                                                ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝
                [0m[2E[2E[5C[1;94mQuestion 2: Some other question that is probably very easy...[0m
                [1E[5C[1;94mA: stupid answer one[0m
                [5C[1;94;102mB: rather strange, but correct one[0m
                [5C[1;94mC: most sensible, but wrong[0m
                """;

        String terminalOutput = tapSystemOutNormalized(()->ui.markCorrectAndChosenAnswer(question, answers, 1, 1));
        assertEquals(EXPECTED, terminalOutput);
    }

    @Test
    void markCorrectAndChosenAnswerTest_NEGATIVE_QUESTION_NULL(){
        String EXPECTED_MESSAGE = "question must not be null";
        assertThrows(NullPointerException.class, () -> ui.markCorrectAndChosenAnswer(null, answers, 1, 2));

        try{
            ui.markCorrectAndChosenAnswer(null, answers, 1,2);
        }catch(NullPointerException npe){
            assertEquals(EXPECTED_MESSAGE, npe.getMessage());
        }
    }

    @Test
    void markCorrectAndChosenAnswerTest_NEGATIVE_QUESTION_EMPTY(){
        String EXPECTED_MESSAGE = "question must not be empty or consist of only whitespace";
        assertThrows(IllegalArgumentException.class, () -> ui.markCorrectAndChosenAnswer(" ", answers, 1,2));

        try{
            ui.markCorrectAndChosenAnswer(" ", answers, 1,2);
        }catch(IllegalArgumentException npe){
            assertEquals(EXPECTED_MESSAGE, npe.getMessage());
        }
    }

    @Test
    void markCorrectAndChosenAnswerTest_NEGATIVE_ANSWERS_NULL(){
        String EXPECTED_MESSAGE = "answers must not be null";
        assertThrows(NullPointerException.class, () -> ui.markCorrectAndChosenAnswer(question, null, 1,2));

        try{
            ui.markCorrectAndChosenAnswer(question, null, 1,2);
        }catch(NullPointerException npe){
            assertEquals(EXPECTED_MESSAGE, npe.getMessage());
        }
    }

    @Test
    void markCorrectAndChosenAnswerTest_Negative_ChosenAnswer(){
        String EXPECTED = "correctAnswer must be 0, 1 or 2";

        assertThrows(IllegalArgumentException.class, () -> ui.markCorrectAndChosenAnswer(question, answers,1, -1));
        assertThrows(IllegalArgumentException.class, () -> ui.markCorrectAndChosenAnswer(question, answers,1, 4));

        try{
            ui.markCorrectAndChosenAnswer(question, answers,1, -1);
        }catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }

        try{
            ui.markCorrectAndChosenAnswer(question, answers, 1, 4);
        }catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED, iAEx.getMessage());
        }
    }

    @Test
    void askForAnswerTest() throws Exception {
        String EXPECTED = """
                [2E[1;34m     Tippe deine Antwort ein: [0m[s""";

        String terminalOutput = tapSystemOutNormalized(()->ui.askForAnswer());
        assertEquals(EXPECTED, terminalOutput);
    }

    @Test
    void printPlayerHasWonTest() throws Exception {
        String EXPECTED = """
                [2E[1;92m     Super! Du hast diese Runde gewonnen![0m""";

        String terminalOutput = tapSystemOutNormalized(()->ui.printPlayerHasWon());
        assertEquals(EXPECTED, terminalOutput);
    }

    @Test
    void printPlayerOnlyWasCorrectTest() throws Exception {
        String EXPECTED = """
                [2E[1;33m     Schade. Deine Antwort war korrekt, aber Player1 war schneller[0m""";

        String terminalOutput = tapSystemOutNormalized(()->ui.printPlayerOnlyWasCorrect("Player1"));
        assertEquals(EXPECTED, terminalOutput);
    }

    @Test
    void printPlayerOnlyWasCorrectTest_NEGATIVE_PLAYERNULL(){
        assertThrows(NullPointerException.class, () -> ui.printPlayerOnlyWasCorrect(null));
        String EXPECTED_MESSAGE = "winningPlayer must not be null";

        try{
            ui.printPlayerOnlyWasCorrect(null);
        } catch(NullPointerException npe){
            assertEquals(EXPECTED_MESSAGE, npe.getMessage());
        }
    }

    @Test
    void printPlayerOnlyWasCorrectTest_NEGATIVE_PLAYEREMPTY(){
        assertThrows(IllegalArgumentException.class, () -> ui.printPlayerOnlyWasCorrect(" "));
        String EXPECTED_MESSAGE = "winningPlayer must not be empty or consist of only whitespace";

        try{
            ui.printPlayerOnlyWasCorrect(" ");
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED_MESSAGE, iAEx.getMessage());
        }
    }

    @Test
    void printPlayerWasWrongTest() throws Exception {
        String EXPECTED = """
                [2E[1;31m     Deine Antwort war falsch. Player1 hat gewonnen.[0m""";

        String terminalOutput = tapSystemOutNormalized(()->ui.printPlayerWasWrong("Player1"));
        assertEquals(EXPECTED, terminalOutput);
    }

    @Test
    void printPlayerWasWrongTest_NEGATIVE_PLAYERNULL(){
        assertThrows(NullPointerException.class, () -> ui.printPlayerWasWrong(null));
        String EXPECTED_MESSAGE = "winningPlayer must not be null";

        try{
            ui.printPlayerWasWrong(null);
        } catch(NullPointerException npe){
            assertEquals(EXPECTED_MESSAGE, npe.getMessage());
        }
    }

    @Test
    void printPlayerWasWrongTest_NEGATIVE_PLAYEREMPTY(){
        assertThrows(IllegalArgumentException.class, () -> ui.printPlayerWasWrong(" "));
        String EXPECTED_MESSAGE = "winningPlayer must not be empty or consist of only whitespace";

        try{
            ui.printPlayerWasWrong(" ");
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED_MESSAGE, iAEx.getMessage());
        }
    }

    @Test
    void printNooneCorrectTest() throws Exception {
        String EXPECTED = """
                [2E[1;31m     Niemand wusste die richtige Antwort.[0m""";

        String terminalOutput = tapSystemOutNormalized(()->ui.printNooneCorrect());
        assertEquals(EXPECTED, terminalOutput);
    }

    @Test
    void printScoreboardTest() throws Exception {
        String EXPECTED = """
                [H[2J[1;34m╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
                ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
                [0m[s[26E[1;34m╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
                ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝
                [0m[u[1E[1;34m                                ███████╗ ██████╗ ██████╗ ██████╗ ███████╗
                                                ██╔════╝██╔════╝██╔═══██╗██╔══██╗██╔════╝
                                                ███████╗██║     ██║   ██║██████╔╝█████╗
                                                ╚════██║██║     ██║   ██║██╔══██╗██╔══╝
                                                ███████║╚██████╗╚██████╔╝██║  ██║███████╗
                                                ╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
                [0m[2E[1E[1;34m                                       ╔═════╦══════════════╦═════╗
                                                       ║ 1.  ║ Player1      ║ 950 ║
                                                       ║ 2.  ║ Player2      ║ 900 ║
                                                       ║ 3.  ║ Player3      ║ 800 ║
                                                       ║ 4.  ║ Player4      ║ 700 ║
                                                       ║ 5.  ║ Player5      ║ 600 ║
                                                       ║ 6.  ║ Player6      ║ 500 ║
                                                       ║ 7.  ║ Player7      ║ 400 ║
                                                       ║ 8.  ║ Player8      ║ 300 ║
                                                       ║ 9.  ║ Player9      ║ 200 ║
                                                       ║ 10. ║ Player10     ║ 100 ║
                                                       ║ 12. ║ Player12     ║ 80  ║
                                                       ╚═════╩══════════════╩═════╝
                [0m""";

        String terminalOutput = tapSystemOutNormalized(()->ui.printScoreboard(scoresLong, "Player12"));
        assertEquals(EXPECTED, terminalOutput);
    }

    @Test
    void printScoreboardTest_NEGATIVE_NAMENULL(){
        assertThrows(NullPointerException.class, () -> ui.printScoreboard(scoresLong, null));
        String EXPECTED_MESSAGE = "name must not be null";

        try{
            ui.printScoreboard(scoresLong, null);
        } catch(NullPointerException npe){
            assertEquals(EXPECTED_MESSAGE, npe.getMessage());
        }
    }

    @Test
    void printScoreboardTest_NEGATIVE_SCOREBOARDENTRIESNULL(){
        assertThrows(NullPointerException.class, () -> ui.printScoreboard(null, "Player"));
        String EXPECTED_MESSAGE = "scoreboardEntries must not be null";

        try{
            ui.printScoreboard(null, "Player");
        } catch(NullPointerException npe){
            assertEquals(EXPECTED_MESSAGE, npe.getMessage());
        }
    }

    @Test
    void printScoreboardTest_NEGATIVE_NAMEEMPTY(){
        assertThrows(IllegalArgumentException.class, () -> ui.printScoreboard(scoresLong, " "));
        String EXPECTED_MESSAGE = "name must not be empty or consist of only whitespace";

        try{
            ui.printScoreboard(scoresLong, " ");
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED_MESSAGE, iAEx.getMessage());
        }
    }

    @Test
    void printEndTest() throws Exception {
        String EXPECTED = """
                [H[2J[1;34m╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
                ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
                [0m[s[26E[1;34m╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
                ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝
                [0m[u[1E[6E[1;34m                                   ███████╗███╗   ██╗██████╗ ███████╗
                                                   ██╔════╝████╗  ██║██╔══██╗██╔════╝
                                                   █████╗  ██╔██╗ ██║██║  ██║█████╗
                                                   ██╔══╝  ██║╚██╗██║██║  ██║██╔══╝
                                                   ███████╗██║ ╚████║██████╔╝███████╗
                                                   ╚══════╝╚═╝  ╚═══╝╚═════╝ ╚══════╝
                [0m[2E[1;34m                           Danke für die Teilnahme an diesem Spiel ! ~N.S.F.[0m[H[2J""";

        String terminalOutput = tapSystemOutNormalized(()->ui.printEnd());
        assertEquals(EXPECTED, terminalOutput);
    }

    @Test
    void printErrorScreenTest() throws Exception{
        String EXPECTED = """
                [H[2J[1;31m╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗
                ╚███████████████████████████████████████████████████████████████████████████████████████████████████████╝
                [0m[s[26E[1;31m╔███████████████████████████████████████████████████████████████████████████████████████████████████████╗
                ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝
                [0m[u[6E[1;31m                                ███████╗██████╗ ██████╗  ██████╗ ██████╗
                                                ██╔════╝██╔══██╗██╔══██╗██╔═══██╗██╔══██╗
                                                █████╗  ██████╔╝██████╔╝██║   ██║██████╔╝
                                                ██╔══╝  ██╔══██╗██╔══██╗██║   ██║██╔══██╗
                                                ███████╗██║  ██║██║  ██║╚██████╔╝██║  ██║
                                                ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═╝
                [0m[4E[1;31m                                    Ups! Etwas ist schief gelaufen :(
                                                         Das Spiel wird beendet.
                                
                                 Untersuche bitte das Log-File unter "C:Windows/temp/FacadeQuiz/<datum>.log
                                              (Für Linux/ MAC: /var/tmp/FacadeQuiz/<datum>.log)
                [0m[H[2J""";

        String terminalOutput = tapSystemOutNormalized(()->ui.printErrorScreen());
        assertEquals(EXPECTED, terminalOutput);
    }

    @Test
    void waitingTest_REASON_NULL(){
        assertThrows(NullPointerException.class, () -> ui.waiting(null));
        String EXPECTED_MESSAGE = "reason must not be null";

        try{
            ui.waiting(null);
        } catch(NullPointerException npe){
            assertEquals(EXPECTED_MESSAGE, npe.getMessage());
        }
    }

    @Test
    void waitingTest_REASON_EMPTY(){
        assertThrows(IllegalArgumentException.class, () -> ui.waiting(" "));
        String EXPECTED_MESSAGE = "reason must not be empty or consist of only whitespace";

        try{
            ui.waiting(" ");
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED_MESSAGE, iAEx.getMessage());
        }
    }

    @Test
    void sleepSaveTest_NEGATIVE_MILLIS(){
        assertThrows(IllegalArgumentException.class, () -> ui.sleepSave(-1));
        String EXPECTED_MESSAGE = "millis must be greater than zero";

        try{
            ui.sleepSave(-1);
        } catch(IllegalArgumentException iAEx){
            assertEquals(EXPECTED_MESSAGE, iAEx.getMessage());
        }
    }

    @Test
    void proceedTest(){
        ui.waiting("Test");
        ui.proceed();

        assertFalse(ui.isWaiting());
    }

    @Test
    void isWaitingTest(){
        ui.proceed();
        ui.waiting("Test");

        assertTrue(ui.isWaiting());
    }
}