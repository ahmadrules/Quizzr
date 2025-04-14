package view.main;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MainFrame extends JFrame implements Runnable {

    private CenterPanel centerContainer;
    private LeftPanel leftPanel;
    private RightPanel rightPanel;
<<<<<<< Updated upstream
    private HashMap<String, String[]> moduleList;

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Quizzr");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);
=======
    private Controller controller;

    public MainFrame(Controller controller){
        this.controller = controller;
    }

    private void createAndShowGUI() {
        this.setTitle("Quizzr");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 400);
>>>>>>> Stashed changes

        //Container for all the different panels in the center
        //Which panel is displayed will depend on which tab is chosen in rightPanel
        centerContainer = new CenterPanel(this);

        // ======= RIGHT PANEL =======
<<<<<<< Updated upstream
        RightPanel rightPanel = new RightPanel();

        // ======= LEFT PANEL =======
        LeftPanel leftPanel = new LeftPanel(rightPanel);

        //Label and list of right panel
        JPanel rightContainer = new JPanel(new BorderLayout());
        rightContainer.add(topPanel, BorderLayout.NORTH);
        rightContainer.add(rightPanel, BorderLayout.CENTER);
=======
        rightPanel = new RightPanel(this, centerContainer);

        // ======= LEFT PANEL =======
        leftPanel = new LeftPanel(centerContainer.getModulePanel(), this);
>>>>>>> Stashed changes

        // ======= LAYOUT SETUP =======
        // centerContainer holds top and all center panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, centerContainer);
        splitPane.setDividerLocation(200);

        this.add(splitPane, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);
        this.setVisible(true);
    }

    @Override
    public void run() {
        createAndShowGUI();
    }
}




