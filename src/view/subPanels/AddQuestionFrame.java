package view.subPanels;

import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class AddQuestionFrame extends JFrame {

    private JComboBox<String> typeComboBox;
    private JPanel mainPanel;

    public AddQuestionFrame(MainFrame mainFrame) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(mainPanel);

        createTypePanel();
        addItemListeners();
        setLocationRelativeTo(mainFrame);

        add(scrollPane);
        setVisible(true);
        pack();
    }

    public void createTypePanel() {
        JPanel typePanel = new JPanel();
        JLabel typeLabel = new JLabel("Type of question: ");

        typeComboBox = new JComboBox<>();
        String[] types = new String[]{"Multiple choice", "True/False", "Matching"};
        typeComboBox.setModel(new DefaultComboBoxModel<>(types));

        typePanel.add(typeLabel);
        typePanel.add(typeComboBox);
        mainPanel.add(typePanel);
    }

    public void createAndShowMultiPanel() {
        JPanel multiPanel = new JPanel(new GridLayout(0, 2));
        //multiPanel.setLayout(new BoxLayout(multiPanel, BoxLayout.Y_AXIS));

        JLabel questionLabel = new JLabel("Question: ");
        JTextField questionTextField = new JTextField(10);
        multiPanel.add(questionLabel);
        multiPanel.add(questionTextField);
        ArrayList<JTextField> multiAlternatives = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            JLabel alternative = new JLabel("Alternative: " + i);
            JTextField alternativeTextField = new JTextField(10);
            multiAlternatives.add(alternativeTextField);
            multiPanel.add(alternative);
            multiPanel.add(alternativeTextField);
        }

        mainPanel.add(multiPanel, 0);
        revalidate();
        repaint();
    }

    public void addItemListeners() {

        typeComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (typeComboBox.getSelectedItem() == "Multiple choice"); {
                    createAndShowMultiPanel();
                }
            }
        });
    }
}
