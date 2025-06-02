package view.main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * This class is responsible for displaying the list of tabs on the right side of the main frame
 * It is also responsible for giving the user an option to log out
 *
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
        JButton logOutButton = createStyledButton("Log Out");
        logOutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        logOutButton.addActionListener(e -> {
            mainFrame.logOut();
        });

        JLabel tabLabel = new JLabel(" Select a tab");
        tabLabel.setFont(new Font("Gorgia", Font.PLAIN, 16));
        tabLabel.setOpaque(true);
        tabLabel.setBackground(new Color(255, 249, 163));
        tabLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tabLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        tabLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
     *
     * @author Ahmad Maarouf
     */
    public void createList() {
        //tabOptions will be fetched from controller
        String[] tabOptions = {"Modules", "Account"};
        //-----------------------------------------

        listOfTabs = new JList<>(tabOptions);

        listOfTabs.setFixedCellWidth(150);
        listOfTabs.setFont(new Font("Georgia", Font.ROMAN_BASELINE, 34));
        listOfTabs.setSelectedIndex(0);
        listOfTabs.setForeground(new Color(10, 10, 40));

        scrollPane = new JScrollPane(listOfTabs);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    /**
     * Adds event listeners to the list
     *
     * @author Ahmad Maarouf
     */
    public void addEventListeners() {
        listOfTabs.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedTab = listOfTabs.getSelectedValue();
                if (selectedTab != null) {
                    if (selectedTab == "Modules") {
                        centerContainer.setModuleLayout();
                    } else if (selectedTab == "Account") {
                        centerContainer.setAccountLayout();
                    }
                }
            }
        });
    }
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial",Font.BOLD,14));

        Color baseColor = new Color(52, 69,140);
        Color haverColor = new Color(90, 140, 230);
        Color borderColor = baseColor.darker();

        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        //button.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        Border lineBorder= BorderFactory.createLineBorder(borderColor,4);
        Border emptyBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        button.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(haverColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(haverColor.darker(), 2),
                        BorderFactory.createEmptyBorder(2, 2,2, 2)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor, 2),
                        BorderFactory.createEmptyBorder(2, 2, 2, 2)
                ));
            }
        });

        return button;
    }
}
