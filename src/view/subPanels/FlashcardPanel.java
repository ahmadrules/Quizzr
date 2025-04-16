package view.subPanels;

import model.FlashCard;
import model.Module;

import javax.swing.*;
import java.util.List;

public class FlashcardPanel {
    private JFrame flashcardFrame;
    private Module selectedModule;

    public FlashcardPanel(Module selectedModule) {
        this.selectedModule = selectedModule;
        createFrame();
    }

    public void createFrame() {
        flashcardFrame = new JFrame("Flashcards for " + selectedModule.getName());
        flashcardFrame.setSize(400, 300);
        flashcardFrame.setLocationRelativeTo(null);

        // Hämtar flashcards från modulen
        List<FlashCard> flashCards = selectedModule.getFlashCards();

        // Omvandlar flashcards till en array med innehåll för visning
        String[] cardContents = new String[flashCards.size()];
        for (int i = 0; i < flashCards.size(); i++) {
            cardContents[i] = flashCards.get(i).getContent();
        }

        // Skapar och visar listan i ett scrollfönster
        JList<String> flashcardList = new JList<>(cardContents);
        flashcardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(flashcardList);

        flashcardFrame.add(scrollPane);
        flashcardFrame.setVisible(true);
    }
}

