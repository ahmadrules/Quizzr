package view.subPanels.Quiz;

import controller.Main;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class TabPanel extends JPanel {
    private MainQuizFrame mainQuizFrame;
    private JList<String> listOfTabs;

    public TabPanel(MainQuizFrame mainQuizFrame) {
        this.mainQuizFrame = mainQuizFrame;

        setLayout(new BorderLayout());

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
        listOfTabs.setFont(new Font("Arial", Font.ROMAN_BASELINE, 18));
        listOfTabs.setSelectedIndex(0);

        listOfTabs.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        listOfTabs.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(listOfTabs, BorderLayout.CENTER);
    }

    public void createTopLabel() {
        JLabel topLabel = new JLabel("Select a tab");
        add(topLabel, BorderLayout.NORTH);
    }

    public void addListListener() {
        listOfTabs.addListSelectionListener(e -> {
           if (!e.getValueIsAdjusting()) {
               if (listOfTabs.getSelectedValue() != null) {
                   String selectedTab = listOfTabs.getSelectedValue();

                   if (selectedTab.equals("Quiz")) {
                       mainQuizFrame.setAvailableQuizPanel();
                   }
                   else if (selectedTab.equals("History")) {
                       mainQuizFrame.setHistoryPanel();
                   }
               }
           }
        });
    }
}
