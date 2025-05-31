package view.main;


import view.main.CenterPanels.CenterModulePanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * This class is responsible for displaying the list of available programs and also the list of available courses depending on
 * which program is selected.
 *
 * @author Ahmad Maarouf
 */
public class LeftPanelAdmin extends JPanel {
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
    private String[] courseNames;
    private String[] programNames;
    private int selectedProgramIndex = -1;
    private int selectedCourseIndex = -1;

    public LeftPanelAdmin(CenterModulePanel centerModulePanel, MainFrame mainFrame) {
        this.centerModulePanel = centerModulePanel;
        this.mainFrame = mainFrame;

        createDataComponents();
        setupLayout();
        addEventListeners();

        add(programsLabel);
        add(programScrollPane);
        add(programButtonPanel);

        add(coursesLabel);
        add(coursesScrollPane);
        add(coursesButtonPanel);
        setBackground(new Color(236, 130, 21));
        setBorder(BorderFactory.createBevelBorder(1));
    }

    /**
     * This method creates all the data components required for storing the required lists.
     * It also sets up the layout of the lists.
     *
     * @author Ahmad Maarouf
     */
    public void createDataComponents() {
        programsLabel = new JLabel();
        //Program data model, program list and program scrollPane created
        programNames = new String[mainFrame.getProgramsNames().length];
        programListModel = new DefaultListModel<>();
        int i = 0;
        for (String programName : mainFrame.getProgramsNames()) {
            programNames[i] = programName;
            programListModel.addElement(programName);
            i++;
        }
        programList = new JList<>(programListModel);

        programList.setFont(new Font("Montserrat", Font.PLAIN, 12));
        programList.setBackground(new Color(251, 187, 41));

        programList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        programScrollPane = new JScrollPane(programList);
        programScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Course data model, course list and course scrollPane created
        coursesListMap = new HashMap<>();
        coursesListModel = new DefaultListModel<>();
        coursesList = new JList<>(coursesListModel);

        coursesList.setFont(new Font("Montserrat", Font.PLAIN, 12));
        coursesList.setBackground(new Color(251, 187, 41));

        coursesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        coursesScrollPane = new JScrollPane(coursesList);
        coursesScrollPane.setVisible(true);
        coursesScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    /**
     * This method updates the list of available programs and also courses if a program is selected.
     *
     * @author Ahmad Maarouf
     */
    public void updateLists() {
        //Fetch program names.
        programNames[0] = mainFrame.getCurrentUserProgram();
        programListModel.clear();
        for (String category : programNames) {
            programListModel.addElement(category);
        }

        //Update list of courses if a program is selected.
        if (selectedProgram != null) {
            courseNames = mainFrame.getCoursesNames(selectedProgram);
            coursesListModel.clear();
            for (String item : coursesListMap.getOrDefault(selectedProgram, courseNames)) {
                coursesListModel.addElement(item);
            }
        }

        //Clear the list of courses if no program is selected.
        else {
            coursesListModel.clear();
        }

        //Automatically re-selects the object that was previously selected in the program and courses lists.
        programList.setSelectedIndex(selectedProgramIndex);
        coursesList.setSelectedIndex(selectedCourseIndex);

        revalidate();
        repaint();
    }

    /**
     * Sets up the layout for the left panels as well as labels and buttons.
     *
     * @author Ahmad Maarouf
     */
    public void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Create labels for program and course lists
        programsLabel = new JLabel(" Programs");
        programsLabel.setFont(new Font("Montserrat", Font.BOLD, 14));
        programsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);


        coursesLabel = new JLabel(" Courses");
        coursesLabel.setFont(new Font("Montserrat", Font.BOLD, 14));
        coursesLabel.setVisible(true);

        //Create buttonPanel for program list buttons
        programButtonPanel = new JPanel(new FlowLayout());
        programButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Create buttons for program list
        addProgramButton = new JButton("Add");
        styleButton(addProgramButton);

        removeProgramButton = new JButton("Delete");
        styleButton(removeProgramButton);

