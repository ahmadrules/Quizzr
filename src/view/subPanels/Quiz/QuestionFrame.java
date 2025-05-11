package view.subPanels.Quiz;

import model.Matching;
import model.Question;
import model.Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QuestionFrame extends JFrame {
    private List<Question> questionList;
    private MainQuizFrame mainQuizFrame;
    private JPanel mainQuestionPanel;
    private ArrayList<ButtonGroup> buttonGroups;
    private HashMap<Question, ButtonGroup> questionButtonGroupMap;
    private HashMap<Integer, ArrayList<JComboBox>> comboBoxMap;
    private HashMap<Question, ArrayList<JComboBox>> questionComboBoxMap;
    private ItemListener comboBoxListenerSelected;
    private ItemListener comboBoxListenerDeselected;
    private JButton submitButton;
    private String previousItem;
    private Quiz currentQuiz;
    private JPanel topPanel;
    private long timerSecondsmount;
    private long currentSeconds;
    private long currentMinutes;
    private Timer timer;
    private HistoryPanel historyPanel;
    private boolean isResult;


    public QuestionFrame(List<Question> questionList, Quiz currentQuiz, MainQuizFrame mainQuizFrame, long timerSecondsAmount, HistoryPanel historyPanel, boolean isResult) {
        this.questionList = questionList;
        this.mainQuizFrame = mainQuizFrame;
        this.currentQuiz = currentQuiz;
        this.timerSecondsmount = timerSecondsAmount;
        this.historyPanel = historyPanel;
        this.isResult = isResult;

        setTitle("Quiz options");
        setLayout(new BorderLayout());

        createComboBoxListeners();
        setupPanel();

        createTopPanel();
        createTimer();

        setupQuestions(isResult);

        setOnClose();
        addListeners();

        pack();
        setSize(getWidth() + 50, 600);
        setLocationRelativeTo(mainQuizFrame);

        setVisible(true);
    }

    public void createTopPanel() {
        topPanel = new JPanel(new BorderLayout());

        JLabel amountOfQuestionsLabel = new JLabel("Amount of questions: " + questionList.size());
        amountOfQuestionsLabel.setFont(new Font("Arial", Font.ROMAN_BASELINE, 18));
        topPanel.add(amountOfQuestionsLabel, BorderLayout.EAST);
    }

    public void timerEnded() {
        timer.stop();
        JOptionPane.showMessageDialog(mainQuizFrame, "Times up!");
        getUserAnswers();
        getTotalPoints();
    }

    public void setOnClose(){
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainQuizFrame.setVisible(true);
                dispose();
            }
        });
    }

    public void createTimer() {
        if (timerSecondsmount > 0) {
            currentSeconds = timerSecondsmount % 60;
            currentMinutes = TimeUnit.SECONDS.toMinutes(timerSecondsmount);

            JLabel timerLabel = new JLabel("Time left: " + currentMinutes + ":00");
            timerLabel.setFont(new Font("Arial", Font.ROMAN_BASELINE, 18));
            topPanel.add(timerLabel, BorderLayout.WEST);

            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentSeconds == 0 && currentMinutes == 0) {
                        timerEnded();
                    }

                    else {
                        currentSeconds -= 1;
                        if (currentSeconds < 0) {
                            currentMinutes -= 1;
                            currentSeconds = 59;
                        }

                        if (currentSeconds < 10) {
                            timerLabel.setText("Time left: " + currentMinutes + ":0" + currentSeconds);
                        }
                        else {
                            timerLabel.setText("Time left: " + currentMinutes + ":" + currentSeconds);
                        }
                    }
                }
            });
        }
    }

    public void getTotalPoints() {
        int userPoints = currentQuiz.CalculateTestResult();
        int totalPoints = currentQuiz.getTotalPoints();
        double statistics = ((double) userPoints/totalPoints) * 100;

        ResultPanel resultPanel = new ResultPanel(userPoints, totalPoints, statistics);
        resultPanel.setSize(getWidth() - 50, getHeight());

        getContentPane().removeAll();

        JLabel resultLabel = new JLabel("Results", SwingConstants.CENTER);
        add(resultLabel);
        add(resultPanel);
        revalidate();
        repaint();
    }

    public void getUserAnswers() {
        for (Question question : questionList) {
            if (questionButtonGroupMap.containsKey(question)) {
                ButtonGroup buttonGroup = questionButtonGroupMap.get(question);

                String currentAnswer = "Empty";
                if (buttonGroup.getSelection() != null) {
                    currentAnswer = buttonGroup.getSelection().getActionCommand();
                }
                currentQuiz.addUserAnswer(question, currentAnswer);
            }

            else if (questionComboBoxMap.containsKey(question)) {
                int comboBoxCounter = 1;
                ArrayList<JComboBox> comboBoxes = questionComboBoxMap.get(question);
                String currentAnswer = "";

                for (JComboBox comboBox : comboBoxes) {
                    currentAnswer += comboBox.getSelectedItem() + ":" + comboBoxCounter++ + ",";
                }
                currentQuiz.addUserAnswer(question, currentAnswer);
            }
        }
        currentQuiz.setDate(DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now()));
        historyPanel.addQuiz(currentQuiz);
    }
    
    public void setupPanel() {
        mainQuestionPanel = new JPanel();
        mainQuestionPanel.setLayout(new BoxLayout(mainQuestionPanel, BoxLayout.Y_AXIS));
        submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(mainQuestionPanel);

        add(scrollPane, BorderLayout.CENTER);
    }

    public boolean checkIfCorrectAnswer(Question question) {
        String userAnswer = currentQuiz.getUserAnswers().get(question);
        System.out.println("User answer: " + userAnswer);

        String correctAnswer = question.getCorrectAnswer();
        System.out.println("Correct answer: " + correctAnswer);
        if (userAnswer.equals(correctAnswer)) {
            System.out.println("method correct");
            return true;
        }
        return false;
    }

    public void setupQuestions(boolean isResult) {
        int questionId = 1;
        int mapCounter = 0;

        questionButtonGroupMap = new HashMap<>();
        questionComboBoxMap = new HashMap<>();

        buttonGroups = new ArrayList<>();
        comboBoxMap = new HashMap<Integer, ArrayList<JComboBox>>();

        mainQuestionPanel.add(topPanel);
        mainQuestionPanel.add(new JSeparator());

        if (timerSecondsmount > 0) {
            timer.start();
        }

        for (Question question : questionList) {

            //Matching questions are set up here.
            if (question instanceof Matching) {
                int counter = 0;
                ArrayList<JComboBox> comboBoxes = new ArrayList<>();

                JPanel questionPanel = new JPanel(new BorderLayout());

                JLabel query = new JLabel(questionId++ + ". " + question.getQuery());
                query.setFont(new Font("Arial", Font.BOLD, 16));

                questionPanel.add(query, BorderLayout.NORTH);

                JPanel alternativesPanel = new JPanel(new GridLayout(0, 2));

                List<String> matchAlternatives = question.getMatches();

                String[] letters = new String[]{"A" , "B" , "C"};

                for (String alternative : question.getAlternatives()) {
                    JComboBox<String> comboBox = new JComboBox<>(letters);
                    comboBoxes.add(comboBox);

                    //Set labels
                    comboBox.setSelectedIndex(counter);
                    comboBox.setActionCommand(Integer.toString(mapCounter));

                    //Add listeners
                    comboBox.addItemListener(comboBoxListenerSelected);
                    comboBox.addItemListener(comboBoxListenerDeselected);

                    JPanel comboBoxPanel = new JPanel(new BorderLayout());

                    JLabel alternativeLabel = new JLabel(alternative);
                    alternativeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

                    JLabel matchLabel = new JLabel(matchAlternatives.get(counter));
                    matchLabel.setFont(new Font("Arial", Font.PLAIN, 14));

                    comboBoxPanel.add(comboBox, BorderLayout.WEST);
                    comboBoxPanel.add(matchLabel, BorderLayout.CENTER);

                    alternativesPanel.add(alternativeLabel);
                    alternativesPanel.add(comboBoxPanel);
                    alternativesPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    counter++;

                }
                comboBoxMap.put(mapCounter++, comboBoxes);
                questionComboBoxMap.put(question, comboBoxes);

                if (isResult && checkIfCorrectAnswer(question)) {
                    questionPanel.setBackground(new Color(56, 143, 53));
                }

                questionPanel.add(alternativesPanel);
                mainQuestionPanel.add(questionPanel);
            }

            //True/false and multiple choice questions are set up here.
            else {
                JPanel questionPanel = new JPanel(new BorderLayout());
                questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));

                JLabel query = new JLabel(questionId++ + ". " + question.getQuery());
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

                if (isResult && checkIfCorrectAnswer(question)) {
                    System.out.println("correct");
                    questionPanel.setBackground(new Color(56, 143, 53));
                    questionPanel.setForeground(new Color(56, 143, 53));
                    query.setBackground(new Color(56, 143, 53));
                }

                questionButtonGroupMap.put(question, buttonGroup);
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
            //mainQuizFrame.setQuizAsDone(true);
            getUserAnswers();
            getTotalPoints();
        });
    }
}
