package view.subPanels;

import controller.Controller;
import model.FlashCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FlashcardPanel extends JPanel {
    private Controller controller;
    private String moduleName;
    private JList<String> flashcardList;
    private DefaultListModel<String> flashcardListModel;
}

/*

    public FlashcardPanel(String moduleName, Controller controller) {
        this.moduleName = moduleName;
        this.controller = controller;
       
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Flashcards for module: " + moduleName, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

           // === Lista med flashcards ===
        flashcardListModel = new DefaultListModel<>();
        flashcardList = new JList<>(flashcardListModel);
        flashcardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(flashcardList);
        add(scrollPane, BorderLayout.CENTER);

        // === Hämta flashcards via controller ===
        ArrayList<FlashCard> flashCards = controller.getFlashcardsForModule(moduleName);
        for (int i = 0; i < flashCards.size(); i++) {
                flashcardListModel.addElement(flashCards.get(i).getFrontContent());
        }
    }

        // === Knappar längst ner ===
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // TODO: Add, Edit och Delete-funktioner kan kopplas till controller-metoder


/**
Metod som ska vara i controller: 
public ArrayList<FlashCard> getFlashcardsForModule(String moduleName) {
    for (int i = 0; i < programList.size(); i++) {
        Program program = programList.get(i);
        List<Course> courses = program.getCourses();
        for (int j = 0; j < courses.size(); j++) {
            Course course = courses.get(j);
            List<Module> modules = course.getModules();
            for (int k = 0; k < modules.size(); k++) {
                Module module = modules.get(k);
                if (module.getName().equals(moduleName)) {
                    return new ArrayList<>(module.getFlashCards());
                }
            }
        }
    }
    return new ArrayList<>();
}
*/



