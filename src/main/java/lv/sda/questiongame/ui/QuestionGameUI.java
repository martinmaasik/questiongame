package lv.sda.questiongame.ui;

import lv.sda.questiongame.game.Answer;
import lv.sda.questiongame.game.GenerateQuestions;
import lv.sda.questiongame.game.Question;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static java.lang.Thread.sleep;

public class QuestionGameUI implements UIGenerator{
    private JPanel questionPanel;
    private JPanel answerPanel;
    private JLabel progressDisplay;
    private int progressIndex = 0;
    private JLabel messageToPlayer;
    private JProgressBar progressBar;
    private final int startingTime = 60;
    private volatile boolean timerIsRunning = true;

    public void generate() {
        JFrame frame = new JFrame();
        questionPanel = new JPanel();
        answerPanel = new JPanel();
        JPanel progressDisplayPanel = new JPanel();
        JPanel messagePanel = new JPanel();
        JPanel timerPanel = new JPanel();
        progressBar = new JProgressBar();
        progressDisplay = new JLabel();
        progressDisplay.setText("0/15 questions answered correctly");
        messageToPlayer = new JLabel();
        progressDisplayPanel.add(progressDisplay);
        progressDisplayPanel.add(progressBar);
        messagePanel.add(messageToPlayer);
        LayoutSetup(frame, progressDisplayPanel, messagePanel, timerPanel);
        displayNewQuestion();
        timerPanel = generateTimer();
        frame.add(timerPanel);
        frame.add(progressDisplayPanel);
        frame.add(questionPanel);
        frame.add(answerPanel);
        frame.add(messagePanel);
        frame.setVisible(true);
    }

    private JPanel generateTimer() {
        JLabel timerLabel = new JLabel();
        JPanel timerPanel = new JPanel();
        int endGameTime = 0;
        Thread t = new Thread(()->{
            int time = startingTime;
            try {
                while (timerIsRunning && time > endGameTime) {
                    time--;
                    timerLabel.setText("Timer: " + Integer.toString(time));
                    sleep(1000);
                }
                if (time == endGameTime) {
                    timerLabel.setText("You have run out of time!");
                    gameLost();
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        t.start();
        timerPanel.add(timerLabel);
        return timerPanel;
    }

    private void stopTimer() {
        timerIsRunning = false;
    }

    private void gameLost() {
        clearScreen();
        messageToPlayer.setText("Game Over! You Lost!");
    }

    private void gameWon() {
        clearScreen();
        stopTimer();
        messageToPlayer.setText("Congratulations! You won!");
    }

    private void clearScreen() {
        questionPanel.removeAll();
        answerPanel.removeAll();
        questionPanel.updateUI();
        answerPanel.updateUI();
    }

    private void LayoutSetup(JFrame frame, JPanel progressDisplayPanel, JPanel messagePanel, JPanel timerPanel) {
        int frameWidth = 1000;
        frame.setBounds(100, 100, frameWidth, 450);
        frame.setLayout(new FlowLayout());
        answerPanel.setLayout(new FlowLayout());
        questionPanel.setLayout(new FlowLayout());

        answerPanel.setPreferredSize(new Dimension(frameWidth,40));
        questionPanel.setPreferredSize(new Dimension(frameWidth,30));
        progressDisplayPanel.setPreferredSize(new Dimension(frameWidth,20));
        messagePanel.setPreferredSize(new Dimension(frameWidth,20));
        timerPanel.setPreferredSize(new Dimension(frameWidth,20));
    }

    private void displayNewQuestion() {
        if (questionPanel != null && answerPanel != null) {
            questionPanel.removeAll();
            answerPanel.removeAll();
        }
        GenerateQuestions questionGenerator = new GenerateQuestions();
        Question question = questionGenerator.getRandomQuestion();
        generateQuestionPanel(question);
        generateAnswerPanel(question);
        questionPanel.updateUI();
        answerPanel.updateUI();
    }

    private void generateQuestionPanel(Question question) {
        JLabel questionText = new JLabel();
        questionText.setText(question.getQuestionText());
        questionPanel.add(questionText);
    }

    private void generateAnswerPanel(Question question) {
        ArrayList<Answer> answerList = question.getAnswerList();
        ButtonGroup buttonGroup = new ButtonGroup();
        Map<JRadioButton,Answer> jRadioButtonAnswerMap = new HashMap<JRadioButton, Answer>();
        for (int i = 0; i < answerList.size(); i++) {
            JRadioButton answerRadioButton = new JRadioButton(answerList.get(i).getText());
            buttonGroup.add(answerRadioButton);
            jRadioButtonAnswerMap.put(answerRadioButton, answerList.get(i));
            answerPanel.add(answerRadioButton);
        }
        JButton submitAnswer = new JButton ("Submit answer");
        submitAnswer.addActionListener(a->{
            generateMessageToPlayer(jRadioButtonAnswerMap.get(getSelectedButtonText(buttonGroup)).isCorrect());
        });
        answerPanel.add(submitAnswer);
        if (answerList.size() > 2) {
            JButton fiftyFiftyButton = new JButton("50/50");
            fiftyFiftyButton.addActionListener(a->{
                eliminateTwoAnswers(question);
            });
            answerPanel.add(fiftyFiftyButton);
        }
    }

    private void eliminateTwoAnswers(Question question) {
        answerPanel.removeAll();
        answerPanel.updateUI();
        Answer correctAnswer = null;
        Answer randomWrongAnswer;
        ArrayList<Answer> answerListWithTwoAnswers = new ArrayList<Answer>();

        ArrayList<Answer> answerListWithFourAnswers = question.getAnswerList();
        ArrayList<Answer> listOfWrongAnswers = new ArrayList<Answer>();
        for (Answer answer : answerListWithFourAnswers) {
            if (answer.isCorrect()) {
                correctAnswer = answer;
            } else {
                listOfWrongAnswers.add(answer);
            }
        }
        Random r = new Random();
        int random = r.nextInt(3);
        randomWrongAnswer = listOfWrongAnswers.get(random);

        answerListWithTwoAnswers.add(correctAnswer);
        answerListWithTwoAnswers.add(randomWrongAnswer);

        question.setAnswerList(answerListWithTwoAnswers);
        generateAnswerPanel(question);
    }

    private void generateMessageToPlayer(boolean lastAnswer) {
        String message = "";
        if (lastAnswer) {
            message = "Congratulations! You answered correctly!";
            progressBar.setMaximum(15);
            progressIndex++;
            progressBar.setValue(progressIndex);
            progressDisplay.setText(progressIndex + "/15 questions answered correctly");
            if (progressIndex == 15) {
                gameWon();
                return;
            }
            displayNewQuestion();
        } else {
            message = "Sorry! The answer was wrong!";
        }
        messageToPlayer.setText(message);
    }

    private JRadioButton getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return (JRadioButton) button;
            }
        }
        return null;
    }
}
