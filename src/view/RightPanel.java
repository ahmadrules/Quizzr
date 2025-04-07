package view;


import javax.swing.*;
import java.awt.*;
import java.util.HashMap;


public class RightPanel extends JPanel {
    private JPanel buttonPanel;
    private JButton quizButton;
    private JButton flashcardsButton;
    private HashMap<String, String[]> moduleListMap;
    private DefaultListModel<String> moduleListModel;
    private JList<String> moduleList;
    private JScrollPane moduleScrollPane;


    public RightPanel() {
        setLayout(new BorderLayout());
        createDataList();

        JLabel displayLabel = new JLabel("Available quizzes", SwingConstants.CENTER);
        add(displayLabel, BorderLayout.NORTH);
        add(moduleScrollPane, BorderLayout.CENTER);

        createButtons();
        addEventListener();
        this.add(buttonPanel, BorderLayout.SOUTH);

    }

    public void createDataList() {
        moduleListMap = new HashMap<>();
        moduleListMap.put("Course A1", new String[]{"Module A1-1", "Module A1-2", "Module A1-3"});
        moduleListMap.put("Course A2", new String[]{"Module A2-1", "Module A2-2", "Module A2-3"});
        moduleListMap.put("Course A3", new String[]{"Module A3-1", "Module A3-2", "Module A3-3"});

        moduleListMap.put("Course B1", new String[]{"Module B1-1", "Module B1-2", "Module B1-3"});
        moduleListMap.put("Course B2", new String[]{"Module B2-1", "Module B2-2", "Module B2-3"});
        moduleListMap.put("Course B3", new String[]{"Module B3-1", "Module B3-2", "Module B3-3"});

        moduleListMap.put("Course C1", new String[]{"Module C1-1", "Module C1-2", "Module C1-3"});
        moduleListMap.put("Course C2", new String[]{"Module C2-1", "Module C2-2", "Module C2-3"});
        moduleListMap.put("Course C3", new String[]{"Module C3-1", "Module C3-2", "Module C3-3"});

        moduleListModel = new DefaultListModel<>();
        moduleList = new JList<>(moduleListModel);
        moduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        moduleScrollPane = new JScrollPane(moduleList);
        moduleScrollPane.setVisible(false);
    }

    public HashMap<String, String[]> getModuleListMap() {
        return moduleListMap;
    }

    public void disableButtons() {
        quizButton.setEnabled(false);
        flashcardsButton.setEnabled(false);
    }

    public void disableScrollPane() {
        moduleScrollPane.setVisible(false);
    }

    public void moduleChosen() {

    }


    public void courseChosen(String courseName) {
        moduleListModel.clear();
        buttonPanel.setVisible(true);
        for (String item : moduleListMap.getOrDefault(courseName, new String[]{})) {
            moduleListModel.addElement(item);
        }
        moduleScrollPane.setVisible(true);
        // === NY KOD ===
        disableButtons();
        // === SLUT PÅ NY KOD ===
        revalidate();
        repaint();
    }

    public void enableButtons() {
        quizButton.setEnabled(true);
        flashcardsButton.setEnabled(true);
    }

    public void createButtons() {
        buttonPanel = new JPanel(new FlowLayout());
        quizButton = new JButton("Quiz");
        flashcardsButton = new JButton("FlashCards");
        quizButton.setEnabled(false);
        flashcardsButton.setEnabled(false);
        buttonPanel.add(quizButton);
        buttonPanel.add(flashcardsButton);
        buttonPanel.setVisible(false);
    }

    public void addEventListener() {
        flashcardsButton.addActionListener(e -> {
            JFrame flashcardFrame = new JFrame("FlashCards Options");
            flashcardFrame.setSize(300, 200);
            flashcardFrame.setLocationRelativeTo(null);
            flashcardFrame.add(new JLabel("List of flashcards goes here...", SwingConstants.CENTER));
            flashcardFrame.setVisible(true);
        });

        quizButton.addActionListener(e -> {
            JFrame quizFrame = new JFrame("Quiz Options");
            quizFrame.setSize(300, 200);
            quizFrame.setLocationRelativeTo(null);
            quizFrame.add(new JLabel("List of quizzes goes here...", SwingConstants.CENTER));
            quizFrame.setVisible(true);
        });

        moduleList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedModule = moduleList.getSelectedValue();
                if (selectedModule != null) {
                    enableButtons();
                }
            }
        });


        /*coursesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedSubItem = coursesList.getSelectedValue();
                if (selectedSubItem != null) {
                    rightPanel.courseChosen(selectedSubItem);
                }
            }
        });

         */
    }
}
