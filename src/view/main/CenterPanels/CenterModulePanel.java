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
    private HashMap<String, String[]> moduleListMap;
    private DefaultListModel<String> moduleListModel;
    private JList<String> moduleList;
    private JScrollPane moduleScrollPane;
    private String chosenModule;
    private MainFrame mainFrame;


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

    public void clearModuleList() {
        moduleListModel.clear();
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
