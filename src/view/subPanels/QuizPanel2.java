package view.subPanels;

import model.Module;
import model.Question;
import model.Quiz;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private ArrayList<Question> questionsList;
    private int timerAmount;
    private JFrame quizFrame;
    private int questionId;
    private JPanel quizPanel;
    private ArrayList<ButtonGroup> buttonGroups;
    private int totalPoints;
    private JButton submitButton;

    public QuizPanel2(String selectedProgram, String selectedCourse, String selectedModule, MainFrame mainFrame) {
        this.selectedProgram = selectedProgram;
        this.selectedCourse = selectedCourse;
        this.selectedModule = selectedModule;
        this.mainFrame = mainFrame;

        submitButton = new JButton("Submit");

        setTitle("Quiz Panel");
        setLayout();
        fetchModule();
        createButtonPanel();
        addActionListeners();
        setVisible(true);
    }

    public void setLayout() {
        setLayout(new BorderLayout());
        setSize(400, 200);

        JLabel quizLabel = new JLabel("Available quiz", SwingConstants.CENTER);
        add(quizLabel, BorderLayout.NORTH);

    }


    public void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        trueOrFalseButton = new JButton("Generate true/false quiz");
        multiButton = new JButton("Generate multiple choice quiz");

        buttonPanel.add(trueOrFalseButton);
        buttonPanel.add(multiButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setupQuestions() {
        questionId = 1;
        buttonGroups = new ArrayList<>();

        for (Question question : questionsList) {
            JPanel questionPanel = new JPanel(new BorderLayout());
            questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));

            JLabel query = new JLabel(questionId++ + ". " + question.getQuestion());
            questionPanel.add(query, BorderLayout.NORTH);

            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroups.add(buttonGroup);

            JPanel alternativesPanel = new JPanel();
            alternativesPanel.setLayout(new BoxLayout(alternativesPanel, BoxLayout.Y_AXIS));
            questionPanel.add(alternativesPanel, BorderLayout.CENTER);

            for (String alternative : question.getAlternatives()) {
                JRadioButton checkBox = new JRadioButton(alternative);
                checkBox.setActionCommand(alternative);
                buttonGroup.add(checkBox);
                alternativesPanel.add(checkBox);
            }
            quizPanel.add(questionPanel);
        }
        quizPanel.add(submitButton);
    }


    public void getUserAnswers() {
        int counter = 0;
        for (ButtonGroup buttonGroup : buttonGroups) {
            Question currentQuestion = questionsList.get(counter);
            String currentAnswer = buttonGroup.getSelection().getActionCommand();

            currentQuiz.addUserAnswer(currentQuestion, currentAnswer);
            counter++;
        }
    }

    public void getTotalPoints() {
        totalPoints = currentQuiz.getTotalPoints();
        quizFrame.removeAll();


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
        quizFrame.setSize(500, 500);

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
        questionsList = currentModule.generateTrueOrFalseQuiz("C:\\Users\\ahmad\\Documents\\GitHub\\Quizzr\\src\\model\\files\\course2\\module1\\trueFalse_questions.txt",
                10);
    }

    public void generateMultipleChoice() {
        currentQuiz = new Quiz("Multiple Choice");
        questionsList = currentModule.generateMultipleChoiceQuiz("src/model/files/DA339A/inheritance_and_polymorphism/multiChoice-questions.txt", 10);
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
    }
}