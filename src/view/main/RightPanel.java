package view.main;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    private JPanel buttonPanel;
    private JButton quizButton;
    private JButton flashcardsButton;
    private JButton addModuleButton;
    private HashMap<String, String[]> moduleListMap;
    private DefaultListModel<String> moduleListModel;
    private JList<String> moduleList;
    private JScrollPane moduleScrollPane;
    private String chosenModule;


    public RightPanel() {
        setLayout(new BorderLayout());
        createDataList();
        createDataComponents();
        createButtons();
        addEventListener();
        setupLayout();
=======

    private MainFrame mainFrame;
    private CenterPanel centerContainer;
    private String[] tabOptions;
    private JList<String> listOfTabs;
    private JScrollPane scrollPane;

    public RightPanel(MainFrame mainFrame, CenterPanel centerContainer) {
        this.mainFrame = mainFrame;
        this.centerContainer = centerContainer;
>>>>>>> Stashed changes

        createList();

=======

    private MainFrame mainFrame;
    private CenterPanel centerContainer;
    private String[] tabOptions;
    private JList<String> listOfTabs;
    private JScrollPane scrollPane;

    public RightPanel(MainFrame mainFrame, CenterPanel centerContainer) {
        this.mainFrame = mainFrame;
        this.centerContainer = centerContainer;

        createList();

>>>>>>> Stashed changes
        JButton logOutButton = new JButton("Log Out");
        logOutButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel tabLabel = new JLabel(" Select a tab");
        tabLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tabLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.setLayout(new BorderLayout());
        this.setSize(150, 400);

        this.add(tabLabel, BorderLayout.NORTH);
        this.add(scrollPane);
        this.add(logOutButton, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public void createList() {
        //tabOptions will be fetched from controller
        String[] tabOptions = { "Modules", "Account", "Quiz" };
        //------------------------------------------

<<<<<<< Updated upstream
<<<<<<< Updated upstream
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
    }

    public void courseChosen(String courseName) {
        moduleListModel.clear();

        //-------------------------------------------------------------------
        //Here we contact the Controller to fetch the available list of modules for the chosen course
        for (String item : moduleListMap.getOrDefault(courseName, new String[]{})) {
            moduleListModel.addElement(item);
=======
        DefaultListModel listModel = new DefaultListModel();
        for (String option : tabOptions) {
            listModel.addElement(option);
>>>>>>> Stashed changes
=======
        DefaultListModel listModel = new DefaultListModel();
        for (String option : tabOptions) {
            listModel.addElement(option);
>>>>>>> Stashed changes
        }

        listOfTabs = new JList<>(tabOptions);

        listOfTabs.setFixedCellWidth(150);
        listOfTabs.setFont(new Font("Arial", Font.ROMAN_BASELINE, 24));

        scrollPane = new JScrollPane(listOfTabs);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
    }
}
