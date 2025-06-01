package view.main;

import view.main.CenterPanels.CenterModulePanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LeftPanelStudent extends JPanel {
    private List<String> coursesNames;
    private JList<String> coursesList;
    private JLabel coursesLabel;
    private DefaultListModel<String> coursesListModel;
    private JScrollPane coursesScrollPane;
    private CenterModulePanel centerModulePanel;
    private String studentProgramName;
    private String selectedCourse;
    private MainFrame mainFrame;

    public LeftPanelStudent(CenterModulePanel centerModulePanel, MainFrame mainFrame) {
        this.centerModulePanel = centerModulePanel;
        this.mainFrame = mainFrame;

        createDataComponents();
        setupLayout();
        addEventListeners();

        add(coursesLabel);
        add(coursesScrollPane);
    }

    public String getSelectedCourse() {
        return selectedCourse;
    }

    /**
     * This method creates all the data components required for storing the required lists.
     * It also sets up the layout of the lists.
     *
     * @author Ahmad Maarouf
     */
    public void createDataComponents() {
        studentProgramName = mainFrame.getStudentProgramName();

        //Course data model, course list and course scrollPane created
        coursesNames = List.of(mainFrame.getCoursesNames(studentProgramName));
        coursesListModel = new DefaultListModel<>();
        coursesListModel.addAll(coursesNames);
        coursesList = new JList<>(coursesListModel);
        coursesList.setFont(new Font("Montserrat", Font.PLAIN, 12));
        coursesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        coursesScrollPane = new JScrollPane(coursesList);
        coursesScrollPane.setVisible(true);
        coursesScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    /**
     * Sets up the layout for the left panels as well as labels and buttons.
     *
     * @author Ahmad Maarouf
     */
    public void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        coursesLabel = new JLabel("My Courses");
        coursesLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        coursesLabel.setVisible(true);
    }

    /**
     * Adds event listeners to the lists and buttons contained within the leftPanel.
     *
     * @author Ahmad Maarouf
     */
    private void addEventListeners() {
        coursesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedCourse = coursesList.getSelectedValue();
                if (selectedCourse != null) {
                    //centerModulePanel handles fetching and displaying a list of available modules for the selected course.
                    centerModulePanel.courseChosen(studentProgramName, selectedCourse);
                }
            }
        });
    }
}
