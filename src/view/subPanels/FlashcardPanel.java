package view.subPanels;

import model.FlashCard;
import view.main.MainFrame;

import javax.swing.*;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

/**
 * FlashcardPanel is a GUI class that displays flashcards associated with
 * a selected module. It allows the user to navigate and view question-answer
 * pairs stored in FlashCard objects.
 *
 * This panel is triggered from CenterModulePanel and follows the MVC pattern.
 * It fetches data through the MainFrame → Controller → Module structure.
 *
 * @author Salman Warsame
 */
public class FlashcardPanel extends JFrame {
    private List<FlashCard> flashCards;
    private int currentIndex;
    private JLabel frontLabel;
    private JLabel backLabel;
    private JButton showBackButton;
    private JButton nextButton;

    private String selectedProgram;
    private String selectedCourse;
    private String selectedModule;
    private MainFrame mainFrame;

    /**
     * Initializes the flashcard window with all UI components,
     * retrieves flashcards via controller, and displays the first card.
     *
     * @param selectedProgram the name of the selected program
     * @param selectedCourse  the name of the selected course
     * @param selectedModule  the name of the selected module
     * @param mainFrame       reference to MainFrame for controller access
     */
    public FlashcardPanel(String selectedProgram, String selectedCourse, String selectedModule, MainFrame mainFrame) {
        this.selectedProgram = selectedProgram;
        this.selectedCourse = selectedCourse;
        this.selectedModule = selectedModule;
        this.mainFrame = mainFrame;

        setTitle("Flashcards");
        setSize(new Dimension(400, 300));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setupComponents();
        loadFlashcards();
        updateCardDisplay();

        setVisible(true);
    }

    /**
     * Sets up the layout, buttons, and text display areas.
     * Adds event listeners to the navigation buttons.
     */
    private void setupComponents() {
        setLayout(new BorderLayout());

        frontLabel = new JLabel("", SwingConstants.CENTER);
        frontLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(frontLabel, BorderLayout.NORTH);

        backLabel = new JLabel("", SwingConstants.CENTER);
        backLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        add(backLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        showBackButton = new JButton("Show Answer");
        nextButton = new JButton("Next");

        buttonPanel.add(showBackButton);
        buttonPanel.add(nextButton);
        add(buttonPanel, BorderLayout.SOUTH);

        showBackButton.addActionListener(this::handleShowAnswer);
        nextButton.addActionListener(this::handleNext);
    }

    /**
     * Loads the list of flashcards for the selected module.
     * Data is fetched from the controller through MainFrame.
     */
    private void loadFlashcards() {
        this.flashCards = mainFrame.getFlashcards(selectedProgram, selectedCourse, selectedModule);
        currentIndex = 0;
    }

    /**
     * Updates the display with the current flashcard's question (front side).
     * Hides the answer initially.
     */
    private void updateCardDisplay() {
        if (flashCards != null && !flashCards.isEmpty()) {
            FlashCard current = flashCards.get(currentIndex);
            frontLabel.setText("Q: " + current.getFrontContent());
            backLabel.setText(""); // Hide answer until requested
        } else {
            frontLabel.setText("No flashcards available");
            backLabel.setText("");
            showBackButton.setEnabled(false);
            nextButton.setEnabled(false);
        }
    }

    /**
     * Reveals the answer (back content) of the current flashcard.
     */
    private void handleShowAnswer(ActionEvent e) {
        if (!flashCards.isEmpty()) {
            FlashCard current = flashCards.get(currentIndex);
            backLabel.setText("A: " + current.getBackContent());
        }
    }

    /**
     * Navigates to the next flashcard in the list and updates the display.
     * If at the end of the list, loops back to the beginning.
     */
    private void handleNext(ActionEvent e) {
        if (!flashCards.isEmpty()) {
            currentIndex = (currentIndex + 1) % flashCards.size();
            updateCardDisplay();
        }
    }
}




