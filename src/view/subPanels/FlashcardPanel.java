package view.subPanels;

import controller.Controller;

import javax.swing.*;

public class FlashcardPanel {
    private JFrame flashcardFrame;
    private String selectedModuleName;
    private Controller controller;

    public FlashcardPanel(String selectedModuleName, Controller controller) {
        this.selectedModuleName = selectedModuleName;
        this.controller = controller;
        createFrame();
    }

    public void createFrame() {
        flashcardFrame = new JFrame("Flashcards for " + selectedModuleName);
        flashcardFrame.setSize(400, 300);
        flashcardFrame.setLocationRelativeTo(null);

        // Hämtar flashcard-innehåll från controller
        String[] flashcardTexts = controller.getFlashcardTextsForModule(selectedModuleName);

        if (flashcardTexts == null || flashcardTexts.length == 0) {
            flashcardFrame.add(new JLabel("No flashcards found for this module.", SwingConstants.CENTER));
        } else {
            JList<String> flashcardList = new JList<>(flashcardTexts);
            flashcardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(flashcardList);
            flashcardFrame.add(scrollPane);
        }

        flashcardFrame.setVisible(true);
    }
}

