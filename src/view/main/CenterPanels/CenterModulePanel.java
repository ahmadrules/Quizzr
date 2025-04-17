package view.main.CenterPanels;

import view.main.MainFrame;
import view.subPanels.FlashcardPanel;
import view.subPanels.QuizPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

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
    private String selectedModule;
    private MainFrame mainFrame;
    private String selectedCourse;


    public CenterModulePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        createDataList();
        createDataComponents();
        createButtons();
        addEventListener();
        setupLayout();

        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setupLayout() {
        JLabel displayLabel = new JLabel("Available modules", SwingConstants.CENTER);
        add(displayLabel, BorderLayout.NORTH);
        add(moduleScrollPane, BorderLayout.CENTER);
    }

    public void createDataComponents() {
        moduleListModel = new DefaultListModel<>();
        moduleList = new JList<>(moduleListModel);
        moduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        moduleScrollPane = new JScrollPane(moduleList);
        moduleScrollPane.setVisible(true);
    }


    public void createDataList() {
        //List of available modules for the chosen course. This will be fetched from Controller
        moduleListMap = new HashMap<>();
    }

    public void courseChosen(String courseName) {
        this.selectedCourse = courseName;
        clearModuleList();

        //-------------------------------------------------------------------
        //Here we contact the Controller to fetch the available list of modules for the chosen course
        for (String item : moduleListMap.getOrDefault(courseName, mainFrame.getModulesNames(courseName))) {
            moduleListModel.addElement(item);
        }
        //-------------------------------------------------------------------

        moduleScrollPane.setVisible(true);
        disableButtons();
        addModuleButton.setEnabled(true);
        revalidate();
        repaint();
    }

    public void disableButtons() {
        quizButton.setEnabled(false);
        flashcardsButton.setEnabled(false);
        addModuleButton.setEnabled(false);
        editModuleButton.setEnabled(false);
        deleteModuleButton.setEnabled(false);
    }

    public void enableButtons() {
        quizButton.setEnabled(true);
        flashcardsButton.setEnabled(true);
        addModuleButton.setEnabled(true);
        editModuleButton.setEnabled(true);
        deleteModuleButton.setEnabled(true);
    }

    public void createButtons() {
        buttonPanel = new JPanel(new FlowLayout());
        quizButton = new JButton("Quiz");
        flashcardsButton = new JButton("FlashCards");
        addModuleButton = new JButton("Add module");
        editModuleButton = new JButton("Edit");
        deleteModuleButton = new JButton("Delete");

        quizButton.setEnabled(false);
        flashcardsButton.setEnabled(false);
        addModuleButton.setEnabled(false);
        editModuleButton.setEnabled(false);
        deleteModuleButton.setEnabled(false);
        buttonPanel.add(quizButton);
        buttonPanel.add(flashcardsButton);
        buttonPanel.add(addModuleButton);
        buttonPanel.add(editModuleButton);
        buttonPanel.add(deleteModuleButton);
        buttonPanel.setVisible(true);
    }

    public void clearModuleList() {
        moduleListModel.clear();
    }

    public void addEventListener() {
        flashcardsButton.addActionListener(e -> {
            //Here we write what happens when we press the flashcard button
            //FlashcardPanel flashcardPanel = new FlashcardPanel(selectedModule);
        });

        quizButton.addActionListener(e -> {
            //Here we write what happens when we press the quiz button
            //QuizPanel quizPanel = new QuizPanel(selectedModule);
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
            if (mainFrame.deleteConfirmation(selectedModule) == true) {
                //Here we write what happens when we press the delete button
                mainFrame.deleteModule(selectedCourse, selectedModule);
                //TODO The module list must be updated after deleting the module
            };
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
    }

    public void addModule() {
        String moduleName = JOptionPane.showInputDialog("Please enter a module name");
        if (moduleName != null) {
            //TO DO: Code to add module to list of available module
            mainFrame.addNewModule(selectedCourse, selectedModule);
            //TODO The module list must be updated after adding the new module
            revalidate();
            repaint();
        }
    }

    public void editModule() {
        String moduleName = JOptionPane.showInputDialog("Please enter a module name", selectedModule);
        if (moduleName != null) {
            //TO DO: Code to edit name of selected module
            //TODO The module list must be updated after editing the module
            revalidate();
            repaint();
        }
    }
}
