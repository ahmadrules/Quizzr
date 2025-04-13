package view.main;

import view.subPanels.FlashcardPanel;
import view.subPanels.QuizPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class RightPanel extends JPanel {
    private JPanel buttonPanel;
    private JButton quizButton;
    private JButton flashcardsButton;
    private JButton addModuleButton;
    private HashMap<String, String[]> moduleListMap;
    private DefaultListModel<String> moduleListModel;
    private JList<String> moduleList;
    private JScrollPane moduleScrollPane;
    private String chosenModule;
    private MainFrame mainFrame;


    public RightPanel(MainFrame mainFrame) {
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

        /*
        //Example values added here
        moduleListMap.put("Course A1", new String[]{"Module A1-1", "Module A1-2", "Module A1-3"});
        moduleListMap.put("Course A2", new String[]{"Module A2-1", "Module A2-2", "Module A2-3"});
        moduleListMap.put("Course A3", new String[]{"Module A3-1", "Module A3-2", "Module A3-3"});

        moduleListMap.put("Course B1", new String[]{"Module B1-1", "Module B1-2", "Module B1-3"});
        moduleListMap.put("Course B2", new String[]{"Module B2-1", "Module B2-2", "Module B2-3"});
        moduleListMap.put("Course B3", new String[]{"Module B3-1", "Module B3-2", "Module B3-3"});

        moduleListMap.put("Course C1", new String[]{"Module C1-1", "Module C1-2", "Module C1-3"});
        moduleListMap.put("Course C2", new String[]{"Module C2-1", "Module C2-2", "Module C2-3"});
        moduleListMap.put("Course C3", new String[]{"Module C3-1", "Module C3-2", "Module C3-3"});
         */

    }

    public void courseChosen(String courseName) {
        moduleListModel.clear();

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
    }

    public void enableButtons() {
        quizButton.setEnabled(true);
        flashcardsButton.setEnabled(true);
        addModuleButton.setEnabled(true);
    }

    public void createButtons() {
        buttonPanel = new JPanel(new FlowLayout());
        quizButton = new JButton("Quiz");
        flashcardsButton = new JButton("FlashCards");
        addModuleButton = new JButton("Add module");
        quizButton.setEnabled(false);
        flashcardsButton.setEnabled(false);
        addModuleButton.setEnabled(false);
        buttonPanel.add(quizButton);
        buttonPanel.add(flashcardsButton);
        buttonPanel.add(addModuleButton);
        buttonPanel.setVisible(true);
    }

    public void addEventListener() {
        flashcardsButton.addActionListener(e -> {
            FlashcardPanel flashcardPanel = new FlashcardPanel(chosenModule);
        });

        quizButton.addActionListener(e -> {
            QuizPanel quizPanel = new QuizPanel(chosenModule);
        });

        moduleList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                chosenModule = moduleList.getSelectedValue();
                if (chosenModule != null) {
                    enableButtons();
                }
            }
        });
    }
}
