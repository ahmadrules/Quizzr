package view.main;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MainFrame implements Runnable {

    private JPanel topPanel;
    private LeftPanel leftPanel;
    private RightPanel rightPanel;
    private HashMap<String, String[]> moduleList;

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Quizzr");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);

        // ======= TOP PANEL =======
        JPanel topPanel = new TopPanel();

        // ======= RIGHT PANEL =======
        RightPanel rightPanel = new RightPanel();

        // ======= LEFT PANEL =======
        LeftPanel leftPanel = new LeftPanel(rightPanel);

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

    @Override
    public void run() {
        createAndShowGUI();
    }
}




