package view.subPanels.Quiz;

import model.Module;
import model.Question;
import model.Quiz;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainQuizFrame extends JFrame {
    private Module currentModule;
    private String selectedModule;
    private String selectedCourse;
    private String selectedProgram;

    private MainFrame mainFrame;

    private AvailableQuizPanel availableQuizPanel;
    private HistoryPanel historyPanel;

    private java.util.List<Question> questionsList;
    private HashMap<Quiz, List<Question>> quizQuestions;
    private ArrayList<Quiz> quizList;
    private Quiz currentQuiz;
    private long timerSeconds;

    public MainQuizFrame(String selectedProgram, String selectedCourse, String selectedModule, MainFrame mainFrame) {
        this.selectedProgram = selectedProgram;
        this.selectedCourse = selectedCourse;
        this.selectedModule = selectedModule;
        this.mainFrame = mainFrame;

        fetchModule();
        createPanels();
        setLayout();

        //Default panel is available quiz
        setAvailableQuizPanel();

        setTitle("Quiz Panel");

        createLists();
        addTabList();
        pack();
        setLocationRelativeTo(mainFrame);

        setVisible(true);
    }


    public void createLists() {
        quizList = new ArrayList<>();
        quizQuestions = new HashMap<>();
    }

    public void createPanels() {
        availableQuizPanel = new AvailableQuizPanel(this);
        historyPanel = new HistoryPanel();
    }

    public void setHistoryPanel() {
        remove(availableQuizPanel);
        add(historyPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void setAvailableQuizPanel() {
        remove(historyPanel);
        add(availableQuizPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void setLayout() {
        setLayout(new BorderLayout());
        setSize(400, 200);
    }

    public void addTabList() {
        TabPanel tabPanel = new TabPanel(this);
        add(tabPanel, BorderLayout.EAST);
    }

    public void showQuiz() {
        new QuestionFrame(quizQuestions.get(currentQuiz), currentQuiz, this, timerSeconds);
    }

    public void fetchModule() {
        currentModule = mainFrame.getCurrentModule(selectedProgram, selectedCourse, selectedModule);
    }

    public void generateQuiz(int amountOfQuestions, String quizName, String typeOfQuiz, long timerSeconds) {
        Quiz newQuiz = new Quiz(quizName);
        quizList.add(newQuiz);

        this.timerSeconds = timerSeconds;

        if (typeOfQuiz.equals("Matching")) {
            questionsList = currentModule.generateMatchingQuiz(amountOfQuestions);
        }
        else if (typeOfQuiz.equals("True/False")) {
            questionsList = currentModule.generateTrueOrFalseQuiz(amountOfQuestions);
        }
        else if (typeOfQuiz.equals("Multiple choice")) {
            questionsList = currentModule.generateMultipleChoiceQuiz(amountOfQuestions);
        }

        questionsList.forEach(question -> {
            newQuiz.addQuestion(question);

        });
        quizQuestions.put(newQuiz, questionsList);

        updateList(quizName);

    }

    public void startQuiz(String selectedQuiz) {
        quizList.forEach(
                quiz -> {if (quiz.getName() == selectedQuiz) {
                    currentQuiz = quiz;
                    questionsList = currentQuiz.getQuestions();
                }}
        );
        showQuiz();
        setVisible(false);
    }

    public void updateList(String quizName) {
        availableQuizPanel.updateList(quizName);
    }

    public void setQuizAsDone(boolean done) {
        mainFrame.setQuizAsDone(done);
    }

}