package view.subPanels.Quiz;

import model.Course;
import model.Module;
import model.Quiz;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HistoryPanel extends JPanel {
    private DefaultListModel<String> quizModel;
    private MainFrame mainFrame;
    private MainQuizFrame mainQuizFrame;
    private List<Quiz> historyList;
    private JButton resultButton;
    private Quiz testQuiz;
    private JList displayList;

    public HistoryPanel(MainFrame mainFrame, MainQuizFrame mainQuizFrame) {
        this.mainFrame = mainFrame;
        this.mainQuizFrame = mainQuizFrame;
        historyList = new ArrayList<>();

        setLayout();
        createLists();
        createButtonPanel();
        addActionListener();
    }

    public void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        resultButton = new JButton("Show result");
        buttonPanel.add(resultButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void updateList() {
        quizModel.clear();
        historyList = mainFrame.getUsersHistoryQuizzes();
        for (Quiz quiz : historyList) {
            quizModel.addElement(quiz.getDate() + " " + quiz.getName());
        }
        revalidate();
        repaint();
    }

    public void setLayout () {
        setLayout(new BorderLayout());
        JLabel northLabel = new JLabel("History of completed quizzes");
        northLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(northLabel, BorderLayout.NORTH);
    }

    public void createLists() {
        quizModel = new DefaultListModel<>();
        displayList = new JList(quizModel);
        displayList.setBorder(BorderFactory.createLineBorder(Color.black));
        updateList();

        add(displayList, BorderLayout.CENTER);
    }

    public void addActionListener() {
        resultButton.addActionListener(e -> {
            for (Quiz quiz : historyList) {
                System.out.println("Found quiz: " + quiz.getName());
                System.out.println("Selected quiz" + displayList.getSelectedValue());
                if (displayList.getSelectedValue() == quiz.getName()) {
                    new QuestionFrame(mainFrame, quiz.getQuestions(), quiz, mainQuizFrame, 0, this, true);
                }
            }
        });
    }
}
