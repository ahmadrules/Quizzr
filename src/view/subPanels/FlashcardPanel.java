package view.subPanels;

import controller.Controller;

import javax.swing.*;

public class QuizPanel {
    private JFrame quizFrame;
    private String selectedModuleName;
    private Controller controller;

    public QuizPanel(String selectedModuleName, Controller controller) {
        this.selectedModuleName = selectedModuleName;
        this.controller = controller;
        createFrame();
    }

    public void createFrame() {
        quizFrame = new JFrame("Quizzes for " + selectedModuleName);
        quizFrame.setSize(400, 300);
        quizFrame.setLocationRelativeTo(null);

        // Hämtar quiz-namn från controller
        String[] quizNames = controller.getQuizNamesForModule(selectedModuleName);

        if (quizNames == null || quizNames.length == 0) {
            quizFrame.add(new JLabel("No quizzes found for this module.", SwingConstants.CENTER));
        } else {
            // Visar quiz-namn i en lista
            JList<String> quizList = new JList<>(quizNames);
            quizList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(quizList);
            quizFrame.add(scrollPane);
        }

        quizFrame.setVisible(true);
    }
}
