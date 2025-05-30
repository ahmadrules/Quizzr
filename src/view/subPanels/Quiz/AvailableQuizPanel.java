package view.subPanels.Quiz;

import model.Quiz;
import view.main.MainFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;

/**
 * This class is responsible for displaying the current available quiz the user can complete
 * It is also responsible for giving the user an option to generate a new quiz
 * @author Ahmad Maarouf
 */
public class AvailableQuizPanel extends JPanel {
    private MainQuizFrame mainQuizFrame;
    private MainFrame mainFrame;

    private DefaultListModel<String> quizListModel;
    private JList<String> availableQuizList;
    private JPanel buttonPanel;
    private JButton startQuizButton;
    private JButton generateButton;
    private JButton deleteButton;
    private String selectedQuiz;
    private JButton clearButton;
    private JButton goBackButton;

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
        quizLabel.setOpaque(true);
        quizLabel.setForeground(new Color(25, 25, 70));
        quizLabel.setBackground(new Color(255, 249, 163));
        quizLabel.setFont(new Font("Georgia", Font.BOLD, 20));
        quizLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
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
        availableQuizList.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Creates the button panel related to the list of available quiz
     * @author Ahmad Maarouf
     */
    public void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        startQuizButton = createStyledButton("Start quiz");
        generateButton = createStyledButton("Generate quiz");
        deleteButton = createStyledButton("Delete");
        clearButton = createStyledButton("Delete all");
        goBackButton = createStyledButton("Go back");

        startQuizButton.setToolTipText("mark a quiz to start!");
        generateButton.setToolTipText("Click to generate your own quiz!");
        goBackButton.setToolTipText("Click to go back to main frame");

        startQuizButton.setEnabled(false);
        deleteButton.setEnabled(false);

        buttonPanel.add(startQuizButton);
        buttonPanel.add(generateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(goBackButton);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        buttonPanel.setBackground(new Color(255, 249, 163));
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 35));
        button.setMaximumSize(new Dimension(100, 35));
        button.setMinimumSize(new Dimension(100, 35));

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial",Font.BOLD,13));

        Color baseColor = new Color(25, 25, 70);
        Color haverColor = new Color(90, 140, 230);
        Color borderColor = baseColor.darker();

        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        Border lineBorder= BorderFactory.createLineBorder(borderColor,1);
        Border emptyBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        button.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
        return button;
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
        goBackButton.addActionListener(e -> {
            mainFrame.setVisible(true);
            SwingUtilities.getWindowAncestor(AvailableQuizPanel.this).dispose();
        });
    }
}
