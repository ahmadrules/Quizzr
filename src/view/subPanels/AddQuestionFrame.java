package view.subPanels;

import view.main.CenterPanels.CenterModulePanel;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class AddQuestionFrame extends JFrame {

    private CenterModulePanel centerPanel;
    private JComboBox<String> typeComboBox;
    private JPanel mainPanel;
    private JButton addButton;
    private JButton submitButton;
    private ArrayList<JTextField> tfStatements;
    private ArrayList<JComboBox> tfAnswers;
    private ArrayList<JTextField> multiAlternatives;
    private ArrayList<JTextField> multiQueries;
    private ArrayList<JComboBox<String>> multiCorrectBoxes;
    private ArrayList<JTextField> matchQueries;
    private ArrayList<JTextField> matchStatements;
    private ArrayList<JTextField> matchAnswers;


    public AddQuestionFrame(CenterModulePanel centerPanel) {
        this.centerPanel = centerPanel;
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(mainPanel);

        initiateLists();
        createTypePanel();
        createSubmitButton();
        addItemListeners();
        setLocationRelativeTo(centerPanel);

        add(scrollPane);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(getWidth() + 15, getHeight());
    }

    public void initiateLists() {
        multiQueries = new ArrayList<>();
        multiCorrectBoxes = new ArrayList<>();
        multiAlternatives = new ArrayList<>();

        tfStatements = new ArrayList<>();
        tfAnswers = new ArrayList<>();

        matchQueries = new ArrayList<>();
        matchStatements = new ArrayList<>();
        matchAnswers = new ArrayList<>();
    }

    public void createSubmitButton() {
        submitButton = new JButton("Submit questions");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setEnabled(false);
        mainPanel.add(submitButton);
    }

    public void collectQuestions() {

        if (tfStatements != null) {
            ArrayList<String> tfAlternatives = new ArrayList<>();
            tfAlternatives.add("True");
            tfAlternatives.add("False");

            for (JTextField textField : tfStatements) {
                if (textField.getText() !=null && !textField.getText().equals("")) {
                    String currentStatement = textField.getText();
                    String currentAnswer = (String) tfAnswers.getFirst().getSelectedItem();
                    tfAnswers.removeFirst();
                    centerPanel.saveTrueOrFalseQuestion(currentStatement, tfAlternatives, 3, currentAnswer);
                }

                else {
                    System.out.println("TF empty");
                    tfAnswers.removeFirst();
                }
            }
        }

        if (multiQueries != null) {
            for (JTextField textField : multiQueries) {
                if (textField.getText() !=null && !textField.getText().equals("")) {
                    String currentQuery = textField.getText();
                    ArrayList<String> currentAlternatives = new ArrayList<>();

                    boolean isEmpty = false;

                    for (int i = 0; i < 3; i++) {
                        JTextField currentAlternative = multiAlternatives.get(i);
                        if (currentAlternative.getText() == null || currentAlternative.getText().equals("")) {
                            isEmpty = true;
                            System.out.println("MC empty");
                            for (int j = 0; j < 3; j++) {
                                multiAlternatives.removeFirst();
                            }
                            multiCorrectBoxes.removeFirst();
                            break;
                        }
                        else {
                            currentAlternatives.add(currentAlternative.getText());
                        }
                    }

                    if (!isEmpty) {
                        int correctAlt = multiCorrectBoxes.getFirst().getSelectedIndex();
                        String correctAnswer = currentAlternatives.get(correctAlt);
                        for (int j = 0; j < 3; j++) {
                            multiAlternatives.removeFirst();
                        }
                        multiCorrectBoxes.removeFirst();
                        centerPanel.saveMultipleChoiceToFile(currentQuery, currentAlternatives, 3, correctAnswer);
                    }
                }

                else {
                    multiCorrectBoxes.removeFirst();
                    for (int j = 0; j < 3; j++) {
                        multiAlternatives.removeFirst();
                    }
                }
            }
        }

        if (matchStatements != null) {
            for (JTextField textField : matchStatements) {
                if (textField.getText() !=null && !textField.getText().equals("")) {
                    String currentQuery = textField.getText();
                    ArrayList<String> currentAlternatives = new ArrayList<>();
                    ArrayList<String> currentMatches = new ArrayList<>();
                    boolean isEmpty = false;

                    for (int i = 0; i < 3; i++) {
                        JTextField currentAlternative = matchStatements.get(i);
                        JTextField currentMatch = matchAnswers.get(i);
                        if (currentAlternative.getText() == null || currentAlternative.getText().equals("") || currentMatch.getText() == null || currentMatch.getText().equals("")) {
                            isEmpty = true;

                            for (int j = 0; j < 3; j++) {
                                matchStatements.removeFirst();
                                matchAnswers.removeFirst();
                            }
                            matchQueries.removeFirst();
                            break;
                        }
                        else {
                            currentAlternatives.add(currentAlternative.getText());
                            currentMatches.add(currentMatch.getText());
                        }
                    }

                    if (!isEmpty) {
                        HashMap<String,Integer> correctMatches = new HashMap<>();

                        String[] letters = new String[]{"A", "B" ,"C"};

                        for (int i = 1; i <= 3; i++) {
                            correctMatches.put(letters[i-1], i);
                        }

                        for (int j = 0; j < 3; j++) {
                            matchAnswers.removeFirst();
                            matchStatements.removeFirst();
                        }

                        matchQueries.removeFirst();
                        centerPanel.saveMatchingToFile(currentQuery, currentAlternatives, currentMatches, 6, correctMatches);
                    }
                }

                else {
                    for (int j = 0; j < 3; j++) {
                        matchAnswers.removeFirst();
                        matchStatements.removeFirst();
                    }
                    matchQueries.removeFirst();
                }
            }
        }

    }

    public void createTypePanel() {
        JPanel typePanel = new JPanel();
        JLabel typeLabel = new JLabel("Type of question: ");
        addButton = new JButton("Add question");

        typeComboBox = new JComboBox<>();
        String[] types = new String[]{"Multiple Choice", "True/False", "Matching"};

        for (String type : types) {
            typeComboBox.addItem(type);
        }

        typePanel.add(typeLabel);
        typePanel.add(typeComboBox);
        typePanel.add(addButton);
        mainPanel.add(typePanel);
    }

    public void createAndShowMatchingPanel() {
        //String query, List<String> statements, List<String> matches, int points, HashMap<String,Integer> correctMatches, String courseName, String moduleName
        JPanel mainQuestionPanel = new JPanel(new BorderLayout());
        JPanel matchPanel = new JPanel(new GridLayout(0, 2));
        mainQuestionPanel.add(matchPanel, BorderLayout.CENTER);

        JLabel typeOfQuestionLabel = new JLabel("Matching", SwingConstants.CENTER);
        typeOfQuestionLabel.setBorder(BorderFactory.createLineBorder(Color.black));

        mainQuestionPanel.add(typeOfQuestionLabel, BorderLayout.NORTH);

        JPanel questionPanel = new JPanel();
        JLabel questionLabel = new JLabel("Query: ");
        JTextField questionTextField = new JTextField(10);
        matchQueries.add(questionTextField);

        questionPanel.add(questionLabel);
        questionPanel.add(questionTextField);
        matchPanel.add(questionPanel);

        JLabel emptyLabel = new JLabel();
        matchPanel.add(emptyLabel);

        String[] letters = new String[]{"A", "B", "C"};

        //2 panels per row
        for (int i = 0; i < 3; i++) {
            JPanel alternativePanel = new JPanel();
            JLabel letterLabel = new JLabel(letters[i] + ": ");
            JTextField letterTextField = new JTextField(10);
            alternativePanel.add(letterLabel);
            alternativePanel.add(letterTextField);
            matchStatements.add(letterTextField);

            JPanel matchedPanel = new JPanel();
            JLabel matchLabel = new JLabel("Match: ");
            JTextField matchTextField = new JTextField(10);
            matchedPanel.add(matchLabel);
            matchedPanel.add(matchTextField);
            matchAnswers.add(letterTextField);

            matchPanel.add(alternativePanel);
            matchPanel.add(matchedPanel);
        }

        mainPanel.add(mainQuestionPanel, 1);
        mainPanel.add(new JSeparator(), 1);
        if (getHeight() < 658) {
            pack();
            setSize(getWidth() + 15, getHeight());
        }

        revalidate();
        repaint();
    }

    public void createAndShowMultiPanel() {
        JPanel mainQuestionPanel = new JPanel(new BorderLayout());
        JPanel multiPanel = new JPanel(new GridLayout(0, 2));
        mainQuestionPanel.add(multiPanel, BorderLayout.CENTER);

        JLabel typeOfQuestionLabel = new JLabel("Multiple Choice", SwingConstants.CENTER);
        typeOfQuestionLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        mainQuestionPanel.add(typeOfQuestionLabel, BorderLayout.NORTH);

        JLabel questionLabel = new JLabel("Question: ");
        JTextField questionTextField = new JTextField(10);
        multiQueries.add(questionTextField);

        multiPanel.add(questionLabel);
        multiPanel.add(questionTextField);

        JComboBox<String> multiCorrectBox = new JComboBox<>();
        multiCorrectBoxes.add(multiCorrectBox);

        for (int i = 1; i <= 3; i++) {
            JLabel alternative = new JLabel("Alternative " + i + ":");
            JTextField alternativeTextField = new JTextField(10);
            alternativeTextField.setName(String.valueOf(i));
            multiAlternatives.add(alternativeTextField);
            multiPanel.add(alternative);
            multiPanel.add(alternativeTextField);
            multiCorrectBox.addItem("Alternative " + i);
        }

        JLabel answerLabel = new JLabel("Answer: ");

        multiPanel.add(answerLabel);
        multiPanel.add(multiCorrectBox);

        mainPanel.add(mainQuestionPanel, 1);
        mainPanel.add(new JSeparator(), 1);
        if (getHeight() < 658) {
            pack();
            setSize(getWidth() + 15, getHeight());
        }

        revalidate();
        repaint();
    }

    public void createAndShowTFPanel() {
        JPanel mainQuestionPanel = new JPanel(new BorderLayout());
        JPanel tfPanel = new JPanel(new GridLayout(0, 2));
        mainQuestionPanel.add(tfPanel, BorderLayout.CENTER);

        JLabel typeOfQuestionLabel = new JLabel("True/False", SwingConstants.CENTER);
        typeOfQuestionLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        mainQuestionPanel.add(typeOfQuestionLabel, BorderLayout.NORTH);
        JLabel questionLabel = new JLabel("Statement: ");
        JTextField questionTextField = new JTextField(10);
        tfStatements.add(questionTextField);
        tfPanel.add(questionLabel);
        tfPanel.add(questionTextField);

        JLabel answerLabel = new JLabel("Answer: ");
        JComboBox<String> correctBox = new JComboBox<>();
        correctBox.addItem("True");
        correctBox.addItem("False");
        tfAnswers.add(correctBox);

        tfPanel.add(answerLabel);
        tfPanel.add(correctBox);

        mainPanel.add(mainQuestionPanel, 1);
        mainPanel.add(new JSeparator(), 1);
        if (getHeight() < 650) {
            pack();
            setSize(getWidth() + 15, getHeight());
        }

    }

    public void addItemListeners() {
        addButton.addActionListener(e -> {
            submitButton.setEnabled(true);
            if (typeComboBox.getSelectedItem().equals("Multiple Choice")) {
                createAndShowMultiPanel();
            }
            else if (typeComboBox.getSelectedItem().equals("True/False")) {
                createAndShowTFPanel();
            }

            else if (typeComboBox.getSelectedItem().equals("Matching")) {
                createAndShowMatchingPanel();
            }
        });

        submitButton.addActionListener(e -> {
            collectQuestions();
            JOptionPane.showMessageDialog(this, "Questions were submitted successfully!");
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });
    }
}
