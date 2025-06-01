package view.subPanels;

import model.FlashCard;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import java.awt.event.ActionEvent;

/**
 * FlashcardPanel is a GUI class that displays flashcards associated with
 * a selected module. It allows the user to navigate and view question-answer
 * pairs stored in FlashCard objects. Includes a button to create a new flashcard.
 *
 * This panel is triggered from CenterModulePanel and follows the MVC pattern.
 * It fetches data through the MainFrame → Controller → Module structure.
 *
 * @author Salman Warsame
 */
public class FlashcardPanel extends JFrame {

    private List<String> frontContent;
    private List<String> backContent;
    private int currentIndex;

    private JPanel mainPanel;
    private JPanel componentsPanel;
    private JPanel infoPanel;
    private JPanel flashcardPanel;
    private JPanel buttonsPanel;

    private JLabel contentLabel;
    private JLabel infoLabel;

    private JButton backButton;
    private JButton nextButton;
    private JButton createFlashcardButton;

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

        setUpPanels();
        loadFlashcards();
        updateCardDisplay();

        setVisible(true);
    }

    public void setUpPanels(){
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 249, 163));

        componentsPanel = new JPanel();
        componentsPanel.setLayout(new BoxLayout(componentsPanel, BoxLayout.Y_AXIS));
        componentsPanel.setBackground(new Color(255, 249, 163));

        infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(new Color(255, 249, 163));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        flashcardPanel = new JPanel(new BorderLayout());
        flashcardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        flashcardPanel.setBackground(new Color(52, 69,140));

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setBackground(new Color(255, 249, 163));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        setUpInfoPanel();
        componentsPanel.add(infoPanel);
        componentsPanel.add(Box.createVerticalStrut(20));

        setUpFlashcardsPanel();
        setListenersForFlashcards();

        createButtons();
        componentsPanel.add(Box.createVerticalStrut(10));
        componentsPanel.add(buttonsPanel);

        mainPanel.add(componentsPanel, BorderLayout.CENTER);
    }

    public void setUpInfoPanel(){
        infoLabel = new JLabel("Click on flashcard to flip it!");
        infoLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        infoLabel.setAlignmentX(SwingConstants.CENTER);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setVerticalAlignment(SwingConstants.CENTER);

        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        infoPanel.add(infoLabel, BorderLayout.CENTER);
        infoPanel.setMaximumSize(new Dimension(400, 70));
    }

    public void setUpFlashcardsPanel(){
        flashcardPanel.setPreferredSize(new Dimension(750, 400));
        flashcardPanel.setMaximumSize(new Dimension(750, 400));
        flashcardPanel.setMinimumSize(new Dimension(750, 400));

        contentLabel = new JLabel("");
        contentLabel.setAlignmentX(SwingConstants.CENTER);
        contentLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        contentLabel.setForeground(Color.WHITE);
        contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentLabel.setVerticalAlignment(SwingConstants.CENTER);

        flashcardPanel.add(contentLabel, BorderLayout.CENTER);
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapper.setBackground(new Color(255, 249, 163));
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40)); // vänster/höger padding
        wrapper.add(flashcardPanel);

        componentsPanel.add(wrapper);
    }

    /**
     * Sets up the layout, buttons, and text display areas.
     * Adds event listeners to the navigation buttons.
     *
     * @author Sara Sheikho
     * @author Salman Warsame
     */
    public void createButtons(){
        nextButton = new JButton("Next");
        nextButton.setBackground(new Color(52, 69,140));
        nextButton.setForeground(Color.WHITE);
        nextButton.setPreferredSize(new Dimension(100, 50));
        nextButton.setMaximumSize(new Dimension(100, 50));
        nextButton.setMinimumSize(new Dimension(100, 50));
        nextButton.setFont(new Font("Montserrat", Font.BOLD, 14));

        createFlashcardButton = new JButton("Create flashcards");
        createFlashcardButton.setBackground(new Color(52, 69,140));
        createFlashcardButton.setForeground(Color.WHITE);
        createFlashcardButton.setPreferredSize(new Dimension(250, 50));
        createFlashcardButton.setMaximumSize(new Dimension(250, 50));
        createFlashcardButton.setMinimumSize(new Dimension(250, 50));
        createFlashcardButton.setFont(new Font("Montserrat", Font.BOLD, 14));

        backButton = new JButton("Back");
        backButton.setBackground(new Color(52, 69,140));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(100, 50));
        backButton.setMaximumSize(new Dimension(100, 50));
        backButton.setMinimumSize(new Dimension(100, 50));
        backButton.setFont(new Font("Montserrat", Font.BOLD, 14));


        buttonsPanel.add(backButton);
        buttonsPanel.add(Box.createHorizontalStrut(150));
        buttonsPanel.add(createFlashcardButton);
        buttonsPanel.add(Box.createHorizontalStrut(150));
        buttonsPanel.add(nextButton);

        addButtonEvents();
        backButton.addActionListener(this::showPreviousFlashcard);
        nextButton.addActionListener(this::showNextFlashcard);
        createFlashcardButton.addActionListener(e -> new CreateFlashcardFrame(selectedCourse, selectedModule, mainFrame));
    }

    public void addButtonEvents(){
        nextButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(nextButton.isEnabled()) {
                    nextButton.setBackground(new Color(90, 140, 230));
                    nextButton.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(90, 140, 230).darker(), 2),
                            BorderFactory.createEmptyBorder(10, 20, 10, 20)
                    ));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(new Color(52, 69,140));
                nextButton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(52, 69,140), 2),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
        });

        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(backButton.isEnabled()) {
                    backButton.setBackground(new Color(90, 140, 230));
                    backButton.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(90, 140, 230).darker(), 2),
                            BorderFactory.createEmptyBorder(10, 20, 10, 20)
                    ));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(52, 69,140));
                backButton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(52, 69,140), 2),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
        });

        createFlashcardButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(90, 140, 230));
                backButton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(90, 140, 230).darker(), 2),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));

            }
        });
    }

    public void setListenersForFlashcards() {
        flashcardPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                setBackground(new Color(90, 140, 230));
                repaint();
            }
        });

        flashcardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(52, 69, 140));
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                handleShowAnswer(e);
            }
        });
    }

    /**
     * Loads the list of flashcards for the selected module.
     * Data is fetched from the controller through MainFrame.
     */
    private void loadFlashcards() {
        this.frontContent = mainFrame.getFlashCardsFrontContent(selectedCourse, selectedModule);
        this.backContent = mainFrame.getFlashCardsBackContent(selectedCourse, selectedModule);
        currentIndex = 0;
    }

    /**

     */
    private void updateCardDisplay(){
        if(!frontContent.isEmpty()) {
            if (frontContent.get(currentIndex) != null && !frontContent.get(currentIndex).isEmpty()) {
                contentLabel.setText("Q: " + frontContent.get(currentIndex));
            } else {
                infoLabel.setText("No flashcards available");
                contentLabel.setText("");
                backButton.setEnabled(false);
                nextButton.setEnabled(false);
            }
        }else{
            infoLabel.setText("No flashcards available");
            contentLabel.setText("");
            backButton.setEnabled(false);
            nextButton.setEnabled(false);
        }
    }

    /**
     *
     * @param e
     */
    private void handleShowAnswer(MouseEvent e) {
        if(!backContent.isEmpty()) {
            if (backContent.get(currentIndex) != null && !backContent.get(currentIndex).isEmpty()) {
                contentLabel.setText(backContent.get(currentIndex));
            }
        }
    }

    /**
     * Navigates to the next flashcard in the list and updates the display.
     * If at the end of the list, loops back to the beginning.
     */
    private void handleNextS(ActionEvent e) {
        currentIndex = (currentIndex + 1) % frontContent.size();
        updateCardDisplay();
    }

    public void showPreviousFlashcard(ActionEvent e){
        if (!frontContent.isEmpty()) {
            currentIndex = (currentIndex - 1 + frontContent.size()) % frontContent.size();
            updateCardDisplay();
        }

    }

    /**
     * Navigates to the next flashcard in the list and updates the display.
     * If at the end of the list, loops back to the beginning.
     *
     * @author Sara
     * @author Salman
     */
    private void showNextFlashcard(ActionEvent e) {
        currentIndex = (currentIndex + 1) % frontContent.size();
        updateCardDisplay();
    }
}
