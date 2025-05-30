package view.subPanels.Quiz;

import controller.Main;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class TabPanel extends JPanel {
    private MainQuizFrame mainQuizFrame;
    private JList<String> listOfTabs;
    private JLabel topLabel;

    public TabPanel(MainQuizFrame mainQuizFrame) {
        this.mainQuizFrame = mainQuizFrame;

        setLayout(new BorderLayout());

        JSeparator separator = new JSeparator();
        add(separator, BorderLayout.WEST);

        createList();
        createTopLabel();
        addListListener();
    }

    public void createList() {
        //tabOptions will be fetched from controller
        String[] tabOptions = {"Quiz", "History"};
        //-----------------------------------------

        listOfTabs = new JList<>(tabOptions);

        listOfTabs.setFixedCellWidth(70);
        listOfTabs.setFont(new Font("Georgia", Font.ROMAN_BASELINE, 30));
        listOfTabs.setSelectedIndex(0);

        listOfTabs.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        listOfTabs.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(listOfTabs, BorderLayout.CENTER);
    }

    public void createTopLabel() {
        topLabel = new JLabel("Click to see your History ");
        topLabel.setFont(new Font("Georgia", Font.BOLD, 15));
        topLabel.setForeground(new Color(25, 25, 70));
        topLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        topLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 40, 0));
        add(topLabel, BorderLayout.NORTH);
    }

    public void addListListener() {
        listOfTabs.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (listOfTabs.getSelectedValue() != null) {
                    String selectedTab = listOfTabs.getSelectedValue();

                    if (selectedTab.equals("Quiz")) {
                        topLabel.setText("Click to see your History!");
                        mainQuizFrame.setAvailableQuizPanel();
                    } else if (selectedTab.equals("History")) {
                        topLabel.setText("Click to see all Quizzes!");
                        mainQuizFrame.setHistoryPanel();
                    }
                }
            }
        });
    }
}
