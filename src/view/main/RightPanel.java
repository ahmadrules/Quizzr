package view.main;

import javax.swing.*;
import java.awt.*;

/**
 * This class is responsible for displaying the list of tabs on the right side of the main frame
 * It is also responsible for giving the user an option to log out
 * @author Ahmad Maarouf
 */

public class RightPanel extends JPanel {
    private MainFrame mainFrame;
    private CenterPanel centerContainer;
    private JList<String> listOfTabs;
    private JScrollPane scrollPane;
    private String selectedTab;

    public RightPanel(MainFrame mainFrame, CenterPanel centerContainer) {
        this.mainFrame = mainFrame;
        this.centerContainer = centerContainer;

        setBorder(BorderFactory.createLineBorder(Color.black));

        createList();
        addEventListeners();
        JButton logOutButton = new JButton("Log Out");
        logOutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        logOutButton.addActionListener(e -> {
            mainFrame.logOut();
        });

        JLabel tabLabel = new JLabel(" Select a tab");
        tabLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        tabLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        setLayout(new BorderLayout());
        setSize(150, 400);


        add(tabLabel, BorderLayout.NORTH);
        add(scrollPane);
        add(logOutButton, BorderLayout.SOUTH);
        add(new JSeparator(), BorderLayout.WEST);
        setVisible(true);
    }

    /**
     * Creates the list for displaying the tabs
     * @author Ahmad Maarouf
     */
    public void createList() {
        //tabOptions will be fetched from controller
        String[] tabOptions = {"Modules", "Account"};
        //-----------------------------------------

        listOfTabs = new JList<>(tabOptions);

        listOfTabs.setFixedCellWidth(150);
        listOfTabs.setFont(new Font("Arial", Font.ROMAN_BASELINE, 24));
        listOfTabs.setSelectedIndex(0);

        scrollPane = new JScrollPane(listOfTabs);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    /**
     * Adds event listeners to the list
     * @author Ahmad Maarouf
     */
    public void addEventListeners() {
        listOfTabs.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedTab = listOfTabs.getSelectedValue();
                if (selectedTab != null) {
                    if (selectedTab == "Modules") {
                        centerContainer.setModuleLayout();
                    }

                    else if (selectedTab == "Account") {
                        centerContainer.setAccountLayout();
                    }
                }
            }
        });
    }
}
