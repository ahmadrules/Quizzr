package view.subPanels.Quiz;

import model.Quiz;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class is responsible for displaying the current available quiz the user can complete
 * It is also responsible for giving the user an option to generate a new quiz
 * @author Ahmad Maarouf
 */
public class AvailableQuizPanel extends JPanel {
    private MainQuizFrame mainQuizFrame;

    private DefaultListModel<String> quizListModel;
    private JList<String> availableQuizList;
    private JPanel buttonPanel;
    private JButton startQuizButton;
    private JButton generateButton;
    private JButton deleteButton;
    private String selectedQuiz;
    private JButton clearButton;

    public AvailableQuizPanel(MainQuizFrame mainQuizFrame) {
        this.mainQuizFrame = mainQuizFrame;
        setLayout(new BorderLayout());

        setNorthLabel();
        createList();
        updateList();
        createButtonPanel();
        addActionListeners();
    }

    /**
     * Disables buttons that affect a selected quiz
     * Used when no quiz is selected
     * @author Ahmad Maarouf
     */
    public void disableButtons() {
        startQuizButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    /**
     * Updates the list of available quiz by fetching it from MainFrame
     * @author Ahmad Maarouf
     */
    public void updateList() {
        quizListModel.clear();
        List<String> relatedQuizNames = mainQuizFrame.getRelatedQuizNames();

        for (String quizName : relatedQuizNames) {
            System.out.println(quizName);
            if (quizName != null && !quizName.isEmpty()) {
                quizListModel.addElement(quizName);
            }
        }
        revalidate();
        repaint();
    }

    /**
     * Sets a title for the list
     * @author Ahmad Maarouf
     */
    public void setNorthLabel() {
        JLabel quizLabel = new JLabel("Available quiz", SwingConstants.CENTER);
        add(quizLabel, BorderLayout.NORTH);
    }

    /**
     * Creates the list component for displaying the names of available quizzes
     * @author Ahmad Maarouf
     */
    public void createList() {
        quizListModel = new DefaultListModel<>();
        availableQuizList = new JList<>(quizListModel);
        JScrollPane scrollPane = new JScrollPane(availableQuizList);

        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Creates the button panel related to the list of available quiz
     * @author Ahmad Maarouf
     */
    public void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        startQuizButton = new JButton("Start quiz");
        generateButton = new JButton("Generate quiz");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Delete all quiz");

        startQuizButton.setEnabled(false);
        deleteButton.setEnabled(false);

        buttonPanel.add(startQuizButton);
        buttonPanel.add(generateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Adds action listeners to the buttons and a list listener for the list
     * @author Ahmad Maarouf
     */
    public void addActionListeners() {
        generateButton.addActionListener(e -> {
            new QuizOptionsFrame(mainQuizFrame);
        });

        availableQuizList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (availableQuizList.getSelectedValue() != null) {
                    startQuizButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                    selectedQuiz = availableQuizList.getSelectedValue();
                }
            }
        });

        startQuizButton.addActionListener(e -> {
            mainQuizFrame.startQuiz(selectedQuiz);
        });

        deleteButton.addActionListener(e -> {
           mainQuizFrame.deleteQuiz(selectedQuiz);
           startQuizButton.setEnabled(false);
           deleteButton.setEnabled(false);
        });

        clearButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                mainQuizFrame.clearCreatedQuiz();
            }
        });
    }
}
