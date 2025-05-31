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
 *
 * @author Ahmad Maarouf
 */
public class MainQuizFrame extends JFrame {
    private String selectedModule;
    private String selectedCourse;

    private MainFrame mainFrame;

    private AvailableQuizPanel availableQuizPanel;
    private HistoryPanel historyPanel;
    private JPanel availableWrapperPanel;
    private JPanel historyWrapperPanel;
    private JPanel currentCenterPanel;
    private TabPanel tabPanel;

    private List<Quiz> quizList;
    private List<Quiz> historyList;
    private Quiz currentQuiz;

    public MainQuizFrame(String selectedCourse, String selectedModule, MainFrame mainFrame) {
        this.selectedCourse = selectedCourse;
        this.selectedModule = selectedModule;
        this.mainFrame = mainFrame;

        createLists();
        createPanels();
        setLayout();

        //Default panel is available quiz
        setAvailableQuizPanel();

        setTitle("Quiz Panel");

        addTabList();
        setLocationRelativeTo(mainFrame);

        ImageIcon icon = new ImageIcon(getClass().getResource("/view/pics/Quizzr-logo.png"));
        setIconImage(icon.getImage());

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    /**
     * Initializes components for storing information about quiz
     *
     * @author Ahmad Maarouf
     */
    public void createLists() {
        quizList = new ArrayList<>();
        historyList = new ArrayList<>();
    }

    /**
     * Creates the two panels that can be displayed in this frame
     *
     * @author Ahmad Maarouf
     */
    public void createPanels() {
        historyPanel = new HistoryPanel(this);
        availableQuizPanel = new AvailableQuizPanel(this, mainFrame);

        historyWrapperPanel = warpWithPadding(historyPanel);
        availableWrapperPanel = warpWithPadding(availableQuizPanel);

    }

    /**
     * Sets the currently displayed panel to the history panel
     *
     * @author Ahmad Maarouf
     */
    public void setHistoryPanel() {
        if (currentCenterPanel != null) {
            getContentPane().remove(currentCenterPanel);
        }
        historyPanel.updateList();
        add(historyWrapperPanel, BorderLayout.CENTER);
        currentCenterPanel = historyWrapperPanel;
        revalidate();
        repaint();
    }

    /**
     * Sets the currently displayed panel to the available quiz panel
     *
     * @author Ahmad Maarouf
     */
    public void setAvailableQuizPanel() {
        if (currentCenterPanel != null) {
            getContentPane().remove(currentCenterPanel);
        }
        availableQuizPanel.updateList();
        add(availableWrapperPanel, BorderLayout.CENTER);
        currentCenterPanel = availableWrapperPanel;
        revalidate();
        repaint();
    }

    /**
     * Sets the layout and size of the frame
     *
     * @author Ahmad Maarouf
     */
    public void setLayout() {
        setLayout(new BorderLayout());
        setSize(800, 400);
    }

    /**
     * Adds the tabPanel on the east side of the frame
     * The selected item in this tab decides which panel is displayed in the center
     *
     * @author Ahmad Maarouf
     */
    public void addTabList() {
        tabPanel = new TabPanel(this);
        tabPanel.setPreferredSize(new Dimension(200, 250));
        tabPanel.setMaximumSize(new Dimension(200, 250));
        tabPanel.setMinimumSize(new Dimension(200, 250));
        tabPanel.setBackground(new Color(255, 249, 163));
        add(tabPanel, BorderLayout.EAST);
    }

    /**
     * Displays a currently selected quiz and its questions
     *
     * @author Ahmad Maarouf
     */
    public void showQuiz() {
        new QuestionFrame(currentQuiz, this, false);
    }

    public void clearHistory() {
        mainFrame.clearHistory(selectedModule, selectedCourse);
    }

    public void clearCreatedQuiz() {
        mainFrame.clearCreatedQuiz(selectedModule, selectedCourse);
    }

    /**
     * Generates a quiz with attributes depending on what the user has chosen
     *
     * @param amountOfQuestions the amount of questions in the quiz
     * @param quizName          the name of the quiz
     * @param typeOfQuiz        the type of quiz (True/False, Multiple choice or Matching)
     * @param timerSeconds      the amount of time for the timer(0 if no timer)
     * @author Ahmad Maarouf
     */
    public void generateQuiz(int amountOfQuestions, String quizName, String typeOfQuiz, long timerSeconds) {
        mainFrame.generateQuiz(amountOfQuestions, quizName, typeOfQuiz, timerSeconds, selectedModule, selectedCourse);
        updateList();
    }

    /**
     * Finds the quiz instance that needs to be started
     *
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
     *
     * @param selectedQuiz name of the quiz to be deleted
     * @author Ahmad Maarouf
     */
    public void deleteQuiz(String selectedQuiz) {
        mainFrame.deleteQuiz(selectedQuiz, selectedModule, selectedCourse);
        updateList();
    }

    /**
     * Updates both the list of available quiz and completed quiz
     *
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
     *
     * @return a list of quiz that have been completed
     * @author Ahmad Maarouf
     */
    public List<Quiz> getHistoryList() {
        historyList = mainFrame.getCurrentUserHistory();
        return historyList;
    }

    /**
     * Fetches the list of available quiz
     *
     * @return a list of generated quiz
     * @author Ahmad Maarouf
     */
    public List<Quiz> getQuizList() {
        quizList = mainFrame.getUsersAvailableQuizes();
        return quizList;
    }

    public Quiz findHistoryQuiz(String quizName) {
        return mainFrame.findHistoryQuiz(quizName, selectedModule, selectedCourse);
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

    public JPanel warpWithPadding(JPanel innerpanel) {
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBackground(new Color(255, 249, 163));
        outerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        outerPanel.add(innerpanel, BorderLayout.CENTER);
        return outerPanel;
    }
}