        editProgramButton = new JButton("Edit");
        styleButton(editProgramButton);
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
        styleButton(addCourseButton);

        removeCourseButton = new JButton("Delete");
        styleButton(removeCourseButton);

        editCourseButton = new JButton("Edit");
        styleButton(editCourseButton);

        disableCourseButtons();

        //Add buttons to panel
        coursesButtonPanel.add(addCourseButton);
        coursesButtonPanel.add(removeCourseButton);
        coursesButtonPanel.add(editCourseButton);

    }

    /**
     * Enable program buttons. Used when a program is selected.
     *
     * @author Ahmad Maarouf
     */
    public void enableProgramButtons() {
        editProgramButton.setEnabled(true);
        removeProgramButton.setEnabled(true);
    }

    public void styleButton(JButton button) {
        button.setBackground(new Color(25, 25, 70));
        button.setForeground(Color.WHITE);
    }

    /**
     * Disable program buttons. Used when no program is selected.
     *
     * @author Ahmad Maarouf
     */
    public void disableProgramButtons() {
        editProgramButton.setEnabled(false);
        removeProgramButton.setEnabled(false);
    }

    /**
     * Enable course buttons. Used when a course is selected
     *
     * @author Ahmad Maarouf
     */
    public void enableCourseButtons() {
        addProgramButton.setEnabled(true);
        editCourseButton.setEnabled(true);
        removeCourseButton.setEnabled(true);
    }

    /**
     * Disable course buttons. Used when no course is selected, or a new program is selected.
     *
     * @author Ahmad Maarouf
     */
    public void disableCourseButtons() {
        addCourseButton.setEnabled(false);
        editCourseButton.setEnabled(false);
        removeCourseButton.setEnabled(false);
    }

    /**
     * Adds event listeners to the lists and buttons contained within the leftPanel.
     *
     * @author Ahmad Maarouf
     */
    private void addEventListeners() {
        programList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                //Selected program is decided here
                //Use selectedProgram for communication with Controller
                selectedProgram = programList.getSelectedValue();
                if (selectedProgram != null) {
                    selectedCourseIndex = -1;
                    selectedProgramIndex = programList.getSelectedIndex();
                    System.out.println("Selected program index: " + selectedProgramIndex);
                    coursesListModel.clear();
                    selectedCourse = null;
                    //-------------------------------------------------------------------
                    //Here we contact the Controller to fetch the available list of courses for the chosen program
                    courseNames = mainFrame.getCoursesNames(selectedProgram);
                    for (String item : coursesListMap.getOrDefault(selectedProgram, courseNames)) {
                        coursesListModel.addElement(item);
                    }
                }
                //--------------------------------------------------------------------

                centerModulePanel.disableButtons();
                centerModulePanel.clearModuleList();

                if (selectedProgramIndex != -1) {
                    enableProgramButtons();
                    disableCourseButtons();
                    addCourseButton.setEnabled(true);
                }
            }
        });


        coursesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedCourse = coursesList.getSelectedValue();
                if (selectedCourse != null) {
                    selectedCourseIndex = coursesList.getSelectedIndex();

                    //centerModulePanel handles fetching and displaying a list of available modules for the selected course.
                    centerModulePanel.courseChosen(selectedProgram, selectedCourse);
                    enableCourseButtons();
                }
            }
        });

        addProgramButton.addActionListener(e -> {
            //Here we write what happens when we press the add button for the programs list
            addProgram();
        });

        removeProgramButton.addActionListener(e -> {
            //Here we write what happens when we press the delete button for the programs list when a program has been chosen
            removeProgram();
        });

        editProgramButton.addActionListener(e -> {
            //Here we write what happens when we press the edit button for the programs list when a program has been chosen
            editProgram();
        });

        addCourseButton.addActionListener(e -> {
            //Here we write what happens when we press the add button for the course list
            addCourse();
        });

        removeCourseButton.addActionListener(e -> {
            //Here we write what happens when we press the delete button for the course list when a course has been chosen
            removeCourse();
        });

        editCourseButton.addActionListener(e -> {
            //Here we write what happens when we press the edit button for the course list when a course has been chosen
            editCourse();
        });
    }

    /**
     * Adds a program to the list of available programs.
     * Also adds it to the file which contains all the available programs.
     * This is done through the mainFrame, which contacts the controller.
     *
     * @author Ahmad Maarouf
     */
    public void addProgram() {
        String programName = JOptionPane.showInputDialog("Please enter a program name");
        String programCode = JOptionPane.showInputDialog("Please enter a program code");
        if (programName != null) {
            if (mainFrame.ifProgramExists(programName)) {
                JOptionPane.showMessageDialog(null, programName + " already exists");
            } else {
                mainFrame.addProgramToProgramList(programName, programCode);
                updateLists();
            }
        }
    }

    /**
     * Adds a course to the list of available courses for a program.
     * Also adds it to the file which contains all the available courses for that program.
     * This is done through the mainFrame, which contacts the controller.
     *
     * @author Ahmad Maarouf
     */
    public void addCourse() {
        String courseName = JOptionPane.showInputDialog("Please enter a course name");
        if (courseName != null) {
            if (mainFrame.ifCourseExists(selectedProgram, courseName)) {
                JOptionPane.showMessageDialog(null, courseName + " already exists for " + selectedProgram + "! Choose a different course name");
            } else {
                mainFrame.addNewCourse(selectedProgram, courseName);
                updateLists();
            }
        }
    }

    /**
     * Edits a program in the list of available programs.
     * Also edits it in the file which contains all the available programs.
     * This is done through the mainFrame, which contacts the controller.
     *
     * @author Ahmad Maarouf
     */
    public void editProgram() {
        String programName = JOptionPane.showInputDialog("Please enter new program name", selectedProgram);
        if (programName != null) {
            if (programName.equals(selectedProgram)) {
                //Do nothing if name not changed
            } else if (mainFrame.ifProgramExists(programName)) {
                JOptionPane.showMessageDialog(null, programName + " already exists! Choose a different program name");
            } else {
                mainFrame.editProgramName(selectedProgram, programName);
                updateLists();
                selectedProgram = programName;
            }
        }
    }

    /**
     * Edits a course in the list of available courses for a program
     * Also edits it in the file which contains all the available courses for that program.
     * This is done through the mainFrame, which contacts the controller.
     *
     * @author Ahmad Maarouf
     */
    public void editCourse() {
        String courseName = JOptionPane.showInputDialog("Please enter new course name", selectedCourse);
        if (courseName != null) {
            if (courseName.equals(selectedCourse)) {
                //Do nothing if name not changed
            } else if (mainFrame.ifCourseExists(selectedProgram, courseName)) {
                JOptionPane.showMessageDialog(null, courseName + " already exists for " + selectedProgram + "! Choose a different course name");
            } else {
                mainFrame.editCourseName(selectedCourse, courseName);
                updateLists();
                selectedCourse = courseName;
            }
        }
    }

    /**
     * Removes a program from the list of available programs.
     * Also removes it from the file which contains all the available programs.
     * This is done through the mainFrame, which contacts the controller.
     *
     * @author Ahmad Maarouf
     */
    public void removeProgram() {
        if (mainFrame.deleteConfirmation(selectedProgram) == true) {
            mainFrame.deleteProgram(selectedProgram);
            selectedProgram = null;
            selectedProgramIndex = -1;
            disableProgramButtons();
            disableCourseButtons();
            updateLists();
        }
    }

    /**
     * Removes a program from the list of available programs.
     * Also removes it from the file which contains all the available programs.
     * This is done through the mainFrame, which contacts the controller.
     *
     * @author Ahmad Maarouf
     */
    public void removeCourse() {
        if (mainFrame.deleteConfirmation(selectedCourse) == true) {
            mainFrame.deleteCourse(selectedProgram, selectedCourse);
            selectedCourse = null;
            selectedCourseIndex = -1;
            disableCourseButtons();
            addCourseButton.setEnabled(true);
            updateLists();
        }
    }
}
