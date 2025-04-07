package view;

import javax.swing.*;

public class QuizPanel {
    private JFrame quizFrame;
    private String chosenModule;

    public void panelOpened(String chosenModule) {
        this.chosenModule = chosenModule;
        JFrame quizFrame = new JFrame("Quiz Options");
        quizFrame.setSize(300, 200);
        quizFrame.setLocationRelativeTo(null);
        quizFrame.add(new JLabel("List of quizzes goes here...", SwingConstants.CENTER));
        quizFrame.setVisible(true);
    }
}
