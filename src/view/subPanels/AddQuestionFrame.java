package view.subPanels;

import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class AddQuestionFrame extends JFrame {

    private JComboBox<String> typeComboBox;
    private JPanel mainPanel;
    private JButton addButton;

    public AddQuestionFrame(MainFrame mainFrame) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(mainPanel);

        createTypePanel();
        addItemListeners();
        setLocationRelativeTo(mainFrame);

        add(scrollPane);
        setVisible(true);
        pack();
        setSize(getWidth() + 15, getHeight());
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

        JPanel categoryPanel = new JPanel();

        JLabel questionLabel = new JLabel("Category: ");
        JTextField questionTextField = new JTextField(10);
        categoryPanel.add(questionLabel);
        categoryPanel.add(questionTextField);
        matchPanel.add(categoryPanel);

        JLabel emptyLabel = new JLabel("");
        matchPanel.add(emptyLabel);

        String[] letters = new String[]{"A", "B", "C"};

        //2 panels per row
        for (int i = 0; i < 3; i++) {
            JPanel alternativePanel = new JPanel(new GridLayout(0,2));
            JLabel letterLabel = new JLabel(letters[i] + ": ");
            JTextField letterTextField = new JTextField(10);
            alternativePanel.add(letterLabel);
            alternativePanel.add(letterTextField);

            JPanel matchedPanel = new JPanel(new GridLayout(0,2));
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
        multiPanel.add(questionLabel);
        multiPanel.add(questionTextField);
        ArrayList<JTextField> multiAlternatives = new ArrayList<>();
        JComboBox<String> correctBox = new JComboBox<>();

        for (int i = 1; i <= 3; i++) {
            JLabel alternative = new JLabel("Alternative: " + i);
            JTextField alternativeTextField = new JTextField(10);
            alternativeTextField.setName("Alternative " + i);
            multiAlternatives.add(alternativeTextField);
            multiPanel.add(alternative);
            multiPanel.add(alternativeTextField);
            correctBox.addItem("Alternative " + i);
        }

        JLabel answerLabel = new JLabel("Answer: ");

        multiPanel.add(answerLabel);
        multiPanel.add(correctBox);

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
        tfPanel.add(questionLabel);
        tfPanel.add(questionTextField);

        ArrayList<JTextField> tfAlternatives = new ArrayList<>();


        JLabel alternative = new JLabel("True: ");
        JTextField alternativeTextField = new JTextField(10);
        alternativeTextField.setName("True");
        tfAlternatives.add(alternativeTextField);
        tfPanel.add(alternative);
        tfPanel.add(alternativeTextField);

        JLabel alternative2 = new JLabel("False: ");
        JTextField alternativeTextField2 = new JTextField(10);
        alternativeTextField2.setName("False");
        tfAlternatives.add(alternativeTextField2);
        tfPanel.add(alternative2);
        tfPanel.add(alternativeTextField2);


        JLabel answerLabel = new JLabel("Answer: ");
        JComboBox<String> correctBox = new JComboBox<>();
        correctBox.addItem("True");
        correctBox.addItem("False");

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
    }
}
