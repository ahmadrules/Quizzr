package view.subPanels.Quiz;

import model.Quiz;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;

public class AvailableQuizPanel extends JPanel {
    private MainQuizFrame mainQuizFrame;
    private MainFrame mainFrame;

    private DefaultListModel<String> quizListModel;
    private JList<String> availableQuizList;
    private JPanel buttonPanel;
    private JButton startQuizButton;
    private JButton generateButton;
    private String selectedQuiz;

    public AvailableQuizPanel(MainQuizFrame mainQuizFrame, MainFrame mainFrame) {
        this.mainQuizFrame = mainQuizFrame;
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        setNorthLabel();
        createList();
        updateList();
        createButtonPanel();
        addActionListeners();
    }

    public void updateList() {
        quizListModel.clear();


        for (Quiz quiz : mainFrame.getUsersAvailableQuizes())
        {
            System.out.println(quiz.getName());
            quizListModel.addElement(quiz.getName());
        }
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
