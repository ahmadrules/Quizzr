package view.subPanels;

import model.Matching;
import model.Module;
import model.Question;
import model.Quiz;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class QuizPanel2 extends JFrame {
    private Module currentModule;
    private String selectedModule;
    private String selectedCourse;
    private String selectedProgram;
    private MainFrame mainFrame;
    private Quiz currentQuiz;
    private JPanel buttonPanel;
    private JButton trueOrFalseButton;
    private JButton multiButton;
    private JButton matchingButton;
    private ArrayList<Question> questionsList;
    private int timerAmount;
    private JFrame quizFrame;
    private int questionId;
    private JPanel quizPanel;
    private ArrayList<ButtonGroup> buttonGroups;
    private ArrayList<JComboBox> comboBoxes;
    private int totalPoints;
    private JButton submitButton;
    private HashMap<Integer, ArrayList<JComboBox>> comboBoxMap;
    private AtomicBoolean userInput = new AtomicBoolean(true);
    private ItemListener comboBoxListener;
    private ArrayList<JComboBox> allComboBoxes;
    private ItemListener comboBoxListenerSelected;
    private ItemListener comboBoxListenerDeselected;
    private String previousItem;

    public QuizPanel2(String selectedProgram, String selectedCourse, String selectedModule, MainFrame mainFrame) {
        this.selectedProgram = selectedProgram;
        this.selectedCourse = selectedCourse;
        this.selectedModule = selectedModule;
        this.mainFrame = mainFrame;

        allComboBoxes = new ArrayList<JComboBox>();

        submitButton = new JButton("Submit");

        setTitle("Quiz Panel");
        createComboBoxListeners();
        setLayout();
        fetchModule();
        createButtonPanel();
        addActionListeners();
        setVisible(true);
    }

    public void setLayout() {
        setLayout(new BorderLayout());
        setSize(700, 200);

        JLabel quizLabel = new JLabel("Available quiz", SwingConstants.CENTER);
        add(quizLabel, BorderLayout.NORTH);
    }


    public void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        trueOrFalseButton = new JButton("Generate true/false quiz");
        multiButton = new JButton("Generate multiple choice quiz");
        matchingButton = new JButton("Generate matching quiz");


        buttonPanel.add(trueOrFalseButton);
        buttonPanel.add(multiButton);
        buttonPanel.add(matchingButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setupQuestions() {
        questionId = 1;
        int mapCounter = 0;
        buttonGroups = new ArrayList<>();
        comboBoxes = new ArrayList<>();
        comboBoxMap = new HashMap<Integer, ArrayList<JComboBox>>();

        for (Question question : questionsList) {

            //Matching questions are set up here.
            if (question instanceof Matching) {
                int counter = 1;
                comboBoxes = new ArrayList<>();

                JPanel questionPanel = new JPanel(new BorderLayout());

                JLabel query = new JLabel(questionId++ + ". " + question.getQuestion());
                query.setFont(new Font("Arial", Font.BOLD, 16));

                questionPanel.add(query, BorderLayout.NORTH);

                JPanel alternativesPanel = new JPanel(new GridLayout(0, 2));

                List<String> matchAlternatives = question.getMatches();

                String[] letters = new String[]{"A" , "B" , "C"};

                for (String alternative : question.getAlternatives()) {
                    JComboBox<String> comboBox = new JComboBox<>(letters);
                    comboBoxes.add(comboBox);

                    //Set labels
                    comboBox.setName(Integer.toString(counter));
                    comboBox.setSelectedIndex(counter - 1);
                    comboBox.setActionCommand(Integer.toString(mapCounter));

                    //Add listeners
                    comboBox.addItemListener(comboBoxListenerSelected);
                    comboBox.addItemListener(comboBoxListenerDeselected);

                    JPanel comboBoxPanel = new JPanel(new BorderLayout());

                    JLabel alternativeLabel = new JLabel(alternative);
                    alternativeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

                    JLabel matchLabel = new JLabel(matchAlternatives.get(counter - 1));
                    matchLabel.setFont(new Font("Arial", Font.PLAIN, 14));

                    comboBoxPanel.add(comboBox, BorderLayout.WEST);

                    comboBoxPanel.add(matchLabel, BorderLayout.CENTER);

                    alternativesPanel.add(alternativeLabel);
                    alternativesPanel.add(comboBoxPanel);
                    alternativesPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    counter++;

                }
                comboBoxMap.put(mapCounter++, comboBoxes);
                questionPanel.add(alternativesPanel);
                quizPanel.add(questionPanel);
            }

            //True/false and multiple choice questions are set up here.
            else {
            JPanel questionPanel = new JPanel(new BorderLayout());
            questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));

            JLabel query = new JLabel(questionId++ + ". " + question.getQuestion());
            query.setFont(new Font("Arial", Font.BOLD, 16));
            questionPanel.add(query, BorderLayout.NORTH);

            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroups.add(buttonGroup);

            JPanel alternativesPanel = new JPanel();
            alternativesPanel.setLayout(new BoxLayout(alternativesPanel, BoxLayout.Y_AXIS));
            alternativesPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            questionPanel.add(alternativesPanel, BorderLayout.CENTER);

            for (String alternative : question.getAlternatives()) {
                JRadioButton checkBox = new JRadioButton(alternative);
                checkBox.setFont(new Font("Arial", Font.PLAIN, 14));
                checkBox.setActionCommand(alternative);
                buttonGroup.add(checkBox);
                alternativesPanel.add(checkBox);
                }

            quizPanel.add(questionPanel);
            }
        }
        quizPanel.add(submitButton, BorderLayout.SOUTH);
    }


    public void getUserAnswers() {
        AtomicInteger counter = new AtomicInteger();

        if (buttonGroups != null && !buttonGroups.isEmpty()) {
            for (ButtonGroup buttonGroup : buttonGroups) {
                Question currentQuestion = questionsList.get(counter.get());
                String currentAnswer = "Empty";
                if (buttonGroup.getSelection() != null) {
                    currentAnswer = buttonGroup.getSelection().getActionCommand();
                }

                currentQuiz.addUserAnswer(currentQuestion, currentAnswer);
                counter.getAndIncrement();
            }
        }

        else {
            if (comboBoxes != null && !comboBoxes.isEmpty()) {
                for (Map.Entry<Integer, ArrayList<JComboBox>> entry : comboBoxMap.entrySet()) {
                    ArrayList<JComboBox> comboBoxes1 = entry.getValue();
                    String currentAnswer = "";
                    for (JComboBox comboBox : comboBoxes1) {
                        currentAnswer = currentAnswer + comboBox.getSelectedItem() + ":" + comboBox.getName() + ",";
                    }
                    System.out.println(currentAnswer);
                    currentQuiz.addUserAnswer(questionsList.get(counter.getAndIncrement()), currentAnswer);
                }
            }
        }
    }

    public void getTotalPoints() {
        totalPoints = currentQuiz.CalculateTestResult();

        quizFrame.getContentPane().removeAll();

        JPanel resultPanel = new JPanel(new BorderLayout());
        JLabel pointsLabel = new JLabel("Total Points: " + totalPoints, SwingConstants.CENTER);

        resultPanel.add(pointsLabel, BorderLayout.CENTER);

        JLabel resultLabel = new JLabel("Results", SwingConstants.CENTER);

        quizFrame.add(resultLabel, BorderLayout.NORTH);
        quizFrame.add(resultPanel, BorderLayout.CENTER);

        quizFrame.revalidate();
        quizFrame.repaint();
    }

    public void showQuiz() {
        quizFrame = new JFrame("The Quiz");
        quizFrame.setLayout(new BorderLayout());
        quizFrame.setSize(400, 500);

        quizPanel = new JPanel();
        quizPanel.setLayout(new BoxLayout(quizPanel, BoxLayout.Y_AXIS));

        setupQuestions();
        JScrollPane scrollPane = new JScrollPane(quizPanel);

        quizFrame.add(scrollPane, BorderLayout.CENTER);
        quizFrame.setVisible(true);
    }

    public void fetchModule() {
        currentModule = mainFrame.getCurrentModule(selectedProgram, selectedCourse, selectedModule);
    }

    public void generateTrueOrFalse() {
        currentQuiz = new Quiz("True or False");
        questionsList = currentModule.generateTrueOrFalseQuiz("src/model/files/course2/module1/trueFalse_questions.txt",
                10);
    }

    public void generateMultipleChoice() {
        currentQuiz = new Quiz("Multiple Choice");
        questionsList = currentModule.generateMultipleChoiceQuiz("src/model/files/course2/module1/multiChoice_questions.txt", 10);
    }

    public void generateMatching() {
        currentQuiz = new Quiz("Matching");
        questionsList = currentModule.generateMatchingQuiz("src/model/files/course2/module1/matching_questions.txt", 10);
    }

    public void addActionListeners() {
        submitButton.addActionListener(e -> {
            getUserAnswers();
            getTotalPoints();
        });

        trueOrFalseButton.addActionListener(e -> {
            generateTrueOrFalse();
            showQuiz();
        });

        multiButton.addActionListener(e -> {
            generateMultipleChoice();
            showQuiz();
        });

        matchingButton.addActionListener(e -> {
            generateMatching();
            showQuiz();
        });
    }

    public void createComboBoxListeners () {
        comboBoxListenerSelected = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                JComboBox currentComboBox = (JComboBox) e.getSource();

                String currentItem = (String) e.getItem();

                int key = Integer.parseInt(currentComboBox.getActionCommand());

                ArrayList<JComboBox> currentComboLists = comboBoxMap.get(key);

                for (JComboBox currentBox : currentComboLists) {
                    if (currentBox.getSelectedItem().equals(currentItem) && currentBox != currentComboBox) {
                        currentBox.removeItemListener(comboBoxListenerSelected);
                        currentBox.removeItemListener(comboBoxListenerDeselected);

                        currentBox.setSelectedItem(previousItem);

                        currentBox.addItemListener(comboBoxListenerSelected);
                        currentBox.addItemListener(comboBoxListenerDeselected);
                    }
                }
            }
        };

        comboBoxListenerDeselected = e -> {
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                previousItem = (String) e.getItem();
            }
        };
    }
}