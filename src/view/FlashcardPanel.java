package view;

import javax.swing.*;

public class FlashcardPanel {
    private JFrame flashcardFrame;
    private String chosenModule;

    public FlashcardPanel(String chosenModule) {
        this.chosenModule = chosenModule;
        createFrame();
    }

    public void createFrame() {
        JFrame flashcardFrame = new JFrame("FlashCards Options");
        flashcardFrame.setSize(300, 200);
        flashcardFrame.setLocationRelativeTo(null);
        flashcardFrame.add(new JLabel("List of flashcards goes here...", SwingConstants.CENTER));
        flashcardFrame.setVisible(true);
    }
}
