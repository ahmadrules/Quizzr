package view.subPanels.Quiz;

import model.Matching;
import model.Module;
import model.Question;
import model.Quiz;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MainQuizFrame extends JFrame {
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
    private JButton startQuizButton;
    private ArrayList<Question> questionsList;
    private JList<String> availableQuizList;
    private String[] quizNames;
    private int timerAmount;
    private ArrayList<Quiz> quizList;
    private HashMap<Quiz, ArrayList<Question>> quizQuestions;
    int counterMC = 0;
    int counterTF = 0;
    int counterMA = 0;
    int counterQL = 0;

    public MainQuizFrame(String selectedProgram, String selectedCourse, String selectedModule, MainFrame mainFrame) {
        this.selectedProgram = selectedProgram;
        this.selectedCourse = selectedCourse;
        this.selectedModule = selectedModule;
        this.mainFrame = mainFrame;

        setTitle("Quiz Panel");
        setLayout();
        fetchModule();
        createList();
        createButtonPanel();
        addActionListeners();
        setVisible(true);
    }

    public ArrayList<Question> getQuestions(Quiz quiz) {
        return quizQuestions.get(quiz);
    }

    public void setLayout() {
        setLayout(new BorderLayout());
        setSize(700, 200);

        JLabel quizLabel = new JLabel("Available quiz", SwingConstants.CENTER);
        add(quizLabel, BorderLayout.NORTH);
    }

    public void createList() {
        quizNames = new String[10];
        DefaultListModel listModel = new DefaultListModel();
        availableQuizList = new JList(listModel);
        add(availableQuizList, BorderLayout.CENTER);
    }


    public void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        trueOrFalseButton = new JButton("Generate true/false quiz");
        multiButton = new JButton("Generate multiple choice quiz");
        matchingButton = new JButton("Generate matching quiz");
        startQuizButton = new JButton("Start quiz");

        buttonPanel.add(startQuizButton);
        buttonPanel.add(trueOrFalseButton);
        buttonPanel.add(multiButton);
        buttonPanel.add(matchingButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void showQuiz() {
        JFrame quizFrame = new QuestionFrame(quizQuestions.get(currentQuiz), currentQuiz, this);
    }

    public void fetchModule() {
        currentModule = mainFrame.getCurrentModule(selectedProgram, selectedCourse, selectedModule);
    }

    public void generateTrueOrFalse() {
        String quizName = "True or False " + counterTF++;
        quizNames[counterQL++] = quizName;

        currentQuiz = new Quiz(quizName);
        questionsList = currentModule.generateTrueOrFalseQuiz("src/model/files/course2/module1/trueFalse_questions.txt", 10);
        quizQuestions.put(currentQuiz, questionsList);

        updateList();
    }

    public void generateMultipleChoice() {
        String quizName = "Multiple Choice " + counterMC++;
        quizNames[counterQL++] = quizName;

        currentQuiz = new Quiz(quizName);
        questionsList = currentModule.generateMultipleChoiceQuiz("src/model/files/course2/module1/multiChoice_questions.txt", 10);
        quizQuestions.put(currentQuiz, questionsList);

        updateList();
    }

    public void generateMatching() {
        String quizName = "Matching " + counterMA++;
        quizNames[counterQL++] = quizName;

        currentQuiz = new Quiz(quizName);
        questionsList = currentModule.generateMatchingQuiz("src/model/files/course2/module1/matching_questions.txt", 10);
        quizQuestions.put(currentQuiz, questionsList);

        updateList();
    }

    public void updateList() {
        availableQuizList.setListData(quizNames);
        revalidate();
        repaint();
    }

    public void addActionListeners() {
        trueOrFalseButton.addActionListener(e -> {
            generateTrueOrFalse();
        });

        multiButton.addActionListener(e -> {
            generateMultipleChoice();
        });

        matchingButton.addActionListener(e -> {
            generateMatching();
        });

        availableQuizList.addListSelectionListener(e -> {
           if (!e.getValueIsAdjusting()) {
               String selectedQuiz = availableQuizList.getSelectedValue();
               quizList.forEach(
                 quiz -> {if (quiz.getName() == selectedQuiz) {
                     currentQuiz = quiz;
                     questionsList = (ArrayList<Question>) currentQuiz.getQuestions();
                 }}
               );
           }
        });

        startQuizButton.addActionListener(e -> {
            showQuiz();
        });
    }

}