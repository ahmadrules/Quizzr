package view.subPanels.Quiz;

import javax.swing.*;
import java.awt.*;

public class QuizOptionsFrame extends JFrame {

    private MainQuizFrame mainQuizFrame;
    private JPanel typePanel;
    private JPanel namePanel;
    private JPanel amountPanel;
    private JPanel timerPanel;
    private JPanel correctionPanel;
    private JButton generateButton;
    private String quizName;
    private String amountOfQuestions;
    private JTextField nameField;
    private JComboBox<String> amountBox;
    private JComboBox<String> timerBox;
    private JComboBox<String> typeBox;

    public QuizOptionsFrame(MainQuizFrame mainQuizFrame) {
        this.mainQuizFrame = mainQuizFrame;
        setupLayout();
        createCenterPanel();
        addListeners();

        setSize(220, 220);
        pack();
        setLocationRelativeTo(mainQuizFrame);
        setVisible(true);
    }

    public void setupLayout() {
        setLayout(new BorderLayout());
        generateButton = new JButton("Generate");
        add(generateButton, BorderLayout.SOUTH);
    }

    public void createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        createCenterLinePanels();

        centerPanel.add(typePanel);
        centerPanel.add(namePanel);
        centerPanel.add(amountPanel);
        centerPanel.add(timerPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    public void createCenterLinePanels() {
        typePanel = new JPanel(new FlowLayout());
        namePanel = new JPanel(new FlowLayout());
        amountPanel = new JPanel(new FlowLayout());
        timerPanel = new JPanel(new FlowLayout());
        correctionPanel = new JPanel(new FlowLayout());

        JLabel typeLabel = new JLabel("Type of quiz:");
        typeBox = new JComboBox<>();

        typeBox.addItem("Multiple choice");
        typeBox.addItem("True/False");
        typeBox.addItem("Matching");

        typePanel.add(typeLabel);
        typePanel.add(typeBox);

        JLabel nameLabel = new JLabel("Quiz name:");
        nameField = new JTextField(5);
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        JLabel amountLabel = new JLabel("Amount of questions:");
        amountBox = new JComboBox<>();

        for (int i = 5; i < 31; i++) {
            amountBox.addItem(Integer.toString(i));
        }

        amountPanel.add(amountLabel);
        amountPanel.add(amountBox);

        JLabel timerLabel = new JLabel("Timer(minutes):");
        timerBox = new JComboBox<>();

        timerBox.addItem("No timer");

        for (int i = 1; i < 16; i++) {
            timerBox.addItem(Integer.toString(i));
        }

        timerPanel.add(timerLabel);
        timerPanel.add(timerBox);
    }

    private void addListeners() {
        generateButton.addActionListener(e -> {

            long timerSeconds = 0;

            if (timerBox.getSelectedItem() != "No timer") {
                timerSeconds = Integer.parseInt((String) timerBox.getSelectedItem()) * 60L;
            }

            amountOfQuestions = (String) amountBox.getSelectedItem();
            if (nameField.getText() != null) {
               quizName = nameField.getText();
           }

            String typeOfQuiz = (String) typeBox.getSelectedItem();

            mainQuizFrame.generateQuiz(Integer.parseInt(amountOfQuestions), quizName, typeOfQuiz, timerSeconds);

            getContentPane().removeAll();
            setVisible(false);
        });
    }
}
