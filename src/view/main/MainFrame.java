package view.main;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements Runnable {

    private CenterPanel centerContainer;
    private LeftPanel leftPanel;
    private RightPanel rightPanel;
    private Controller controller;

    public MainFrame(Controller controller){
        this.controller = controller;
    }

    private void createAndShowGUI() {
        this.setTitle("QuizR");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 400);

        //LogInFrame logInFrame = new LogInFrame(this);

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

    public boolean deleteConfirmation(String selectedItem) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + selectedItem + "?", "Please confirm", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void run() {
        createAndShowGUI();
    }
}




