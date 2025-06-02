package view.main.CenterPanels;

import view.main.MainFrame;
import view.subPanels.AddQuestionFrame;
import view.subPanels.Quiz.MainQuizFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

/**
 * This class is responsible for showing a list of modules for the
 * course selected in LeftPanel.
 * It is also responsible for giving options to add,edit and remove modules.
 *
 * @author Ahmad Maarouf
 */
public class CenterModulePanel extends JPanel {
    private JPanel buttonPanel;
    private JButton quizButton;
    private JButton flashcardsButton;
    private JButton addModuleButton;
    private JButton editModuleButton;
    private JButton deleteModuleButton;
    private JButton tutorialButton;
    private JButton generalQuizButton;

    private JButton closeButton;
    private JPanel tutorialPanel;
    private JPanel centerPanel;
    private JPanel modulePanel;

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
    private boolean isAdmin;
    private boolean tutorialOpen = true;

    public CenterModulePanel(MainFrame mainFrame, boolean isAdmin) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        this.isAdmin = isAdmin;

        createDataComponents();
        createButtons();
        setupLayout();
        addEventListener();

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setupTutorialPanel() {
        tutorialPanel = new JPanel(new BorderLayout());
        JPanel tutorialTextPanel = new JPanel();
        tutorialTextPanel.setLayout(new BoxLayout(tutorialTextPanel, BoxLayout.Y_AXIS));

        tutorialPanel.add(new JSeparator(), BorderLayout.WEST);

        closeButton = new JButton("Close");
        tutorialPanel.add(closeButton, BorderLayout.SOUTH);


        JLabel titleLabel = new JLabel();
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        titleLabel.setText("Welcome to Quizzr!");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel tutorialLabel = new JLabel("<html><br/>If you need help getting started in your journey,<br/> please follow the steps below.<br/><br/>" +
                "1. Select a course from the list on the far left.<br/>" +
                "2. Select a module from \"Available modules\".<br/>" +
                "3. Click on either \"Quiz\" or \"Flashcards\".<br/><br/>" +
                "Have fun and good luck! </html>");
        tutorialLabel.setFont(new Font("Montserrat", Font.PLAIN, 12));
        tutorialLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        tutorialPanel.add(titleLabel, BorderLayout.NORTH);
        tutorialTextPanel.add(tutorialLabel);

        tutorialPanel.add(tutorialTextPanel, BorderLayout.CENTER);

    }

    /**
     * Sets up the layout of the panel
     *
     * @author Ahmad Maarouf
     */
    public void setupLayout() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(new Color(255, 249, 163));
        topPanel.setPreferredSize(new Dimension(getWidth(), getHeight()+30));
        JLabel displayLabel = new JLabel("Available modules", SwingConstants.CENTER);
        displayLabel.setFont(new Font("Gorgia", Font.BOLD, 20));
        displayLabel.setForeground(new Color(10, 10, 40));

        topPanel.add(displayLabel, BorderLayout.CENTER);

        ImageIcon icon = new ImageIcon(getClass().getResource("/view/pics/tinylogo1.png"));
        tutorialButton = new JButton();
        tutorialButton.setIcon(icon);
        tutorialButton.setSize(20, 20);
        tutorialButton.setBorder(BorderFactory.createEmptyBorder());
        topPanel.add(tutorialButton, BorderLayout.EAST);


