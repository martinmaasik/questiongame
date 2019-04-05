package lv.sda.questiongame.game;

import java.util.ArrayList;
import java.util.Random;

public class GenerateQuestions {
    public ArrayList<Question> generateQuestions() {
        ArrayList<Question> questionList = new ArrayList<Question>();
        //
        // QUESTION 1
        //
        Question question1 = new Question();
        Answer answer1a = new Answer();
        Answer answer1b = new Answer();
        Answer answer1c = new Answer();
        Answer answer1d = new Answer();

        question1.setQuestionText("How many millimetres are there in a metre?");

        answer1a.setText("1000");
        answer1a.setCorrect(true);
        answer1b.setText("10000");
        answer1b.setCorrect(false);
        answer1c.setText("10");
        answer1c.setCorrect(false);
        answer1d.setText("100");
        answer1d.setCorrect(false);

        ArrayList<Answer> question1AnswerList = new ArrayList<Answer>();
        question1AnswerList.add(answer1a);
        question1AnswerList.add(answer1b);
        question1AnswerList.add(answer1c);
        question1AnswerList.add(answer1d);

        question1.setAnswerList(question1AnswerList);
        //
        // QUESTION 2
        //
        Question question2 = new Question();
        Answer answer2a = new Answer();
        Answer answer2b = new Answer();
        Answer answer2c = new Answer();
        Answer answer2d = new Answer();

        question2.setQuestionText("What is my name?");

        answer2a.setText("Martin");
        answer2a.setCorrect(true);
        answer2b.setText("Joe");
        answer2b.setCorrect(false);
        answer2c.setText("Bob");
        answer2c.setCorrect(false);
        answer2d.setText("Jack");
        answer2d.setCorrect(false);

        ArrayList<Answer> question2AnswerList = new ArrayList<Answer>();
        question2AnswerList.add(answer2a);
        question2AnswerList.add(answer2b);
        question2AnswerList.add(answer2c);
        question2AnswerList.add(answer2d);

        question2.setAnswerList(question2AnswerList);
        ///
        ///
        questionList.add(question1);
        questionList.add(question2);
        return questionList;
    }

    public Question getRandomQuestion() {
        ArrayList<Question> questionList = generateQuestions();
        Random random = new Random();
        int randomIndex = random.nextInt(questionList.size());
        Question question = questionList.get(randomIndex);
        return question;
    }
}
