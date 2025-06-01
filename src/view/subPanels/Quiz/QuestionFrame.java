package view.subPanels.Quiz;

import model.Matching;
import model.Question;
import model.Quiz;
import view.main.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

//Color yellow 255, 249, 163
//Color baseColor = new Color(25, 25, 70);
//Color haverColor = new Color(90, 140, 230);
public class QuestionFrame extends JFrame {
    private MainQuizFrame mainQuizFrame;
    private boolean isResult;
    private Quiz currentQuiz;
    private List<Question> questionList;

    private JPanel mainQuestionPanel;
    private JPanel topPanel;

    private ArrayList<ButtonGroup> buttonGroups;
    private JButton submitButton;
    private JButton closeButton;
    private String previousItem;

    private HashMap<Question, ButtonGroup> questionButtonGroupMap;
    private HashMap<Integer, ArrayList<JComboBox>> comboBoxMap;
    private HashMap<Question, ArrayList<JComboBox>> questionComboBoxMap;
    private ItemListener comboBoxListenerSelected;
    private ItemListener comboBoxListenerDeselected;

    private long timerSecondsAmount;
    private long currentSeconds;
    private long currentMinutes;
    private Timer timer;


    public QuestionFrame(Quiz currentQuiz, MainQuizFrame mainQuizFrame, boolean isResult) {
        this.mainQuizFrame = mainQuizFrame;
        this.currentQuiz = currentQuiz;
        this.isResult = isResult;
        setBackground(new Color(255, 249, 163));

        questionList = currentQuiz.getQuestions();

        timerSecondsAmount = currentQuiz.getTimerSeconds();
        if (isResult) {
            timerSecondsAmount = 0;
        }

        setTitle("Quiz options");
        setLayout(new BorderLayout());

        if (!isResult) {
            createComboBoxListeners();
        }

        setupPanel();
        createTopPanel();
        add(topPanel, BorderLayout.NORTH);
        createTimer();

        setupQuestions(isResult);

        setOnClose();
        addListeners();

        pack();
        setSize(getWidth() + 200, 600);
        setLocationRelativeTo(mainQuizFrame);

        ImageIcon icon = new ImageIcon(getClass().getResource("/view/pics/Quizzr-logo.png"));
        setIconImage(icon.getImage());

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public QuestionFrame(Quiz currentQuiz, MainFrame mainFrame) {
        this.currentQuiz = currentQuiz;

        questionList = currentQuiz.getQuestions();

        timerSecondsAmount = 0;
        if (isResult) {
            timerSecondsAmount = 0;
        }

        setTitle("Quiz options");
        setLayout(new BorderLayout());

        if (!isResult) {
            createComboBoxListeners();
        }

        setupPanel();

        createTopPanel();
        createTimer();

        setupQuestions(false);

        setOnClose();
        addListeners();

        pack();
        setSize(getWidth() + 50, 600);
        setLocationRelativeTo(mainFrame);

        ImageIcon icon = new ImageIcon(getClass().getResource("/view/pics/Quizzr-logo.png"));
        setIconImage(icon.getImage());

        setVisible(true);
    }

    public void createTopPanel() {
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(255, 249, 163));

        JPanel eastPanel = new JPanel();
        eastPanel.setBackground(new Color(255, 249, 163));
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));

        JPanel amountOfQuestionsPanel = new JPanel(new BorderLayout());
        amountOfQuestionsPanel.setBackground(new Color(52,69,140));

        JLabel amountOfQuestionsLabel = new JLabel("Total questions: " + questionList.size());
        amountOfQuestionsLabel.setFont(new Font("Montserrat", Font.BOLD, 16));
        amountOfQuestionsLabel.setForeground(Color.WHITE);

        amountOfQuestionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        amountOfQuestionsLabel.setPreferredSize(new Dimension(175, 30));
        amountOfQuestionsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        amountOfQuestionsPanel.add(amountOfQuestionsLabel, BorderLayout.CENTER);
        eastPanel.add(amountOfQuestionsPanel);

        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 7, 15, 10));

        if (isResult) {
            String[] parts = currentQuiz.getDate().split("-");
            JLabel dateLabel = new JLabel("Date: " + parts[0]);
            JLabel timeLabel = new JLabel("Time: " + parts[1]);
            dateLabel.setFont(new Font("Montserrat", Font.ROMAN_BASELINE, 18));
            timeLabel.setFont(new Font("Montserrat", Font.ROMAN_BASELINE, 18));
            eastPanel.add(dateLabel);
            eastPanel.add(timeLabel);
        }

        if (isResult) {
            topPanel.add(eastPanel, BorderLayout.WEST);
        } else {
            topPanel.add(eastPanel, BorderLayout.EAST);
        }
    }

    public void timerEnded() {
        timer.stop();
        JOptionPane.showMessageDialog(null, "Times up!");
        getUserAnswers();
        getTotalPoints();
    }

    public void setOnClose() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (mainQuizFrame != null) {
                    mainQuizFrame.setVisible(true);
                }
                if (timer != null && timer.isRunning()) {
                    timer.stop();
                }
                dispose();
            }
        });
    }

    public void createTimer() {
        if (timerSecondsAmount > 0) {
            currentSeconds = timerSecondsAmount % 60;
            currentMinutes = TimeUnit.SECONDS.toMinutes(timerSecondsAmount);

            JLabel timerLabel = new JLabel("Time left: " + currentMinutes + ":00");
            timerLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
            timerLabel.setForeground(new Color(44, 58, 117));
            topPanel.add(timerLabel, BorderLayout.WEST);

            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentSeconds == 0 && currentMinutes == 0) {
                        timerEnded();
                    } else {
                        currentSeconds -= 1;
                        if (currentSeconds < 0) {
                            currentMinutes -= 1;
                            currentSeconds = 59;
                        }

                        if(currentMinutes == 0 && currentSeconds <= 30){
                            timerLabel.setForeground(new Color(199, 23,30));
                        }

                        if (currentSeconds < 10) {
                            timerLabel.setText("Time left: " + currentMinutes + ":0" + currentSeconds);
                        } else {
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
        double statistics = ((double) userPoints / totalPoints) * 100;

        ResultPanel resultPanel = new ResultPanel(userPoints, totalPoints, statistics);
        resultPanel.setSize(getWidth() - 50, getHeight());
        resultPanel.setBackground(new Color(255, 249, 163));

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
            } else if (questionComboBoxMap.containsKey(question)) {
                int comboBoxCounter = 1;
                ArrayList<JComboBox> comboBoxes = questionComboBoxMap.get(question);
                String currentAnswer = "";

                for (JComboBox comboBox : comboBoxes) {
                    currentAnswer += comboBox.getSelectedItem() + ":" + comboBoxCounter++ + ",";
                }
                currentQuiz.addUserAnswer(question, currentAnswer);
            }
        }

        if (!isResult && mainQuizFrame != null) {
            mainQuizFrame.addQuizToHistory(currentQuiz.getName(), currentQuiz.getQuestions(), currentQuiz.getUserAnswers());
        }
    }

    public void setupPanel() {
        mainQuestionPanel = new JPanel();
        mainQuestionPanel.setBackground(new Color(255, 249, 163));
        mainQuestionPanel.setLayout(new BoxLayout(mainQuestionPanel, BoxLayout.Y_AXIS));

        submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(52, 69,140));
        submitButton.setForeground(Color.WHITE);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setPreferredSize(new Dimension(110, 50));
        submitButton.setMaximumSize(submitButton.getPreferredSize());
        submitButton.setMinimumSize(submitButton.getPreferredSize());
        submitButton.setFont(new Font("Montserrat", Font.BOLD, 14));
        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(new Color(90, 140, 230));
                submitButton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(90, 140, 230).darker(), 2),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(new Color(52, 69,140));
                submitButton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(52, 69,140), 2),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
        });

        closeButton = new JButton("Close");
        closeButton.setBackground(new Color(25, 25, 70));
        closeButton.setForeground(Color.WHITE);
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(mainQuestionPanel);
        scrollPane.setBackground(new Color(25, 25, 70));
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(25, 25, 70);
                this.trackColor = new Color(255, 249, 163);
            }
        });
        add(scrollPane, BorderLayout.CENTER);
        invalidate();
        validate();
        repaint();
    }

    public boolean checkIfCorrectAnswer(Question question) {
        String userAnswer = currentQuiz.getUserAnswers().get(question);
        String correctAnswer = question.getCorrectAnswer();

        if (question instanceof Matching) {
            String[] letters = new String[]{"A", "B", "C"};
            HashMap<String, Integer> correctMatches = ((Matching) question).getCorrectMatches();
            correctAnswer = "";

            for (String letter : letters) {
                correctAnswer += letter + ":" + correctMatches.get(letter) + ",";
            }
        }

        if (userAnswer.equals(correctAnswer)) {
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
        comboBoxMap = new HashMap<>();
        
        mainQuestionPanel.add(new JSeparator());
        mainQuestionPanel.setMaximumSize(new Dimension(1000, Integer.MAX_VALUE));

        if (timerSecondsAmount > 0) {
            timer.start();
        }

        for (Question question : questionList) {

            //Matching questions are set up here.
            if (question instanceof Matching) {
                int counter = 0;
                ArrayList<JComboBox> comboBoxes = new ArrayList<>();

                JPanel questionPanel = new JPanel(new BorderLayout());
                questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));

                JPanel topPanel = new JPanel(new BorderLayout());
                topPanel.setBackground(new Color(255, 249, 163));


                JLabel queryLabel = new JLabel(questionId++ + ". " + question.getQuery());
                queryLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
                queryLabel.setForeground(Color.BLACK);
                queryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                queryLabel.setPreferredSize(new Dimension(200, 30));
                queryLabel.setHorizontalAlignment(SwingConstants.CENTER);

                topPanel.setBorder(BorderFactory.createEmptyBorder(15, 7, 15, 10));

                topPanel.add(queryLabel, BorderLayout.CENTER);

                questionPanel.add(topPanel, BorderLayout.NORTH);

                JPanel alternativesPanel = new JPanel(new GridLayout(0, 2));
                alternativesPanel.setBackground(Color.WHITE);

                List<String> matchAlternatives = question.getMatches();

                String[] letters = new String[]{"A", "B", "C"};

                String[] userAnswers = new String[3];

                if (isResult) {
                    userAnswers = currentQuiz.getUserAnswers().get(question).split(",");
                }

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
                    comboBoxPanel.setBackground(Color.WHITE);

                    JLabel alternativeLabel = new JLabel(alternative);
                    alternativeLabel.setFont(new Font("Montserrat", Font.PLAIN, 16));
                    alternativeLabel.setBackground(Color.WHITE);

                    JLabel matchLabel = new JLabel(matchAlternatives.get(counter));
                    matchLabel.setFont(new Font("Montserrat", Font.PLAIN, 16));

                    comboBoxPanel.add(comboBox, BorderLayout.WEST);
                    comboBoxPanel.add(matchLabel, BorderLayout.CENTER);

                    alternativesPanel.add(alternativeLabel);
                    alternativesPanel.add(comboBoxPanel);

                    if (isResult) {
                        String imagePath = "";
                        if (checkIfCorrectAnswer(question)) {
                            comboBoxPanel.setBackground(new Color(150, 240, 149));
                            alternativesPanel.setBackground(new Color(150, 240, 149));
                            imagePath = getClass().getResource("/view/pics/greenCheckmark.png").toString();
                        } else {
                            comboBoxPanel.setBackground(new Color(240, 149, 149));
                            alternativesPanel.setBackground(new Color(240, 149, 149));
                            imagePath = getClass().getResource("/view/pics/redCheckmark.png").toString();
                        }

                        JLabel imageIcon = new JLabel("<html><img src='" + imagePath + "' width='20' height='20'></html>");
                        topPanel.add(imageIcon, BorderLayout.EAST);

                        String[] userLetters = userAnswers[counter].split(":");

                        comboBox.setSelectedItem(userLetters[0]);
                        JTextField field = (JTextField) comboBox.getEditor().getEditorComponent();
                        field.setEnabled(false);

                        MouseListener[] mouseListeners = comboBox.getMouseListeners();
                        for (MouseListener mouseListener : mouseListeners) {
                            comboBox.removeMouseListener(mouseListener);
                        }

                        for (Component component : comboBox.getComponents()) {
                            if (component instanceof AbstractButton) {
                                component.setEnabled(false);
                                MouseListener[] listeners = component.getMouseListeners();
                                for (MouseListener listener : listeners) {
                                    component.removeMouseListener(listener);
                                }

                            }
                        }
                    }
                    counter++;
                }

                comboBoxMap.put(mapCounter++, comboBoxes);
                questionComboBoxMap.put(question, comboBoxes);

                questionPanel.add(alternativesPanel);
                mainQuestionPanel.add(questionPanel);
            }

            //True/false and multiple choice questions are set up here.
            else {
                JPanel questionPanel = new JPanel();
                questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
                questionPanel.setBackground(Color.WHITE);
                questionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                questionPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

                JPanel topPanel = new JPanel(new BorderLayout());
                topPanel.setBackground(new Color(52, 69,140));
                topPanel.setPreferredSize(new Dimension(900, 50));


                JLabel queryLabel = new JLabel(questionId++ + ". " + question.getQuery());
                queryLabel.setFont(new Font("Montserrat", Font.BOLD, 14));
                queryLabel.setForeground(Color.WHITE);
                queryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                queryLabel.setHorizontalAlignment(SwingConstants.CENTER);

                if (isResult) {
                    String imagePath = "";
                    if (checkIfCorrectAnswer(question)) {
                        imagePath = getClass().getResource("/view/pics/greenCheckmark.png").toString();
                    } else {
                        imagePath = getClass().getResource("/view/pics/redCheckmark.png").toString();
                    }

                    JLabel imageIcon = new JLabel("<html><img src='" + imagePath + "' width='20' height='20'></html>");
                    topPanel.add(imageIcon, BorderLayout.EAST);
                }


                questionPanel.setMaximumSize(new Dimension(900, 500));
                topPanel.add(queryLabel, BorderLayout.CENTER);
                questionPanel.add(Box.createVerticalStrut(15));
                questionPanel.add(topPanel);

                ButtonGroup buttonGroup = new ButtonGroup();
                buttonGroups.add(buttonGroup);

                JPanel alternativesPanel = new JPanel(new GridBagLayout());
                alternativesPanel.setBackground(Color.WHITE);
                alternativesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                alternativesPanel.setAlignmentX(SwingConstants.LEFT);
                alternativesPanel.setMaximumSize(questionPanel.getMaximumSize());

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.anchor = GridBagConstraints.WEST;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1;

                for (String alternative : question.getAlternatives()) {
                    JRadioButton checkBox = new JRadioButton(alternative);
                    checkBox.setFont(new Font("Montserrat", Font.PLAIN, 16));
                    checkBox.setBackground(Color.WHITE);
                    checkBox.setAlignmentX(Component.CENTER_ALIGNMENT);
                    checkBox.setAlignmentX(SwingConstants.LEFT);
                    checkBox.setActionCommand(alternative);
                    buttonGroup.add(checkBox);

                    alternativesPanel.add(checkBox, gbc);
                    gbc.gridy++;
                }

                questionPanel.add(alternativesPanel);

                if (isResult) {
                    String correctAnswer = question.getCorrectAnswer();
                    Component[] components = alternativesPanel.getComponents();
                    String userAnswer = currentQuiz.getUserAnswers().get(question);
                    for (Component component : components) {
                        String actionCommand = ((JRadioButton) component).getActionCommand();
                        component.setEnabled(false);
                        if (actionCommand.equals(correctAnswer)) {
                            component.setBackground(new Color(150, 240, 149));
                        } else {
                            component.setBackground(new Color(240, 149, 149));
                        }
                        if (actionCommand.equals(userAnswer)) {
                            ((JRadioButton) component).setSelected(true);
                        }
                    }
                }

                questionButtonGroupMap.put(question, buttonGroup);
                mainQuestionPanel.add(questionPanel);
                mainQuestionPanel.add(Box.createVerticalStrut(30));
            }
        }
        mainQuestionPanel.add(Box.createVerticalStrut(20));
        if (isResult) {
            mainQuestionPanel.add(closeButton);
        } else {
            mainQuestionPanel.add(submitButton);
        }
        mainQuestionPanel.add(Box.createVerticalStrut(40));
        invalidate();
        validate();
        repaint();
    }

    public void createComboBoxListeners() {
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

    public void addListeners() {
        submitButton.addActionListener(e -> {
            //mainQuizFrame.setQuizAsDone(true);
            getUserAnswers();
            getTotalPoints();
        });

        closeButton.addActionListener(e -> {
            dispose();
        });
    }
}
