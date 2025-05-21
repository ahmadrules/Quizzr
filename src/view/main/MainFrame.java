package view.main;

import controller.Controller;
import model.*;
import model.Module;
import view.subPanels.LogInFrame;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.awt.*;
import java.util.Map;

/**
Main view-class responsible for initializing other view-classes and also responsible for contact with the Controller class.
@author Ahmad Maarouf
 */
public class MainFrame extends JFrame {

    private CenterPanel centerContainer;
    private LeftPanel leftPanel;
    private RightPanel rightPanel;
    private Controller controller;


    public MainFrame(Controller controller){
        this.controller = controller;
    }

    public void createAndShowGUI(boolean isAdmin) {
        this.setTitle("QuizR");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 400);

        if (isAdmin) {
            setSize(1000, 400);
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
        leftPanel = new LeftPanel(centerContainer.getModulePanel(), this, isAdmin);

        /*
        Here we create a JSplitPane to give some freedom of movement between the left panel and the center container.
        CenterContainer holds all center panels.
         */
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, centerContainer);
        splitPane.setDividerLocation(200);


        this.add(splitPane, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);
        this.setVisible(true);
    }

    public void startLogin() {
        //Login page is initialized here.
        LogInFrame logInFrame = new LogInFrame(controller);
    }

    public void saveTrueOrFalseQuestion(String query, List<String> alternatives, int points, String correctAnswer, String selectedCourse, String moduleName) {
        controller.saveTrueOrFalseQuestion(query, alternatives, points, correctAnswer, selectedCourse, moduleName);
    }

    public void saveMultipleChoiceToFile(String query, List<String> alternatives, int points, String correctAnswer, String selectedCourse, String moduleName) {
        controller.saveMultipleChoiceToFile(query, alternatives, points, correctAnswer, selectedCourse, moduleName);
    }

    public void saveMatchingToFile(String query, List<String> statements, List<String> matches, int points, HashMap<String,Integer> correctMatches, String courseName, String moduleName) {
        controller.saveMatchingToFile(query, statements, matches, points, correctMatches, courseName, moduleName);
    }


    public String[] getCoursesNames(String selectedProgramName){
        return controller.getCoursesNames(selectedProgramName);
    }

    public String[] getProgramsNames(){
        return controller.getProgramsNames();
    }

    public String[] getModulesNames(String selectedCourse){
        return controller.getModulesNames(selectedCourse);
    }

    public void addProgramToProgramList(String programName, String programCode){
        controller.addProgramToProgramList(programName,programCode);
    }
    public void deleteProgram(String programName){
        controller.deleteProgramFromFile(programName);
    }
    public void addNewCourse(String selectedProgram, String courseName){
        controller.addNewCourse(selectedProgram, courseName);
    }
    public void addNewModule(String courseName, String moduleName){
        controller.addNewModule(courseName, moduleName);
    }
    public void deleteCourse(String programName, String courseName){
        controller.deleteCourse(programName, courseName);
    }
    public void deleteModule(String courseName, String moduleName){
        controller.deleteModule(courseName, moduleName);
    }
    public void editProgramName(String oldProgramName, String updatedProgramName){
        controller.editProgramName(oldProgramName, updatedProgramName);
    }
    public void editCourseName(String oldCourseName, String updatedCourseName){
        controller.editCourseName(oldCourseName, updatedCourseName);
    }
    public void editModuleName(String courseName, String oldModuleName, String updatedModuleName){
        controller.editModuleName(courseName, oldModuleName, updatedModuleName);
    }

    public boolean ifProgramExists(String programName){
        return controller.ifProgramExists(programName);
    }

    public boolean ifCourseExists(String programName, String courseName){
        return controller.ifCourseExists(programName, courseName);
    }

    public boolean ifModuleExists(String programName, String courseName, String moduleName){
        return controller.ifModuleExists(programName, courseName, moduleName);
    }

    public Module getCurrentModule(String programName, String courseName, String moduleName){
        return controller.getModule(programName, courseName, moduleName);
    }

    public boolean deleteConfirmation(String selectedItem) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + selectedItem + "?", "Please confirm", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            return true;
        }
        else {
            return false;
        }
    }

    
    public List<FlashCard> getFlashcards(String program, String course, String module) {
        return null;//(List<FlashCard>) controller.getFlashcards(program, course, module);
    }

    public List<Quiz> getCurrentUserHistory() {
        return controller.getCurrentUsersQuizHistory();
    }
    
    
    public String[] getCurrentUserInfo() {
        return controller.getCurrentUserInfo();
    }

    public void setNewUsername(String username) {
        controller.setNewUsername(username);
    }

    public void setNewEmail(String email) {
        controller.setNewEmail(email);
    }

    public void setNewPassword(String password) {
        controller.setNewPassword(password);
    }

    public boolean isEmailValid(String email) {
        return controller.isEmailValid(email);
    }
    public boolean registerNewUser(String username, String password, String email, String programCode) {
        return controller.registerNewUser(username, password, email, programCode);
    }
    public boolean loginUser(String username, String password) {
        return controller.loginUser(username, password);
    }

    public void logOut() {
        controller.logoutUser();
    }

    public void setQuizAsDone(boolean done){
        controller.setQuizDone(done);
    }

    public List<String> getFlashCardsFrontContent(String selectedCourse, String selectedModule){
        return controller.getFlashCardsFrontContent(selectedCourse, selectedModule);
    }

    public List<String> getFlashCardsBackContent(String selectedCourse, String selectedModule){
        return controller.getFlashCardsBackContent(selectedCourse, selectedModule);
    }

    public List<Quiz> getUsersHistoryQuizzes(){
        return controller.getUsersHistoryQuizzes();
    }
    public List<Quiz> getUsersAvailableQuizes(){
        return controller.getUsersAvailableQuizzes();
    }
    public void addQuizToAvailableQuizzes(Quiz quiz){
        controller.addQuizToAvailableQuizzes(quiz);
    }
    public void addQuizToHistory(String quizName, List<Question> questions, Map<Question, String> answers){
        controller.addQuizToHistory(quizName, questions, answers);
    }
    public List<String> getQuizNames() {
        return controller.getAvailableQuizNames();
    }
    public List<String> getHistoryQuizNames() {
        return controller.getHistoryQuizNames();
    }

    public void deleteQuiz(String quizName) {
        controller.deleteQuiz(quizName);
    }

    public Quiz findQuiz (String quizName) {
        return controller.findQuiz(quizName);
    }

    public Quiz findHistoryQuiz (String quizName) {
        return controller.findHistoryQuiz(quizName);
    }

    public void clearHistory() {
        controller.clearHistory();
    }

    public void clearCreatedQuiz() {
        controller.clearCreatedQuiz();
    }
}




