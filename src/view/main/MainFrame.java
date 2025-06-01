package view.main;

import controller.Controller;
import model.*;
import model.Module;
import view.subPanels.Quiz.MainQuizFrame;
import view.subPanels.Quiz.QuestionFrame;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.awt.*;
import java.util.Map;

/**
 * Main view-class responsible for initializing other view-classes and also responsible for contact with the Controller class.
 *
 * @author Ahmad Maarouf
 */
public class MainFrame extends JFrame {
    private CenterPanel centerContainer;
    private LeftPanelAdmin leftPanelAdmin;
    private LeftPanelStudent leftPanelStudent;
    private RightPanel rightPanel;
    private Controller controller;

    public MainFrame(Controller controller) {
        this.controller = controller;
    }

    /**
     * Creates and shows the mainFrame view.
     * View changes depending on if the user that logged in is an admin or not.
     *
     * @param isAdmin if the user logged in is an admin or not
     */
    public void createAndShowGUI(boolean isAdmin) {
        this.setTitle("QuizR");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(850, 400);

        if (isAdmin) {
            setSize(1050, 400);
        }
        setLocationRelativeTo(null);

        /*
        Container for all the different panels in the center is initialized here.
        Which information is displayed will depend on which tab is chosen in rightPanel.
         */
        centerContainer = new CenterPanel(this, isAdmin);

        /*
        Right panel containing the list of available tabs is initialized here.
         */
        rightPanel = new RightPanel(this, centerContainer);

        /*
        Left panel containing the list of available programs, as well as list of available courses is initialized here.
        The panel displayed in the center when the "Modules" tab is chosen is sent in as a parameter.
        This is because the information for the center panel is dependent on what course is chosen in the left panel.
         */
        leftPanelAdmin = new LeftPanelAdmin(centerContainer.getModulePanel(), this);
        leftPanelStudent = new LeftPanelStudent(centerContainer.getModulePanel(), this);

        /*
        Here we create a JSplitPane to give some freedom of movement between the left panel and the center container.
        CenterContainer holds all center panels.
         */

        JSplitPane splitPane;

        if (isAdmin) {
            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanelAdmin, centerContainer);
        } else {
            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanelStudent, centerContainer);
        }
        splitPane.setDividerLocation(200);

        ImageIcon icon = new ImageIcon(getClass().getResource("/view/pics/Quizzr-logo.png"));
        setIconImage(icon.getImage());

        this.add(splitPane, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);
        this.setVisible(true);
    }

    /**
     * Adds a True/False question to the files through the Controller class
     *
     * @param query          the query of the question
     * @param alternatives   answer alternatives to the question
     * @param points         how many points the question gives with correct answer
     * @param correctAnswer  the correct answer to the question
     * @param selectedCourse the currently selected course
     * @param moduleName     the currently selected module
     */
    public void saveTrueOrFalseQuestion(String query, List<String> alternatives, int points, String correctAnswer, String selectedCourse, String moduleName) {
        controller.saveTrueOrFalseQuestion(query, alternatives, points, correctAnswer, selectedCourse, moduleName);
    }

    /**
     * Adds a multiple choice question to the files through the Controller class
     *
     * @param query          the query of the question
     * @param alternatives   answer alternatives to the question
     * @param points         how many points the question gives with correct answer
     * @param correctAnswer  the correct answer to the question
     * @param selectedCourse the currently selected course
     * @param moduleName     the currently selected module
     */
    public void saveMultipleChoiceToFile(String query, List<String> alternatives, int points, String correctAnswer, String selectedCourse, String moduleName) {
        controller.saveMultipleChoiceToFile(query, alternatives, points, correctAnswer, selectedCourse, moduleName);
    }

    /**
     * Adds a matching question to the files through the Controller class
     *
     * @param query          the query of the question
     * @param statements     the first row of statements
     * @param matches        the potential matches to the statements
     * @param points         how many points the question gives with correct answer
     * @param correctMatches the correct matches/correct answer
     * @param courseName     the currently selected course
     * @param moduleName     the currently selected module
     */
    public void saveMatchingToFile(String query, List<String> statements, List<String> matches, int points, HashMap<String, Integer> correctMatches, String courseName, String moduleName) {
        controller.saveMatchingToFile(query, statements, matches, points, correctMatches, courseName, moduleName);
    }

    /**
     * Returns all the available courses names for a selected programme
     *
     * @param selectedProgramName the currently selected programme
     * @return an array of course names
     */
    public String[] getCoursesNames(String selectedProgramName) {
        return controller.getCoursesNames(selectedProgramName);
    }

    /**
     * Returns all the available programme names
     *
     * @return an array of programme names
     */
    public String[] getProgramsNames() {
        return controller.getProgramsNames();
    }

    public List<String> getProgramCodes() {
        return controller.getProgramCodes();
    }

    /**
     * Returns a list of all available modules names for a selected course
     *
     * @param selectedCourse the currently selected course
     * @return an array of module names
     */
    public String[] getModulesNames(String selectedCourse) {
        return controller.getModulesNames(selectedCourse);
    }

    /**
     * Adds a program to the files through the Controller class
     *
     * @param programName the name of the new program
     * @param programCode the program code of the new program
     */
    public void addProgramToProgramList(String programName, String programCode) {
        controller.addProgramToProgramList(programName, programCode);
    }

    /**
     * Deletes a program from the files
     * This also deletes all related courses, modules, quiz and flashcards
     *
     * @param programName the name of the program to be deleted
     */
    public void deleteProgram(String programName) {
        controller.deleteProgramFromFile(programName);
    }

    /**
     * Adds a new course to a selected programme
     *
     * @param selectedProgram the currently selected programme
     * @param courseName      the name of the new course
     */
    public void addNewCourse(String selectedProgram, String courseName) {
        controller.addNewCourse(selectedProgram, courseName);
    }

    /**
     * Adds a new module to the selected course
     *
     * @param courseName name of the selected course
     * @param moduleName name of the new module
     */
    public void addNewModule(String courseName, String moduleName) {
        controller.addNewModule(courseName, moduleName);
    }

    /**
     * Deletes a course from a selected programme
     *
     * @param programName name of the selected programme
     * @param courseName  name of the course to be deleted
     */
    public void deleteCourse(String programName, String courseName) {
        controller.deleteCourse(programName, courseName);
    }

    /**
     * Deletes a module from a selected course
     *
     * @param courseName name of the selected course
     * @param moduleName name of the module to be deleted
     */
    public void deleteModule(String courseName, String moduleName) {
        controller.deleteModule(courseName, moduleName);
    }

    /**
     * Edits the name of a programme
     *
     * @param oldProgramName     the previous programme name
     * @param updatedProgramName the new name of the programme
     */
    public void editProgramName(String oldProgramName, String updatedProgramName) {
        controller.editProgramName(oldProgramName, updatedProgramName);
    }

    /**
     * Edits the name of a course
     *
     * @param oldCourseName     the previous course name
     * @param updatedCourseName the new name of the course
     */
    public void editCourseName(String oldCourseName, String updatedCourseName) {
        controller.editCourseName(oldCourseName, updatedCourseName);
    }

    /**
     * Edits the name of a module
     *
     * @param courseName        the cname of the course the module belongs to
     * @param oldModuleName     the previous name of the module
     * @param updatedModuleName the new name of the module
     */
    public void editModuleName(String courseName, String oldModuleName, String updatedModuleName) {
        controller.editModuleName(courseName, oldModuleName, updatedModuleName);
    }

    /**
     * Checks if a programme already exists
     *
     * @param programName the name of the progarmme to be checked
     * @return boolean
     */
    public boolean ifProgramExists(String programName) {
        return controller.ifProgramExists(programName);
    }

    /**
     * Checks if a course already exists
     *
     * @param programName the name of the programme the course to be checked belongs to
     * @param courseName  the name of the course to be checked
     * @return boolean
     */
    public boolean ifCourseExists(String programName, String courseName) {
        return controller.ifCourseExists(programName, courseName);
    }

    /**
     * Checks if a module already exists
     *
     * @param programName the name of the programme the course to be checked belongs to
     * @param courseName  the name of the course to be checked
     * @param moduleName  the name of the module to be checked
     * @return boolean
     */
    public boolean ifModuleExists(String programName, String courseName, String moduleName) {
        return controller.ifModuleExists(programName, courseName, moduleName);
    }

    /**
     * Returns an instance of Module connected to a specific module name
     *
     * @param programName name of the programme the module belongs to
     * @param courseName  name of the course the module belongs to
     * @param moduleName  name of the module to be returned
     * @return an instance of Module
     */
    public Module getCurrentModule(String programName, String courseName, String moduleName) {
        return controller.getModule(programName, courseName, moduleName);
    }

    /**
     * Confirms with the user if they want to delete a specific item
     *
     * @param selectedItem the name of the item to be deleted
     * @return boolean
     */
    public boolean deleteConfirmation(String selectedItem) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + selectedItem + "?", "Please confirm", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }


    public List<FlashCard> getFlashcards(String program, String course, String module) {
        return null;//(List<FlashCard>) controller.getFlashcards(program, course, module);
    }

    /**
     * Returns a list of instances of Quiz that have answers already added to them
     * This is used to show the user the result of quiz they've completed previously
     *
     * @return a list completed quiz
     */
    public List<Quiz> getCurrentUserHistory() {
        return controller.getCurrentUsersQuizHistory();
    }

    /**
     * Returns information about a user
     *
     * @return an array of user information
     */
    public String[] getCurrentUserInfo() {
        return controller.getCurrentUserInfo();
    }

    /**
     * Used to change the username of a logged-in user
     *
     * @param username the new username
     */
    public void setNewUsername(String username) {
        controller.setNewUsername(username);
    }

    /**
     * Used to change the email connected to the logged-in user
     *
     * @param email the new email
     */
    public void setNewEmail(String email) {
        controller.setNewEmail(email);
    }

    /**
     * Used to change the password of a logged-in user
     *
     * @param password the new password
     */
    public void setNewPassword(String password) {
        controller.setNewPassword(password);
    }

    /**
     * Checks if the given email is a valid email
     *
     * @param email the email to be checked
     * @return boolean
     */
    public boolean isEmailValid(String email) {
        return controller.isEmailValid(email);
    }

    /**
     * Creates a new account in the files with information given by the current user
     *
     * @param username    chosen username
     * @param password    chosen password
     * @param email       chosen email
     * @param programCode program code of the program the user is registered to
     * @return boolean that indicates if the account was successfully registered
     */
    public boolean registerNewUser(String username, String password, String email, String programCode) {
        return controller.registerNewUser(username, password, email, programCode);
    }

    public boolean loginUser(String username, String password) {
        return controller.loginUser(username, password);
    }

    /**
     * Logs out the user
     */
    public void logOut() {
        controller.logoutUser();
    }

    public void setQuizAsDone(boolean done) {
        controller.setQuizDone(done);
    }

    public List<String> getFlashCardsFrontContent(String selectedCourse, String selectedModule) {
        return controller.getFlashCardsFrontContent(selectedCourse, selectedModule);
    }

    public List<String> getFlashCardsBackContent(String selectedCourse, String selectedModule) {
        return controller.getFlashCardsBackContent(selectedCourse, selectedModule);
    }

    public List<Quiz> getUsersHistoryQuizzes() {
        return controller.getUsersHistoryQuizzes();
    }

    public List<Quiz> getUsersAvailableQuizes() {
        return controller.getUsersAvailableQuizzes();
    }

    public void addQuizToAvailableQuizzes(Quiz quiz) {
        controller.addQuizToAvailableQuizzes(quiz);
    }

    public void addQuizToHistory(String quizName, List<Question> questions, Map<Question, String> answers, String relatedModule, String relatedQuiz) {
        controller.addQuizToHistory(quizName, questions, answers, relatedModule, relatedQuiz);
    }

    public List<String> getQuizNames() {
        return controller.getAvailableQuizNames();
    }

    public List<String> getHistoryQuizNames() {
        return controller.getHistoryQuizNames();
    }

    public void deleteQuiz(String quizName, String relatedModule, String relatedCourse) {
        controller.deleteQuiz(quizName, relatedModule, relatedCourse);
    }

    public Quiz findQuiz(String quizName, String relatedModule, String relatedCourse) {
        return controller.findQuiz(quizName, relatedModule, relatedCourse);
    }

    public Quiz findHistoryQuiz(String quizName, String relatedModule, String relatedCourse) {
        return controller.findHistoryQuiz(quizName, relatedModule, relatedCourse);
    }

    public void clearHistory(String selectedModule, String selectedCourse) {
        controller.clearHistory(selectedModule, selectedCourse);
    }

    public void clearCreatedQuiz(String selectedModule, String selectedCourse) {
        controller.clearCreatedQuiz(selectedModule, selectedCourse);
    }

    public String getCurrentUserProgram() {
        return controller.getCurrentStudentProgramName();
    }

    public void addUserAnswer(Question question, String answer) {

    }

    public List<String> getRelatedQuizNames(String relatedModule, String relatedCourse) {
        return controller.getRelatedQuiz(relatedModule, relatedCourse);
    }

    public List<String> getRelatedHistoryQuizNames(String relatedModule, String relatedCourse) {
        return controller.getRelatedHistoryQuiz(relatedModule, relatedCourse);
    }

    public void generateQuiz(int amountOfQuestions, String quizName, String typeOfQuiz, long timerSeconds, String relatedModule, String relatedCourse) {
        controller.generateQuiz(amountOfQuestions, quizName, typeOfQuiz, timerSeconds, relatedModule, relatedCourse);
    }

    public String getStudentProgramName() {
        return controller.getCurrentStudentProgramName();
    }

    public void changeProfilePicture(String selectedPicPath) {
        controller.changeProfilePicture(selectedPicPath);
    }

    public String getUserProfilePicturePath() {
        return controller.getUserProfilePicturePath();
    }

    public void generateGeneralQuiz() {
        Quiz quiz = controller.generateGeneralCourseQuiz(leftPanelStudent.getSelectedCourse());
        new QuestionFrame(quiz, this);
    }

    /**
     * Saves a flashcard object for the currently logged-in user.
     *
     * @param flashCard the FlashCard to save
     * @author Salman Warsame
     */
    public void saveUserFlashCard(FlashCard flashCard) {
        controller.saveUsersFlashCards(flashCard);
    }
    /**
     * Fetches a Course object based on the given program and course name.
     * Delegates the logic to the Controller class.
     *
     * @param programName the name of the program the course belongs to
     * @param courseName the name of the course
     * @return a Course object matching the given program and course
     */
    public Course getCourseByName(String programName, String courseName) {
        return controller.getCourseByName(programName, courseName);
    }
    /**
     * Returns the name of the program currently associated with the logged-in student.
     *
     * @return program name of the current student user
     * @author Salman Warsame
     */
    public String getCurrentStudentProgramName() {
        return controller.getCurrentStudentProgramName();
    }
}




