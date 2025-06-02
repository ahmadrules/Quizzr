package view.subPanels.Quiz;

import model.Quiz;
import view.main.MainFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;

/**
 * This class is responsible for displaying a list of quiz completed by the user
 * It is also responsible for giving the user an option to view the result of a completed quiz
 *
 * @author Ahmad Maarouf
 */
public class HistoryPanel extends JPanel {
    private DefaultListModel<String> quizModel;
    private String selectedQuiz;
    private MainQuizFrame mainQuizFrame;
    private JButton resultButton;
    private JButton clearButton;
    private JList<String> displayList;

    public HistoryPanel(MainQuizFrame mainQuizFrame) {
        this.mainQuizFrame = mainQuizFrame;


        setLayout();
        createLists();
        createButtonPanel();
        disableButtons();
        addActionListener();
    }

    /**
     * Creates the button panel
     *
     * @author Ahmad Maarouf
     */
    public void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 249, 163));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        resultButton = createStyledButton("Show result");
        clearButton = createStyledButton("Clear history");
        buttonPanel.add(resultButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 35));
        button.setMaximumSize(new Dimension(100, 35));
        button.setMinimumSize(new Dimension(100, 35));

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial",Font.BOLD,13));

        Color baseColor = new Color(52, 69,140);
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
     * Disables buttons that affect a selected quiz
     * Used if no quiz is selected
     *
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
     *
     * @author Ahmad Maarouf
     */
    public void setLayout() {
        setLayout(new BorderLayout());
        JLabel northLabel = new JLabel("History of completed quizzes");
        northLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        northLabel.setOpaque(true);
        northLabel.setBackground(new Color(255, 249, 163));
        northLabel.setFont(new Font("Georgia", Font.BOLD, 20));
        northLabel.setForeground(new Color(25, 25, 70));
        northLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(northLabel, BorderLayout.NORTH);
    }

    /**
     * Creates the list component used to display completed quiz
     *
     * @author Ahmad Maarouf
     */
    public void createLists() {
        quizModel = new DefaultListModel<>();
        displayList = new JList(quizModel);
        displayList.setFont(new Font("Montserrat", Font.PLAIN, 16));
        displayList.setBorder(BorderFactory.createLineBorder(Color.black));
        JScrollPane scrollPane = new JScrollPane(displayList);
        updateList();

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Adds action listeners to the buttons and a list listener to the list
     *
     * @author Ahmad Maarouf
     */
    public void addActionListener() {
        resultButton.addActionListener(e -> {
            Quiz quiz = mainQuizFrame.findHistoryQuiz(selectedQuiz);
            new QuestionFrame(quiz, mainQuizFrame, true);
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
            mainQuizFrame.clearHistory();
            updateList();
        });
    }
}
