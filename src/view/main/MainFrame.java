package view.main;

import controller.Controller;
import model.User;
import view.subPanels.LogInFrame;

import javax.swing.*;
import java.awt.*;

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

        LogInFrame logInFrame = new LogInFrame(this);

        //Container for all the different panels in the center
        //Which panel is displayed will depend on which tab is chosen in rightPanel
        centerContainer = new CenterPanel(this);

        // ======= RIGHT PANEL =======
        rightPanel = new RightPanel(this, centerContainer);

        // ======= LEFT PANEL =======
        leftPanel = new LeftPanel(centerContainer.getModulePanel(), this);


        // ======= LAYOUT SETUP =======
        // centerContainer holds top and all center panels
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




