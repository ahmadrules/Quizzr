package view.subPanels.Quiz;

import model.Matching;
import model.Question;
import model.Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionFrame extends JFrame {
    private List<Question> questionList;
    private MainQuizFrame mainQuizFrame;
    private JPanel mainQuestionPanel;
    private ArrayList<ButtonGroup> buttonGroups;
    private ArrayList<JComboBox> comboBoxes;
    private HashMap<Integer, ArrayList<JComboBox>> comboBoxMap;
    private ItemListener comboBoxListenerSelected;
    private ItemListener comboBoxListenerDeselected;
    private JButton submitButton;
    private String previousItem;
    private Quiz currentQuiz;

    public QuestionFrame(List<Question> questionList, Quiz currentQuiz, MainQuizFrame mainQuizFrame) {
        this.questionList = questionList;
        this.mainQuizFrame = mainQuizFrame;
        this.currentQuiz = currentQuiz;

        setTitle("Quiz options");
        setLayout(new BorderLayout());
        setSize(400, 500);

        createComboBoxListeners();
        setupPanel();
        setupQuestions();
        addListeners();
        setVisible(true);
    }

    public void getTotalPoints() {
        int totalPoints = currentQuiz.CalculateTestResult();

        getContentPane().removeAll();

        JPanel resultPanel = new JPanel(new BorderLayout());
        JLabel pointsLabel = new JLabel("Total Points: " + totalPoints, SwingConstants.CENTER);

        resultPanel.add(pointsLabel, BorderLayout.CENTER);

        JLabel resultLabel = new JLabel("Results", SwingConstants.CENTER);

        add(resultLabel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public void getUserAnswers() {
        int counter = 0;

        if (buttonGroups != null && !buttonGroups.isEmpty()) {
            for (ButtonGroup buttonGroup : buttonGroups) {
                Question currentQuestion = questionList.get(counter);
                String currentAnswer = "Empty";
                if (buttonGroup.getSelection() != null) {
                    currentAnswer = buttonGroup.getSelection().getActionCommand();
                }

                currentQuiz.addUserAnswer(currentQuestion, currentAnswer);
                counter++;
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
                    currentQuiz.addUserAnswer(questionList.get(counter), currentAnswer);
                    counter++;
                }
            }
        }
    }
    
    public void setupPanel() {
        mainQuestionPanel = new JPanel();
        mainQuestionPanel.setLayout(new BoxLayout(mainQuestionPanel, BoxLayout.Y_AXIS));
        submitButton = new JButton("Submit");

        JScrollPane scrollPane = new JScrollPane(mainQuestionPanel);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void setupQuestions() {
        int questionId = 1;
        int mapCounter = 0;
        buttonGroups = new ArrayList<>();
        comboBoxes = new ArrayList<>();
        comboBoxMap = new HashMap<Integer, ArrayList<JComboBox>>();

        for (Question question : questionList) {

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
                mainQuestionPanel.add(questionPanel);
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

                mainQuestionPanel.add(questionPanel);
            }
        }
        mainQuestionPanel.add(submitButton);
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

    public void addListeners () {
        submitButton.addActionListener(e -> {
            getUserAnswers();
            getTotalPoints();
        });
    }
}
