package view.subPanels.Quiz;

import javax.swing.*;
import java.awt.*;

public class AvailableQuizPanel extends JPanel {
    private MainQuizFrame mainQuizFrame;

    private DefaultListModel<String> quizListModel;
    private JList<String> availableQuizList;
    private JPanel buttonPanel;
    private JButton startQuizButton;
    private JButton generateButton;
    private String selectedQuiz;

    public AvailableQuizPanel(MainQuizFrame mainQuizFrame) {
        this.mainQuizFrame = mainQuizFrame;
        setLayout(new BorderLayout());

        setNorthLabel();
        createList();
        createButtonPanel();
        addActionListeners();
    }

    public void updateList(String quizName) {
        quizListModel.addElement(quizName);
        revalidate();
        repaint();
    }

    public void setNorthLabel() {
        JLabel quizLabel = new JLabel("Available quiz", SwingConstants.CENTER);
        add(quizLabel, BorderLayout.NORTH);
    }

    public void createList() {
        quizListModel = new DefaultListModel<>();
        availableQuizList = new JList(quizListModel);
        JScrollPane scrollPane = new JScrollPane(availableQuizList);

        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        startQuizButton = new JButton("Start quiz");
        generateButton = new JButton("Generate quiz");

        startQuizButton.setEnabled(false);

        buttonPanel.add(startQuizButton);
        buttonPanel.add(generateButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void addActionListeners() {
        generateButton.addActionListener(e -> {
            new QuizOptionsFrame(mainQuizFrame);
        });

        availableQuizList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (availableQuizList.getSelectedValue() != null) {
                    startQuizButton.setEnabled(true);
                    selectedQuiz = availableQuizList.getSelectedValue();
                }
            }
        });

        startQuizButton.addActionListener(e -> {
            mainQuizFrame.startQuiz(selectedQuiz);
        });
    }
}
