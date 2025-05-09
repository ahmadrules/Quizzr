package view.subPanels;

import view.main.CenterPanels.CenterModulePanel;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

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
        pack();
        setSize(getWidth() + 15, getHeight());
    }

    public void initiateLists() {
        multiQueries = new ArrayList<>();
        multiCorrectBoxes = new ArrayList<>();
        multiAlternatives = new ArrayList<>();

        tfStatements = new ArrayList<>();
        tfAnswers = new ArrayList<>();

    }

    public void createSubmitButton() {
        submitButton = new JButton("Submit questions");
        //submitButton.setHorizontalAlignment(SwingConstants.CENTER);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setEnabled(false);
        mainPanel.add(submitButton);
    }

    public void collectQuestions() {
        //String query, List<String> alternatives,int points, String correctAnswer ,String courseName, String ModuleName;

        if (tfStatements != null) {
            int tfAnswerCounter = 0;
            ArrayList<String> tfAlternatives = new ArrayList<>();
            tfAlternatives.add("True");
            tfAlternatives.add("False");
            for (JTextField textField : tfStatements) {
                if (textField.getText() !=null && !textField.getText().equals("")) {
                    String currentStatement = textField.getText();
                    System.out.println(currentStatement);
                    String currentAnswer = (String) tfAnswers.get(tfAnswerCounter++).getSelectedItem();
                    System.out.println(currentAnswer);
                    centerPanel.saveTrueOrFalseQuestion(currentStatement, tfAlternatives, 3, currentAnswer);
                }
            }
        }

        if (multiQueries != null) {
            System.out.println(multiQueries.size());
            for (JTextField textField : multiQueries) {
                if (textField.getText() !=null && !textField.getText().equals("")) {
                    System.out.println("New multiquestion being collected");
                    String currentQuery = textField.getText();
                    System.out.println("Multi query: " + currentQuery);
                    ArrayList<String> currentAlternatives = new ArrayList<>();

                    boolean isEmpty = false;

                    for (int i = 0; i < 3; i++) {
                        JTextField currentAlternative = multiAlternatives.get(i);
                        if (currentAlternative.getText() == null || currentAlternative.getText().equals("")) {
                            isEmpty = true;
                            System.out.println("its empty");
                            for (int j = 0; j < 3; j++) {
                                multiAlternatives.removeFirst();
                            }
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
                            System.out.println("Alternative " + j + ":" + currentAlternatives.get(j));
                            multiAlternatives.removeFirst();
                        }
                        multiCorrectBoxes.removeFirst();
                        System.out.println("Correct answer: " + correctAnswer);
                        centerPanel.saveMultipleChoiceToFile(currentQuery, currentAlternatives, 3, correctAnswer);
                    }
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
        JPanel mainQuestionPanel = new JPanel(new BorderLayout());
        JPanel matchPanel = new JPanel(new GridLayout(0, 2));
        mainQuestionPanel.add(matchPanel, BorderLayout.CENTER);

        JLabel typeOfQuestionLabel = new JLabel("Matching", SwingConstants.CENTER);
        typeOfQuestionLabel.setBorder(BorderFactory.createLineBorder(Color.black));

        mainQuestionPanel.add(typeOfQuestionLabel, BorderLayout.NORTH);

        JPanel questionPanel = new JPanel();
        JLabel questionLabel = new JLabel("Query: ");
        JTextField questionTextField = new JTextField(10);
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

            JPanel matchedPanel = new JPanel();
            JLabel matchLabel = new JLabel("Match: ");
            JTextField matchTextField = new JTextField(10);
            matchedPanel.add(matchLabel);
            matchedPanel.add(matchTextField);

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
        System.out.println("New textfield added to multiqueries");

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
            System.out.println(typeComboBox.getSelectedItem());
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
        });
    }
}
