package view.main;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MainFrame implements Runnable {

    private JPanel topPanel;
    private LeftPanel leftPanel;
    private RightPanel rightPanel;
    private HashMap<String, String[]> moduleList;
    private Controller controller;

    public MainFrame(Controller controller){
        this.controller = controller;
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Quizzr");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);

        // ======= TOP PANEL =======
        JPanel topPanel = new TopPanel();

        // ======= RIGHT PANEL =======
        RightPanel rightPanel = new RightPanel(this);

        // ======= LEFT PANEL =======
        LeftPanel leftPanel = new LeftPanel(rightPanel, this);

        //Label and list of right panel
        JPanel rightContainer = new JPanel(new BorderLayout());
        rightContainer.add(topPanel, BorderLayout.NORTH);
        rightContainer.add(rightPanel, BorderLayout.CENTER);

        // ======= LAYOUT SETUP =======
        // rightContainer holds top and right panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightContainer);
        splitPane.setDividerLocation(200);


        frame.add(splitPane);
        frame.setVisible(true);
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

    @Override
    public void run() {
        createAndShowGUI();
    }
}




