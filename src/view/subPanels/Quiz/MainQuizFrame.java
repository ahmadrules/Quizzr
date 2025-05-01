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
    private List<Question> questionsList;
    private JList<String> availableQuizList;
    private String[] quizNames;
    private DefaultListModel<String> quizListModel;
    private int timerAmount;
    private ArrayList<Quiz> quizList;
    private HashMap<Quiz, List<Question>> quizQuestions;
    int counterMC = 1;
    int counterTF = 1;
    int counterMA = 1;

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

    public List<Question> getQuestions(Quiz quiz) {
        return quizQuestions.get(quiz);
    }

    public void setLayout() {
        setLayout(new BorderLayout());
        setSize(700, 200);

        JLabel quizLabel = new JLabel("Available quiz", SwingConstants.CENTER);
        add(quizLabel, BorderLayout.NORTH);
    }

    public void createList() {
        quizListModel = new DefaultListModel();
        availableQuizList = new JList(quizListModel);

        availableQuizList.setBorder(BorderFactory.createLineBorder(Color.black));
        add(availableQuizList, BorderLayout.CENTER);

        quizQuestions = new HashMap<>();
        quizList = new ArrayList<>();
    }


    public void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        trueOrFalseButton = new JButton("Generate true/false quiz");
        multiButton = new JButton("Generate multiple choice quiz");
        matchingButton = new JButton("Generate matching quiz");
        startQuizButton = new JButton("Start quiz");

        startQuizButton.setEnabled(false);

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

        Quiz newQuiz = new Quiz(quizName);
        quizList.add(newQuiz);

        questionsList = currentModule.generateTrueOrFalseQuiz("src/model/files/course2/module1/trueFalse_questions.txt", 10);
        questionsList.forEach(question -> {newQuiz.addQuestion(question);});
        quizQuestions.put(newQuiz, questionsList);

        updateList(quizName);
    }

    public void generateMultipleChoice() {
        String quizName = "Multiple Choice " + counterMC++;

        Quiz newQuiz = new Quiz(quizName);
        quizList.add(newQuiz);

        questionsList = currentModule.generateMultipleChoiceQuiz("src/model/files/course2/module1/multiChoice_questions.txt", 10);
        questionsList.forEach(question -> {newQuiz.addQuestion(question);});
        quizQuestions.put(newQuiz, questionsList);


        updateList(quizName);
    }

    public void generateMatching() {
        String quizName = "Matching " + counterMA++;

        Quiz newQuiz = new Quiz(quizName);
        quizList.add(newQuiz);

        questionsList = currentModule.generateMatchingQuiz("src/model/files/course2/module1/matching_questions.txt", 10);
        questionsList.forEach(question -> {newQuiz.addQuestion(question);});
        quizQuestions.put(newQuiz, questionsList);

        updateList(quizName);
    }

    public void updateList(String quizName) {
        quizListModel.addElement(quizName);
        revalidate();
        repaint();
    }

    public void addActionListeners() {
        trueOrFalseButton.addActionListener(e -> {
            QuizOptionsPanel quizOptionsPanel = new QuizOptionsPanel();
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
               if (availableQuizList.getSelectedValue() != null) {
                   startQuizButton.setEnabled(true);

                   String selectedQuiz = availableQuizList.getSelectedValue();
                   System.out.println(selectedQuiz);
                   quizList.forEach(
                           quiz -> {if (quiz.getName() == selectedQuiz) {
                               currentQuiz = quiz;
                               questionsList = currentQuiz.getQuestions();
                           }}
                   );
               }
           }
        });

        startQuizButton.addActionListener(e -> {
            showQuiz();
        });
    }

    public void createNewQuiz() {
        String quizName = JOptionPane.showInputDialog("Enter quiz name");


    }

}