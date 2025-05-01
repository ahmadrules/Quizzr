package view.subPanels.Quiz;

import model.Quiz;

import javax.swing.*;
import java.awt.*;

public class QuizOptionsPanel extends JPanel {

    private JPanel namePanel;
    private JPanel amountPanel;
    private JPanel timerPanel;
    private JPanel correctionPanel;
    private JButton generateButton;
    private String quizName;
    private String amountOfQuestions;
    private String timerMinutes;
    private JTextField nameField;
    private JComboBox<String> amountBox;
    private JComboBox<String> timerBox;

    public QuizOptionsPanel() {
        setupLayout();
        createCenterPanel();
    }

    public void setupLayout() {
        setLayout(new BorderLayout());
        generateButton = new JButton("Generate");
        add(generateButton, BorderLayout.SOUTH);
    }

    public String[] getChosenOptions() {
        String[] infoString = new String[3];
        infoString[0] = quizName;
        infoString[1] = amountOfQuestions;
        infoString[2] = timerMinutes;
        return infoString;
    }

    public void createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        createCenterLinePanels();

        centerPanel.add(namePanel);
        centerPanel.add(amountPanel);
        centerPanel.add(timerPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    public void createCenterLinePanels() {
        namePanel = new JPanel(new FlowLayout());
        amountPanel = new JPanel(new FlowLayout());
        timerPanel = new JPanel(new FlowLayout());
        correctionPanel = new JPanel(new FlowLayout());

        JLabel nameLabel = new JLabel("Quiz name:");
        nameField = new JTextField(10);
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

        for (int i = 5; i < 16; i++) {
            timerBox.addItem(Integer.toString(i));
        }

        timerPanel.add(timerLabel);
        timerPanel.add(timerBox);
    }

    private void addListeners() {
        generateButton.addActionListener(e -> {

            timerMinutes = (String) timerBox.getSelectedItem();
            amountOfQuestions = (String) amountBox.getSelectedItem();
            if (nameField.getText() != null) {
               quizName = nameField.getText();
           }
        });
    }
}
