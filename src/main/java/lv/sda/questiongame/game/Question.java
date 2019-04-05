package lv.sda.questiongame.game;

import lv.sda.questiongame.game.Answer;

import java.util.ArrayList;

public class Question {
    private String questionText;
    private ArrayList<Answer> answerList;

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public ArrayList<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(ArrayList<Answer> answerList) {
        this.answerList = answerList;
    }
}
