package view.subPanels.Quiz;

import model.Quiz;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class is responsible for displaying a list of quiz completed by the user
 * It is also responsible for giving the user an option to view the result of a completed quiz
 * @author Ahmad Maarouf
 */
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

    /**
     * Creates the button panel
     * @author Ahmad Maarouf
     */
    public void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        resultButton = new JButton("Show result");
        clearButton = new JButton("Clear history");
        buttonPanel.add(resultButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Disables buttons that affect a selected quiz
     * Used if no quiz is selected
     * @author Ahmad Maarouf
     */
    public void disableButtons() {
        resultButton.setEnabled(false);
    }

    /**
     * Updates the list of completed quiz by fetching it from MainFrame
     */
    public void updateList() {
        quizModel.clear();
        List<String> nameList = mainQuizFrame.getRelatedHistoryQuizNames();

        for (String name : nameList) {
            quizModel.addElement(name);
        }
        revalidate();
        repaint();
    }

    /**
     * Sets the layout of the panel
     * @author Ahmad Maarouf
     */
    public void setLayout () {
        setLayout(new BorderLayout());
        JLabel northLabel = new JLabel("History of completed quizzes");
        northLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(northLabel, BorderLayout.NORTH);
    }

    /**
     * Creates the list component used to display completed quiz
     * @author Ahmad Maarouf
     */
    public void createLists() {
        quizModel = new DefaultListModel<>();
        displayList = new JList(quizModel);
        displayList.setBorder(BorderFactory.createLineBorder(Color.black));
        JScrollPane scrollPane = new JScrollPane(displayList);
        updateList();

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Adds action listeners to the buttons and a list listener to the list
     * @author Ahmad Maarouf
     */
    public void addActionListener() {
        resultButton.addActionListener(e -> {
            Quiz quiz = mainQuizFrame.findHistoryQuiz(selectedQuiz);
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
