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
import java.util.Map;

/**
 * This class is responsible for initializing a frame
 * This frame is responsible for displaying the panels for available quiz and completed quiz(history)
 * Which panel is displayed depends on what is chosen in the tabPanel
 * @author Ahmad Maarouf
 */
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
    private List<Quiz> quizList;
    private List<Quiz> historyList;
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

    /**
     * Initializes components for storing information about quiz
     * @author Ahmad Maarouf
     */
    public void createLists() {
        quizList = new ArrayList<>();
        quizQuestions = new HashMap<>();
        historyList = new ArrayList<>();
    }

    /**
     * Creates the two panels that can be displayed in this frame
     * @author Ahmad Maarouf
     */
    public void createPanels() {
        historyPanel = new HistoryPanel(mainFrame, this);
        availableQuizPanel = new AvailableQuizPanel(this, mainFrame, selectedModule, selectedCourse);
    }

    /**
     * Sets the currently displayed panel to the history panel
     * @author Ahmad Maarouf
     */
    public void setHistoryPanel() {
        availableQuizPanel.disableButtons();
        remove(availableQuizPanel);
        historyPanel.updateList();
        add(historyPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Sets the currently displayed panel to the available quiz panel
     * @author Ahmad Maarouf
     */
    public void setAvailableQuizPanel() {
        historyPanel.disableButtons();
        remove(historyPanel);
        availableQuizPanel.updateList();
        add(availableQuizPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Sets the layout and size of the frame
     * @author Ahmad Maarouf
     */
    public void setLayout() {
        setLayout(new BorderLayout());
        setSize(400, 200);
    }

    /**
     * Adds the tabPanel on the east side of the frame
     * The selected item in this tab decides which panel is displayed in the center
     * @author Ahmad Maarouf
     */
    public void addTabList() {
        TabPanel tabPanel = new TabPanel(this);
        add(tabPanel, BorderLayout.EAST);
    }

    /**
     * Displays a currently selected quiz and its questions
     * @author Ahmad Maarouf
     */
    public void showQuiz() {
        new QuestionFrame(mainFrame, currentQuiz.getQuestions(), currentQuiz, this, timerSeconds, false);
    }

    /**
     * Fetches the currently selected module
     * @author Ahmad Maarouf
     */
    public void fetchModule() {
        currentModule = mainFrame.getCurrentModule(selectedProgram, selectedCourse, selectedModule);
    }

    /**
     * Generates a quiz with attributes depending on what the user has chosen
     * @param amountOfQuestions the amount of questions in the quiz
     * @param quizName the name of the quiz
     * @param typeOfQuiz the type of quiz (True/False, Multiple choice or Matching)
     * @param timerSeconds the amount of time for the timer(0 if no timer)
     * @author Ahmad Maarouf
     */
    public void generateQuiz(int amountOfQuestions, String quizName, String typeOfQuiz, long timerSeconds, String relatedModule, String relatedCourse) {
        mainFrame.generateQuiz(amountOfQuestions, quizName, typeOfQuiz, timerSeconds, selectedModule, selectedCourse);
        updateList();
    }

    /**
     * Finds the quiz instance that needs to be started
     * @param selectedQuiz the name of the selected quiz
     * @author Ahmad Maarouf
     */
    public void startQuiz(String selectedQuiz) {
        currentQuiz = mainFrame.findQuiz(selectedQuiz, selectedModule, selectedCourse);
        showQuiz();
        setVisible(false);
    }

    /**
     * Deletes a selected quiz
     * @param selectedQuiz name of the quiz to be deleted
     * @author Ahmad Maarouf
     */
    public void deleteQuiz(String selectedQuiz, String relatedModule, String relatedCourse) {
        mainFrame.deleteQuiz(selectedQuiz, relatedModule, relatedCourse);
        updateList();
    }

    /**
     * Updates both the list of available quiz and completed quiz
     * @author Ahmad Maarouf
     */
    public void updateList() {
        getHistoryList();
        getQuizList();
        availableQuizPanel.updateList();
        historyPanel.updateList();
    }

    /**
     * Fetches the list of completed quiz
     * @return a list of quiz that have been completed
     * @author Ahmad Maarouf
     */
    public List<Quiz> getHistoryList() {
        historyList = mainFrame.getCurrentUserHistory();
        return historyList;
    }

    /**
     * Fetches the list of available quiz
     * @return a list of generated quiz
     * @author Ahmad Maarouf
     */
    public List<Quiz> getQuizList() {
        quizList = mainFrame.getUsersAvailableQuizes();
        return quizList;
    }

    /**
     * Fetches the names of the available quiz
     * @return a list of quiz names
     */
    public List<String> getQuizNames() {
        return mainFrame.getQuizNames();
    }

    public List<String> getHistoryQuizNames() {
        return mainFrame.getHistoryQuizNames();
    }

    public Quiz findHistoryQuiz(String quizName) {
        return mainFrame.findHistoryQuiz(quizName, selectedModule, selectedCourse);
    }

    public Quiz findQuiz(String quizName, String relatedModule, String relatedCourse) {
        return mainFrame.findQuiz(quizName, relatedModule, relatedCourse);
    }

    public void setQuizAsDone(boolean done) {
        mainFrame.setQuizAsDone(done);
    }

    public List<String> getRelatedQuizNames() {
        return mainFrame.getRelatedQuizNames(selectedModule, selectedCourse);
    }

    public List<String> getRelatedHistoryQuizNames() {
        return mainFrame.getRelatedHistoryQuizNames(selectedModule, selectedCourse);
    }

    public void addQuizToHistory(String quizName, List<Question> questions, Map<Question, String> answers) {
        mainFrame.addQuizToHistory(quizName, questions, answers, selectedModule, selectedCourse);
    }
}