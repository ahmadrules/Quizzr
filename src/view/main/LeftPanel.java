package view.main;


import view.main.CenterPanels.CenterModulePanel;
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
    private CenterModulePanel centerModulePanel;
    private DefaultListModel<String> programListModel;
    private JScrollPane programScrollPane;
    private JLabel programsLabel;
    private String selectedProgram;
    private String selectedCourse;
    private JPanel programButtonPanel;
    private JPanel coursesButtonPanel;
    private JButton addCourseButton;
    private JButton removeCourseButton;
    private JButton editCourseButton;
    private JButton addProgramButton;
    private JButton removeProgramButton;
    private JButton editProgramButton;
    private MainFrame mainFrame;

    public LeftPanel(CenterModulePanel centerModulePanel, MainFrame mainFrame) {
        this.centerModulePanel = centerModulePanel;
        this.mainFrame = mainFrame;

        createDataComponents();
        createDataLists();
        setupLayout();
        addEventListeners();

        this.add(programsLabel);
        this.add(programScrollPane);
        this.add(programButtonPanel);
        this.add(coursesLabel);
        this.add(coursesScrollPane);
        this.add(coursesButtonPanel);
    }

    public void createDataComponents() {
        //Program data model, program list and program scrollPane created
        String[] categories = mainFrame.getProgramsNames();
        programListModel = new DefaultListModel<>();
        for (String category : categories) programListModel.addElement(category);
        programList = new JList<>(programListModel);
        programList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        programScrollPane = new JScrollPane(programList);
        programScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Course data model, course list and course scrollPane created
        coursesListModel = new DefaultListModel<>();
        coursesList = new JList<>(coursesListModel);
        coursesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        coursesScrollPane = new JScrollPane(coursesList);
        coursesScrollPane.setVisible(true);
        coursesScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public void setupLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Create labels for program and course lists
        programsLabel = new JLabel(" Programs");
        programsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        programsLabel.setFont(new Font("Arial", Font.BOLD, 14));

        coursesLabel = new JLabel(" Courses");
        coursesLabel.setFont(new Font("Arial", Font.BOLD, 14));
        coursesLabel.setVisible(true);

        //Create buttonPanel for program list buttons
        programButtonPanel = new JPanel(new FlowLayout());
        programButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Create buttons for program list
        addProgramButton = new JButton("Add");
        removeProgramButton = new JButton("Delete");
        editProgramButton = new JButton("Edit");
        disableProgramButtons();

        //Add buttons to panel
        programButtonPanel.add(addProgramButton);
        programButtonPanel.add(removeProgramButton);
        programButtonPanel.add(editProgramButton);

        //Create buttonPanel for course list buttons
        coursesButtonPanel = new JPanel(new FlowLayout());
        coursesButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Create buttons for course list
        addCourseButton = new JButton("Add");
        removeCourseButton = new JButton("Delete");
        editCourseButton = new JButton("Edit");
        addCourseButton.setEnabled(false);
        removeCourseButton.setEnabled(false);
        editCourseButton.setEnabled(false);

        //Add buttons to panel
        coursesButtonPanel.add(addCourseButton);
        coursesButtonPanel.add(removeCourseButton);
        coursesButtonPanel.add(editCourseButton);

    }
    public void createDataLists() {
        //List of available courses. This will be fetched from the Controller.
        coursesListMap = new HashMap<>();

        //Example values added here.
        coursesListMap.put("Program 1", new String[]{"Course A1", "Course A2", "Course A3"});
        coursesListMap.put("Program 2", new String[]{"Course B1", "Course B2", "Course B3"});
        coursesListMap.put("Program 3", new String[]{"Course C1", "Course C2", "Course C3"});
    }

    public void enableProgramButtons() {
        editProgramButton.setEnabled(true);
        removeProgramButton.setEnabled(true);
    }

    public void disableProgramButtons() {
        editProgramButton.setEnabled(false);
        removeProgramButton.setEnabled(false);
    }

    public void enableCourseButtons() {
        editCourseButton.setEnabled(true);
        removeCourseButton.setEnabled(true);
    }

    public void disableCourseButtons() {
        addCourseButton.setEnabled(false);
        editCourseButton.setEnabled(false);
        removeCourseButton.setEnabled(false);
    }

    private void addEventListeners() {
        programList.addListSelectionListener(e -> {
            if (programList.getValueIsAdjusting()) {

                //Selected program is decided here
                //Use selectedProgram for communication with Controller
                selectedProgram = programList.getSelectedValue();

                if (selectedProgram != null) {
                    coursesListModel.clear();
                    selectedCourse = null;
                    //-------------------------------------------------------------------
                    //Here we contact the Controller to fetch the available list of courses for the chosen program
                    String[] coursesNames = mainFrame.getCoursesNames(selectedProgram);
                    for (String item : coursesListMap.getOrDefault(selectedProgram, coursesNames)) {
                        coursesListModel.addElement(item);
                    }
                }
                //--------------------------------------------------------------------

                centerModulePanel.disableButtons();
                centerModulePanel.clearModuleList();
                enableProgramButtons();
                disableCourseButtons();
                addCourseButton.setEnabled(true);
                this.revalidate();
                this.repaint();
            }
        });

        coursesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedCourse = coursesList.getSelectedValue();
                if (selectedCourse != null) {

                    //rightPanel handles fetching a list of available modules for the selected course
                    centerModulePanel.courseChosen(selectedCourse);
                    enableCourseButtons();
                }
            }
        });

        addProgramButton.addActionListener(e -> {
            //Here we write what happens when we press the add button for the programs list
        });

        removeProgramButton.addActionListener(e -> {
            //Here we write what happens when we press the delete button for the programs list when a program has been chosen
            if (mainFrame.askForConfirmation(selectedProgram) == true) {
                //What happens if we confirm the "are you sure?" message
            }
        });

        editProgramButton.addActionListener(e -> {
            //Here we write what happens when we press the edit button for the programs list when a program has been chosen
        });

        addCourseButton.addActionListener(e -> {
            //Here we write what happens when we press the add button for the course list
        });

        removeCourseButton.addActionListener(e -> {
            //Here we write what happens when we press the delete button for the course list when a course has been chosen
            if (mainFrame.askForConfirmation(selectedCourse) == true) {
                //What happens if we confirm the "are you sure?" message
            }
        });

        editCourseButton.addActionListener(e -> {
            //Here we write what happens when we press the edit button for the course list when a course has been chosen
        });
    }

}
