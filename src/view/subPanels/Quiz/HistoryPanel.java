package view.subPanels.Quiz;

import model.Quiz;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class HistoryPanel extends JPanel {
    private DefaultListModel<String> quizModel;
    private String selectedQuiz;
    private MainFrame mainFrame;
    private MainQuizFrame mainQuizFrame;
    private JButton resultButton;
    private JButton clearButton;
    private JList<String> displayList;

    public HistoryPanel(MainFrame mainFrame, MainQuizFrame mainQuizFrame) {
        this.mainFrame = mainFrame;
        this.mainQuizFrame = mainQuizFrame;

        setLayout();
        createLists();
        createButtonPanel();
        addActionListener();
    }

    public void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        resultButton = new JButton("Show result");
        clearButton = new JButton("Clear history");
        buttonPanel.add(resultButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void disableButtons() {
        resultButton.setEnabled(false);
    }

    public void updateList() {
        quizModel.clear();
        List<String> nameList = mainFrame.getHistoryQuizNames();

        for (String name : nameList) {
            quizModel.addElement(name);
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
        JScrollPane scrollPane = new JScrollPane(displayList);
        updateList();

        add(scrollPane, BorderLayout.CENTER);
    }

    public void addActionListener() {
        resultButton.addActionListener(e -> {
            Quiz quiz = mainFrame.findHistoryQuiz(selectedQuiz);
            new QuestionFrame(mainFrame, quiz.getQuestions(), quiz, mainQuizFrame, 0, true);
        });

        displayList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (displayList.getSelectedValue() != null) {
                    selectedQuiz = displayList.getSelectedValue();
                    resultButton.setEnabled(true);
                }
            }
        });

        clearButton.addActionListener(e -> {
           mainFrame.clearHistory();
           updateList();
        });
    }
}
