package view.main.CenterPanels;

import view.main.MainFrame;
import view.subPanels.AddQuestionFrame;
import view.subPanels.Quiz.MainQuizFrame;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * This class is responsible for showing a list of modules for the
 * course selected in LeftPanel.
 * It is also responsible for giving options to add,edit and remove modules.
 * @author Ahmad Maarouf
 */
public class CenterModulePanel extends JPanel {
    private JPanel buttonPanel;
    private JButton quizButton;
    private JButton flashcardsButton;
    private JButton addModuleButton;
    private JButton editModuleButton;
    private JButton deleteModuleButton;
    private HashMap<String, String[]> moduleListMap;
    private DefaultListModel<String> moduleListModel;
    private JList<String> moduleList;
    private JScrollPane moduleScrollPane;
    private MainFrame mainFrame;
    private String selectedModule;
    private String selectedCourse;
    private String selectedProgram;
    private JButton addQuestionButton;
    private AddQuestionFrame addQuestionFrame;


    public CenterModulePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());


        createDataComponents();
        createButtons();
        addEventListener();
        setupLayout();

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Sets up the layout of the panel
     * @author Ahmad Maarouf
     */
    public void setupLayout() {
        JLabel displayLabel = new JLabel("Available modules", SwingConstants.CENTER);
        add(displayLabel, BorderLayout.NORTH);
        add(moduleScrollPane, BorderLayout.CENTER);
    }

    /**
     * Creates the necessary data components used to show the list of modules.
     * moduleListMap is used to link modules to a specific course.
     * @author Ahmad Maarouf
     */
    public void createDataComponents() {
        moduleListMap = new HashMap<>();
        moduleListModel = new DefaultListModel<>();
        moduleList = new JList<>(moduleListModel);
        moduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        moduleScrollPane = new JScrollPane(moduleList);
        moduleScrollPane.setVisible(true);
    }


    /**
     * Displays the modules available for a selected course within a selected program.
     * Called by leftPanel when a course is selected.
     * @param selectedProgram a string with the selected programs name
     * @param selectedCourse a string with the selected courses name
     * @author Ahmad Maarouf
     */
    public void courseChosen(String selectedProgram, String selectedCourse) {
        this.selectedProgram = selectedProgram;
        this.selectedCourse = selectedCourse;
        clearModuleList();

        //-------------------------------------------------------------------
        //Here we contact the Controller to fetch the available list of modules for the chosen course
        for (String item : moduleListMap.getOrDefault(selectedCourse, mainFrame.getModulesNames(selectedCourse))) {
            moduleListModel.addElement(item);
        }
        //-------------------------------------------------------------------

        moduleScrollPane.setVisible(true);
        disableButtons();
        addModuleButton.setEnabled(true);
        revalidate();
        repaint();
    }

    /**
     * Disables module buttons when a module is not chosen.
     * @author Ahmad Maarouf
     */
    public void disableButtons() {
        quizButton.setEnabled(false);
        flashcardsButton.setEnabled(false);
        addModuleButton.setEnabled(false);
        editModuleButton.setEnabled(false);
        deleteModuleButton.setEnabled(false);
        addQuestionButton.setEnabled(false);
    }

    /**
     * Enables module buttons when a module is selected.
     * @author Ahmad Maarouf
     */
    public void enableButtons() {
        quizButton.setEnabled(true);
        flashcardsButton.setEnabled(true);
        addModuleButton.setEnabled(true);
        editModuleButton.setEnabled(true);
        deleteModuleButton.setEnabled(true);
        addQuestionButton.setEnabled(true);
    }

    /**
     * Creates the necessary buttons for opening quiz and flashcards for the selected module,
     * as well as adding, editing and removing modules.
     * @author Ahmad Maarouf
     */
    public void createButtons() {
        buttonPanel = new JPanel(new FlowLayout());
        quizButton = new JButton("Quiz");
        flashcardsButton = new JButton("FlashCards");
        addModuleButton = new JButton("Add module");
        editModuleButton = new JButton("Edit");
        deleteModuleButton = new JButton("Delete");
        addQuestionButton = new JButton("Add question");

        quizButton.setEnabled(false);
        flashcardsButton.setEnabled(false);
        addModuleButton.setEnabled(false);
        editModuleButton.setEnabled(false);
        deleteModuleButton.setEnabled(false);
        addQuestionButton.setEnabled(false);
        buttonPanel.add(quizButton);
        buttonPanel.add(flashcardsButton);
        buttonPanel.add(addQuestionButton);
        buttonPanel.add(addModuleButton);
        buttonPanel.add(editModuleButton);
        buttonPanel.add(deleteModuleButton);

        buttonPanel.setVisible(true);
    }

    /**
     * Clears the module list.
     * Called when the list of modules needs to be updated.
     * @author Ahmad Maarouf
     */
    public void clearModuleList() {
        moduleListModel.clear();
    }

    /**
     * Updates the current list of modules according to what course is selected in LeftPanel.
     * @author Ahmad Maarouf
     */
    public void updateList() {
        clearModuleList();
        for (String item : moduleListMap.getOrDefault(selectedCourse, mainFrame.getModulesNames(selectedCourse))) {
            moduleListModel.addElement(item);
        }
        revalidate();
        repaint();
    }

    /**
     * Adds event listeners to the buttons and the list.
     * @author Ahmad Maarouf
     */
    public void addEventListener() {
         flashcardsButton.addActionListener(e -> {
            if (selectedProgram != null && selectedCourse != null && selectedModule != null) {
                new view.subPanels.FlashcardPanel(selectedProgram, selectedCourse, selectedModule, mainFrame);
            } else {
                JOptionPane.showMessageDialog(this, "Please select program, course, and module before opening flashcards.");
            }
        });

        quizButton.addActionListener(e -> {
            //Here we write what happens when we press the quiz button
            //QuizPanel quizPanel = new QuizPanel(selectedModule);
            MainQuizFrame mainQuizFrame = new MainQuizFrame(selectedProgram, selectedCourse, selectedModule, mainFrame);
        });

        addModuleButton.addActionListener(e -> {
            //Here we write what happens when we press the add module button
            addModule();
        });

        editModuleButton.addActionListener(e -> {
           //Here we write what happens when we press the edit button
            editModule();
        });

        deleteModuleButton.addActionListener(e -> {
            removeModule();
        });

        moduleList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                //Here we write what happens when we choose a module
                selectedModule = moduleList.getSelectedValue();
                if (selectedModule != null) {
                    enableButtons();
                }
            }
        });

        addQuestionButton.addActionListener(e -> {
            addQuestionFrame = new AddQuestionFrame(mainFrame);
        });
    }

    /**
     * Adds a module to the list of available modules for the selected course and also,
     * in the file that stores modules.
     * This is done through mainFrame which calls on controller.
     * @author Ahmad Maarouf
     */
    public void addModule() {
        String moduleName = JOptionPane.showInputDialog("Please enter a module name");
        if (moduleName != null) {
            if (mainFrame.ifModuleExists(selectedProgram, selectedCourse, moduleName) == false) {
                mainFrame.addNewModule(selectedCourse, moduleName);
                disableButtons();
                addModuleButton.setEnabled(true);
                updateList();
            }
            else {
                JOptionPane.showMessageDialog(null, moduleName + " already exists for " + selectedCourse + "! Please choose another module name.");
            }

        }
    }

    /**
     * Edits a module in the list of available modules for the selected course and also,
     * in the file that stores modules.
     * This is done through mainFrame which calls on controller.
     * @author Ahmad Maarouf
     */
    public void editModule() {
        String moduleName = JOptionPane.showInputDialog("Please enter a module name", selectedModule);
        if (moduleName != null) {
            if (moduleName.equals(selectedModule)) {
                //Do nothing if name not changed
            } else if (mainFrame.ifModuleExists(selectedProgram, selectedCourse, moduleName)) {
                JOptionPane.showMessageDialog(null, moduleName + " already exists for " + selectedCourse + "! Please choose another module name.");
            } else {
                mainFrame.editModuleName(selectedCourse, selectedModule, moduleName);
                disableButtons();
                addModuleButton.setEnabled(true);
                updateList();
            }
        }
    }

    /**
     * Removes a module from the list of available modules for the selected course and also,
     * from the file that stores modules.
     * This is done through mainFrame which calls on controller.
     * @author Ahmad Maarouf
     */
    public void removeModule() {
        if (mainFrame.deleteConfirmation(selectedModule) == true) {
            //Here we write what happens when we press the delete button
            mainFrame.deleteModule(selectedCourse, selectedModule);
            disableButtons();
            addModuleButton.setEnabled(true);
            updateList();
        };
    }
}
