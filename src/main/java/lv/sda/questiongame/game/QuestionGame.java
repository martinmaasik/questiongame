package lv.sda.questiongame.game;

import lv.sda.questiongame.ui.QuestionGameUI;
import lv.sda.questiongame.ui.UIGenerator;

public class QuestionGame {
    private UIGenerator uiGenerator;

    public QuestionGame() {
        uiGenerator = new QuestionGameUI();
        uiGenerator.generate();
    }
}
