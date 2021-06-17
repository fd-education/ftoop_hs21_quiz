package ch.ffhs.quiz.questions;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionFactoryTest {

    @Test
    void questionBuilderTest() {
        String filename = "testQuestions/test_questions_2.txt";
        List<Question> quizTest = QuestionFactory.questionBuilder(filename);

        List<Answer> answerList1 = new ArrayList<>(Arrays.asList(
                new AnswerImpl("A 3", false),
                new AnswerImpl("B 2", true),
                new AnswerImpl("C 1", false)));

        List<Answer> answerList2 = new ArrayList<>(Arrays.asList(
                new AnswerImpl("A Albert", true),
                new AnswerImpl("B Kurt", false),
                new AnswerImpl("C Henry", false)));

        List<Answer> answerList3 = new ArrayList<>(Arrays.asList(
                new AnswerImpl("A Lausanne", false),
                new AnswerImpl("B Zürich", false),
                new AnswerImpl("C Bern", true)));


        List<Question> questionList = new ArrayList<>(Arrays.asList(
                new QuestionImpl("Was ist die Wurzel von 4?", answerList1),
                new QuestionImpl("Was ist die Hauptstadt der Schweiz?", answerList3),
                new QuestionImpl("Wie ist der Vorname von Einstein?", answerList2)
        ));
        int index = 0;
        for (Question question : questionList) {
            assertEquals(question.getQuestionText(), quizTest.get(index).getQuestionText());
            for (int i = 0; i < question.getAnswers().size(); i++) {
                assertEquals(question.getAnswers().get(i).getAnswer(), questionList.get(index).getAnswers().get(i).getAnswer());
            }
            index++;
        }

    }


}