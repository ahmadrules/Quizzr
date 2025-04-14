package view.main;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
    private MainFrame mainFrame;
    private CenterPanel centerContainer;
    private String[] tabOptions;
    private JList<String> listOfTabs;
    private JScrollPane scrollPane;

    public RightPanel(MainFrame mainFrame, CenterPanel centerContainer) {
        this.mainFrame = mainFrame;
        this.centerContainer = centerContainer;

        createList();
        JButton logOutButton = new JButton("Log Out");
        logOutButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel tabLabel = new JLabel(" Select a tab");
        tabLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tabLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.setLayout(new BorderLayout());
        this.setSize(150, 400);

        this.add(tabLabel, BorderLayout.NORTH);
        this.add(scrollPane);
        this.add(logOutButton, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public void createList() {
        //tabOptions will be fetched from controller
        String[] tabOptions = {"Modules", "Account", "Quiz"};
        //-----------------------------------------

        listOfTabs = new JList<>(tabOptions);

        listOfTabs.setFixedCellWidth(150);
        listOfTabs.setFont(new Font("Arial", Font.ROMAN_BASELINE, 24));

        scrollPane = new JScrollPane(listOfTabs);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
    }
}