        //add(displayLabel, BorderLayout.NORTH);
        //add(moduleScrollPane, BorderLayout.CENTER);

        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 2));

        modulePanel = new JPanel(new BorderLayout());

        modulePanel.add(topPanel, BorderLayout.NORTH);
        modulePanel.add(moduleScrollPane, BorderLayout.CENTER);

        setupTutorialPanel();

        centerPanel.add(modulePanel);
        centerPanel.add(tutorialPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    public void saveTrueOrFalseQuestion(String query, List<String> alternatives, int points, String correctAnswer) {
        mainFrame.saveTrueOrFalseQuestion(query, alternatives, points, correctAnswer, selectedCourse, selectedModule);
    }

    public void saveMultipleChoiceToFile(String query, List<String> alternatives, int points, String correctAnswer) {
        mainFrame.saveMultipleChoiceToFile(query, alternatives, points, correctAnswer, selectedCourse, selectedModule);
    }

    public void saveMatchingToFile(String query, List<String> statements, List<String> matches, int points, HashMap<String, Integer> correctMatches) {
        mainFrame.saveMatchingToFile(query, statements, matches, points, correctMatches, selectedCourse, selectedModule);
    }

    /**
     * Creates the necessary data components used to show the list of modules.
     * moduleListMap is used to link modules to a specific course.
     *
     * @author Ahmad Maarouf
     */
    public void createDataComponents() {
        moduleListMap = new HashMap<>();
        moduleListModel = new DefaultListModel<>();
        moduleList = new JList<>(moduleListModel);
        moduleList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        moduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        moduleScrollPane = new JScrollPane(moduleList);
        moduleScrollPane.setBackground(new Color(52,69,140));
        moduleScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(52,69,140);
                this.trackColor = new Color(255, 249, 163);
            }
        });
        moduleScrollPane.setVisible(true);
    }


    /**
     * Displays the modules available for a selected course within a selected program.
     * Called by leftPanel when a course is selected.
     *
     * @param selectedProgram a string with the selected programs name
     * @param selectedCourse  a string with the selected courses name
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
        generalQuizButton.setEnabled(true);
        if (isAdmin) {
            addModuleButton.setEnabled(true);
        }
        revalidate();
        repaint();
    }

    /**
     * Disables module buttons when a module is not chosen.
     *
     * @author Ahmad Maarouf
     */
    public void disableButtons() {
        quizButton.setEnabled(false);
        flashcardsButton.setEnabled(false);
        generalQuizButton.setEnabled(false);
        if (isAdmin) {
            addModuleButton.setEnabled(false);
            editModuleButton.setEnabled(false);
            deleteModuleButton.setEnabled(false);
            addQuestionButton.setEnabled(false);
        }

    }

    /**
     * Enables module buttons when a module is selected.
     *
     * @author Ahmad Maarouf
     */
    public void enableButtons() {
        quizButton.setEnabled(true);
        flashcardsButton.setEnabled(true);
        generalQuizButton.setEnabled(true);
        if (isAdmin) {
            addModuleButton.setEnabled(true);
            editModuleButton.setEnabled(true);
            deleteModuleButton.setEnabled(true);
            addQuestionButton.setEnabled(true);
        }

    }

    /**
     * Creates the necessary buttons for opening quiz and flashcards for the selected module,
     * as well as adding, editing and removing modules.
     *
     * @author Ahmad Maarouf
     */
    public void createButtons() {
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(255, 249, 163));
        buttonPanel.setPreferredSize(new Dimension(getPreferredSize().width, 50));
        quizButton = createStyledButton("Quiz");
        ImageIcon icon = new ImageIcon(getClass().getResource("/view/pics/quizIcon.png"));
        quizButton.setIcon(icon);

        flashcardsButton = createStyledButton("FlashCards");
        icon = new ImageIcon(getClass().getResource("/view/pics/flashcardIcon.jpg"));
        flashcardsButton.setIcon(icon);

        generalQuizButton = createStyledButton("Generate course quiz");
        icon = new ImageIcon(getClass().getResource("/view/pics/tinylogo1.png"));
        generalQuizButton.setIcon(icon);

        if (isAdmin) {
            addModuleButton = createStyledButton("Add module");
            editModuleButton = createStyledButton("Edit");
            deleteModuleButton = createStyledButton("Delete");
            addQuestionButton = createStyledButton("Add question");
        }

        disableButtons();
        buttonPanel.add(quizButton);
        buttonPanel.add(flashcardsButton);
        buttonPanel.add(generalQuizButton);

        if (isAdmin) {
            buttonPanel.add(addQuestionButton);
            buttonPanel.add(addModuleButton);
            buttonPanel.add(editModuleButton);
            buttonPanel.add(deleteModuleButton);
        }

        quizButton.setFont(new Font("Montserrat", Font.PLAIN, 14));
        flashcardsButton.setFont(new Font("Montserrat", Font.PLAIN, 14));
        generalQuizButton.setFont(new Font("Montserrat", Font.PLAIN, 14));
        if (isAdmin) {
            addModuleButton.setFont(new Font("Montserrat", Font.PLAIN, 14));
            editModuleButton.setFont(new Font("Montserrat", Font.PLAIN, 14));
            deleteModuleButton.setFont(new Font("Montserrat", Font.PLAIN, 14));
            addQuestionButton.setFont(new Font("Montserrat", Font.PLAIN, 14));
        }

        buttonPanel.setVisible(true);
    }

    /**
     * Clears the module list.
     * Called when the list of modules needs to be updated.
     *
     * @author Ahmad Maarouf
     */
    public void clearModuleList() {
        moduleListModel.clear();
    }

    /**
     * Updates the current list of modules according to what course is selected in LeftPanel.
     *
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
     *
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

            new MainQuizFrame(selectedCourse, selectedModule, mainFrame);
            this.mainFrame.setVisible(false);
        });

        if (isAdmin) {
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

            addQuestionButton.addActionListener(e -> {
                addQuestionFrame = new AddQuestionFrame(this);
            });
        }

        closeButton.addActionListener(e -> {
            centerPanel.removeAll();
            centerPanel.setLayout(new BorderLayout());
            centerPanel.add(modulePanel);
            tutorialOpen = false;
            revalidate();
            repaint();
        });

        tutorialButton.addActionListener(e -> {
            if (tutorialOpen) {
                centerPanel.removeAll();
                centerPanel.setLayout(new BorderLayout());
                centerPanel.add(modulePanel);
                tutorialOpen = false;
            } else {
                centerPanel.removeAll();
                centerPanel.setLayout(new GridLayout(0, 2));
                centerPanel.add(modulePanel);
                centerPanel.add(tutorialPanel);
                tutorialOpen = true;
            }
            revalidate();
            repaint();

        });

        tutorialButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                tutorialButton.setBackground(new Color(72, 173, 240));
                //tutorialButton.setBorder(BorderFactory.createBevelBorder(0));
            }

            public void mouseExited(MouseEvent e) {
                tutorialButton.setBackground(null);
                //tutorialButton.setBorder(BorderFactory.createEmptyBorder());
            }
        });

        generalQuizButton.addActionListener(e -> {
            mainFrame.generateGeneralQuiz();
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

    /**
     * Adds a module to the list of available modules for the selected course and also,
     * in the file that stores modules.
     * This is done through mainFrame which calls on controller.
     *
     * @author Ahmad Maarouf
     */
    public void addModule() {
        String moduleName = JOptionPane.showInputDialog("Please enter a module name");
        if (moduleName != null) {
            if (!mainFrame.ifModuleExists(selectedProgram, selectedCourse, moduleName)) {
                mainFrame.addNewModule(selectedCourse, moduleName);
                disableButtons();
                addModuleButton.setEnabled(true);
                updateList();
            } else {
                JOptionPane.showMessageDialog(null, moduleName + " already exists for " + selectedCourse + "! Please choose another module name.");
            }

        }
    }

    /**
     * Edits a module in the list of available modules for the selected course and also,
     * in the file that stores modules.
     * This is done through mainFrame which calls on controller.
     *
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
     *
     * @author Ahmad Maarouf
     */
    public void removeModule() {
        if (mainFrame.deleteConfirmation(selectedModule)) {
            //Here we write what happens when we press the delete button
            mainFrame.deleteModule(selectedCourse, selectedModule);
            disableButtons();
            addModuleButton.setEnabled(true);
            updateList();
        }
        ;
    }
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial",Font.BOLD,14));

        Color baseColor = new Color(52, 69,140);
        Color haverColor = new Color(90, 140, 230);
        Color borderColor = baseColor.darker();

        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        //button.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        Border lineBorder= BorderFactory.createLineBorder(borderColor,4);
        Border emptyBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        button.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(haverColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(haverColor.darker(), 2),
                        BorderFactory.createEmptyBorder(2, 2,2, 2)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor, 2),
                        BorderFactory.createEmptyBorder(2, 2, 2, 2)
                ));
            }
        });

        return button;
    }
}
