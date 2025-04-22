package view.main;

import controller.Controller;
import model.User;
import view.subPanels.LogInFrame;

import javax.swing.*;
import java.awt.*;

/**
Main view-class responsible for initializing other view-classes and also responsible for contact with the Controller class.
@author Ahmad Maarouf
 */
public class MainFrame extends JFrame implements Runnable {

    private CenterPanel centerContainer;
    private LeftPanel leftPanel;
    private RightPanel rightPanel;
    private Controller controller;
    private boolean isLoggedIn = false;


    public MainFrame(Controller controller){
        this.controller = controller;
    }

    private void createAndShowGUI() {
        this.setTitle("QuizR");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 400);

        //Login page is initialized here.
        LogInFrame logInFrame = new LogInFrame(this);

        /*
        Container for all the different panels in the center is initialized here.
        Which information is displayed will depend on which tab is chosen in rightPanel.
         */
        centerContainer = new CenterPanel(this);

        /*
        Right panel containing the list of available tabs is initialized here.
         */
        rightPanel = new RightPanel(this, centerContainer);

        /*
        Left panel containing the list of available programs, as well as list of available courses is initialized here.
        The panel displayed in the center when the "Modules" tab is chosen is sent in as a parameter.
        This is because the information for the center panel is dependent on what course is chosen in the left panel.
         */
        leftPanel = new LeftPanel(centerContainer.getModulePanel(), this);

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

    public String[] getCoursesNames(String selectedProgramName){
        return controller.getCoursesNames(selectedProgramName);
    }

    public String[] getProgramsNames(){
        return controller.getProgramsNames();
    }

    public String[] getModulesNames(String selectedCourse){
        return controller.getModulesNames(selectedCourse);
    }

    public void addProgramToProgramList(String programName){
        controller.addProgramToProgramList(programName);
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

    public boolean deleteConfirmation(String selectedItem) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + selectedItem + "?", "Please confirm", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            return true;
        }
        else {
            return false;
        }
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

    @Override
    public void run() {
        createAndShowGUI();
    }
}




