package view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class LeftPanel extends JPanel {

    private JList<String> programList;
    private HashMap<String, String[]> coursesListMap;
    private JList<String> coursesList;
    private JLabel coursesLabel;
    private DefaultListModel<String> coursesListModel;
    private JScrollPane coursesScrollPane;
    private RightPanel rightPanel;
    private DefaultListModel<String> programListModel;
    private JScrollPane programScrollPane;
    private JLabel programsLabel;

    public LeftPanel(RightPanel rightPanel) {
        this.rightPanel = rightPanel;

        //Layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        programsLabel = new JLabel(" Programs");
        programsLabel.setFont(new Font("Arial", Font.BOLD, 14));

        String[] categories = {"Program 1", "Program 2", "Program 3"};
        programListModel = new DefaultListModel<>();
        for (String category : categories) programListModel.addElement(category);
        programList = new JList<>(programListModel);
        programList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        programScrollPane = new JScrollPane(programList);

        coursesListModel = new DefaultListModel<>();
        coursesList = new JList<>(coursesListModel);
        coursesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        coursesScrollPane = new JScrollPane(coursesList);
        coursesScrollPane.setVisible(false);

        coursesLabel = new JLabel(" Courses");
        coursesLabel.setFont(new Font("Arial", Font.BOLD, 14));
        coursesLabel.setVisible(false);

        createDataLists();
        addEventListeners();

        this.add(programsLabel);
        this.add(programScrollPane);
        this.add(coursesLabel);
        this.add(coursesScrollPane);
    }


    public void createDataLists() {
        coursesListMap = new HashMap<>();
        coursesListMap.put("Program 1", new String[]{"Course A1", "Course A2", "Course A3"});
        coursesListMap.put("Program 2", new String[]{"Course B1", "Course B2", "Course B3"});
        coursesListMap.put("Program 3", new String[]{"Course C1", "Course C2", "Course C3"});

    }

    private void addEventListeners() {
        programList.addListSelectionListener(e -> {
            if (programList.getValueIsAdjusting()) {
                String selectedCategory = programList.getSelectedValue();
                if (selectedCategory != null) {
                    coursesListModel.clear();
                    for (String item : coursesListMap.getOrDefault(selectedCategory, new String[]{})) {
                        coursesListModel.addElement(item);
                    }
                    coursesScrollPane.setVisible(true);
                    coursesLabel.setVisible(true);
                    rightPanel.disableScrollPane();
                    // === NY KOD ===
                    rightPanel.disableButtons();
                    // === SLUT PÅ NY KOD ===
                    this.revalidate();
                    this.repaint();
                }
            }
        });

        coursesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedSubItem = coursesList.getSelectedValue();
                if (selectedSubItem != null) {
                    rightPanel.courseChosen(selectedSubItem);
                }
            }
        });
    }
}
