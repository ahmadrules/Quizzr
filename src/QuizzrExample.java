import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class QuizzrExample implements Runnable {

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Three-Level List Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);

        // ======= TOP PANEL =======
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(230, 230, 230));

        JLabel topLabel = new JLabel("Select a program from the left", SwingConstants.LEFT);
        topLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        topPanel.add(topLabel, BorderLayout.WEST);
        topPanel.add(new JSeparator(), BorderLayout.SOUTH);

        // ======= LEFT PANEL =======
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // "Programs" Label
        JLabel programsLabel = new JLabel(" Programs");
        programsLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Main List (Programs)
        String[] categories = {"Program 1", "Program 2", "Program 3"};
        DefaultListModel<String> mainListModel = new DefaultListModel<String>();
        for (String category : categories) mainListModel.addElement(category);
        JList<String> mainList = new JList<>(mainListModel);
        mainList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane mainListScrollPane = new JScrollPane(mainList);

        // "Courses" Label
        JLabel coursesLabel = new JLabel(" Courses");
        coursesLabel.setFont(new Font("Arial", Font.BOLD, 14));
        coursesLabel.setVisible(false);

        // Sublist (Initially Empty)
        DefaultListModel<String> sublistModel = new DefaultListModel<>();
        JList<String> sublist = new JList<>(sublistModel);
        sublist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sublistScrollPane = new JScrollPane(sublist);
        sublistScrollPane.setVisible(false);

        // Add labels and lists to the left panel
        leftPanel.add(programsLabel);
        leftPanel.add(mainListScrollPane);
        leftPanel.add(coursesLabel);
        leftPanel.add(sublistScrollPane);

        // ======= RIGHT PANEL =======
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Related List
        DefaultListModel<String> relatedListModel = new DefaultListModel<>();
        JList<String> relatedList = new JList<>(relatedListModel);
        relatedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane relatedListScrollPane = new JScrollPane(relatedList);
        relatedListScrollPane.setVisible(false);

        // Display label for selection
        JLabel displayLabel = new JLabel("Available quizzes", SwingConstants.CENTER);
        rightPanel.add(displayLabel, BorderLayout.NORTH);
        rightPanel.add(relatedListScrollPane, BorderLayout.CENTER);

        // Add Top Panel to Right Panel
        JPanel rightContainer = new JPanel(new BorderLayout());
        rightContainer.add(topPanel, BorderLayout.NORTH);
        rightContainer.add(rightPanel, BorderLayout.CENTER);

        //  mainlist
        Map<String, String[]> sublistData = new HashMap<>();
        sublistData.put("Program 1", new String[]{"Course A1", "Course A2", "Course A3"});
        sublistData.put("Program 2", new String[]{"Course B1", "Course B2", "Course B3"});
        sublistData.put("Program 3", new String[]{"Course C1", "Course C2", "Course C3"});

        // sublist
        Map<String, String[]> relatedListData = new HashMap<>();
        relatedListData.put("Course A1", new String[]{"Detail A1-1", "Detail A1-2", "Detail A1-3"});
        relatedListData.put("Course A2", new String[]{"Detail A2-1", "Detail A2-2", "Detail A2-3"});
        relatedListData.put("Course A3", new String[]{"Detail A3-1", "Detail A3-2", "Detail A3-3"});

        relatedListData.put("Course B1", new String[]{"Detail B1-1", "Detail B1-2", "Detail B1-3"});
        relatedListData.put("Course B2", new String[]{"Detail B2-1", "Detail B2-2", "Detail B2-3"});
        relatedListData.put("Course B3", new String[]{"Detail B3-1", "Detail B3-2", "Detail B3-3"});

        relatedListData.put("Course C1", new String[]{"Detail C1-1", "Detail C1-2", "Detail C1-3"});
        relatedListData.put("Course C2", new String[]{"Detail C2-1", "Detail C2-2", "Detail C2-3"});
        relatedListData.put("Course C3", new String[]{"Detail C3-1", "Detail C3-2", "Detail C3-3"});

        // ======= EVENT HANDLERS =======

        // Handle Main List Selection (Show Sublist Below Selected Item)
        mainList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedCategory = mainList.getSelectedValue();
                if (selectedCategory != null) {
                    sublistModel.clear();
                    for (String item : sublistData.getOrDefault(selectedCategory, new String[]{})) {
                        sublistModel.addElement(item);
                    }
                    sublistScrollPane.setVisible(true);
                    coursesLabel.setVisible(true);
                    relatedListScrollPane.setVisible(false); // Hide related list when switching programs
                    leftPanel.revalidate();
                    leftPanel.repaint();
                }
            }
        });

        // Handle Sublist Selection (Show Related List in Right Panel)
        sublist.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedSubItem = sublist.getSelectedValue();
                if (selectedSubItem != null) {
                    relatedListModel.clear();
                    for (String item : relatedListData.getOrDefault(selectedSubItem, new String[]{})) {
                        relatedListModel.addElement(item);
                    }
                    relatedListScrollPane.setVisible(true);
                    rightPanel.revalidate();
                    rightPanel.repaint();
                }
            }
        });

        // Handle Related List Selection (Update Display Label)
        relatedList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                displayLabel.setText("Selected: " + relatedList.getSelectedValue());
            }
        });

        // ======= LAYOUT SETUP =======
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightContainer);
        splitPane.setDividerLocation(200);

        frame.add(splitPane);
        frame.setVisible(true);
    }

    @Override
    public void run() {
        createAndShowGUI();
    }
}



