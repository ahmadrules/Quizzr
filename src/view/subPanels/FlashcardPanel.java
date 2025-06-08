package view.subPanels;

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
     * @author Salman Warsame
     * @author Sara Sheikho
     */
    public FlashcardPanel(String selectedProgram, String selectedCourse, String selectedModule, MainFrame mainFrame) {
        this.selectedProgram = selectedProgram;
        this.selectedCourse = selectedCourse;
        this.selectedModule = selectedModule;
        this.mainFrame = mainFrame;

        setTitle("Flashcards");
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setUpPanels();
        loadFlashcards();
        updateCardDisplay();
        add(mainPanel);

        setVisible(true);
    }

    /**
     * Initializes and lays out all major panels of the flashcard interface.
     * <p>
     * This includes the main layout panel, info panel, flashcard panel, and
     * the button panel. It also sets component alignments, colors, spacing,
     * and calls helper methods for flashcard and button setup.
     * </p>
     * @author Sara Sheikho
     */
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

    /**
     * Configures the information panel that displays instructions or tips.
     * <p>
     * Sets the font, alignment, border spacing, and adds the label to the info panel.
     * </p>
     * @author Sara Sheikho
     */
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

    /**
     * Sets up the main flashcard display area.
     * <p>
     * Initializes size constraints and appearance of the flashcard panel,
     * creates the content label, centers it, and wraps it in a FlowLayout panel.
     * Adds the final wrapped panel to the components panel.
     * </p>
     * @author Sara Sheikho
     */
    public void setUpFlashcardsPanel(){
        flashcardPanel.setPreferredSize(new Dimension(700, 400));
        flashcardPanel.setMaximumSize(new Dimension(700, 400));
        flashcardPanel.setMinimumSize(new Dimension(700, 400));

        contentLabel = new JLabel("");
        contentLabel.setAlignmentX(SwingConstants.CENTER);
        contentLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        contentLabel.setForeground(Color.WHITE);
        contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentLabel.setVerticalAlignment(SwingConstants.CENTER);

        flashcardPanel.add(contentLabel, BorderLayout.CENTER);
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapper.setBackground(new Color(255, 249, 163));
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
        wrapper.add(flashcardPanel);

        componentsPanel.add(wrapper);
    }

    /**
     * Creates and configures the buttons used in the UI, including
     * the "Next", "Back", and "Create flashcards" buttons.
     * Sets their colors, sizes, fonts, and adds them to the buttons panel.
     * Also attaches action listeners to handle button click events.
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

    /**
     * Adds mouse event listeners to the buttons to provide
     * hover effects, changing their background color and border
     * when the mouse enters or exits the button area.
     *
     * @author Sara Sheikho
     */
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
                createFlashcardButton.setBackground(new Color(90, 140, 230));
                createFlashcardButton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(90, 140, 230).darker(), 2),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));

            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                createFlashcardButton.setBackground(new Color(52, 69,140));
                createFlashcardButton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(52, 69,140), 2),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
        });
    }

    /**
     * Sets mouse listeners on the flashcard panel to handle
     * mouse movements and clicks:
     * <ul>
     *   <li>Changes background color on mouse movement over the panel.</li>
     *   <li>Resets background color when the mouse exits the panel.</li>
     *   <li>Handles clicks on the flashcard to show the answer.</li>
     * </ul>
     * @author Sara Sheikho
     */
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
     * @author Salman Warsame
     * @author Sara Sheikho
     */
    private void loadFlashcards() {
        this.frontContent = mainFrame.getFlashCardsFrontContent(selectedCourse, selectedModule);
        this.backContent = mainFrame.getFlashCardsBackContent(selectedCourse, selectedModule);
        currentIndex = 0;
    }

    /**
     * Updates the display of the flashcard content based on the current index.
     * <p>
     * If there is valid content on the front side of the current flashcard,
     * it sets the contentLabel text accordingly. Otherwise, it shows a message
     * indicating no flashcards are available and disables navigation buttons.
     * @author Sara Sheikho
     */
    private void updateCardDisplay(){
        if(!frontContent.isEmpty()) {
            if (frontContent.get(currentIndex) != null && !frontContent.get(currentIndex).isEmpty()) {
                contentLabel.setText(frontContent.get(currentIndex));
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
     * Handles the mouse click event to show the answer side of the flashcard.
     * <p>
     * If the current displayed text is the front content, this method switches
     * the display to the back content (the answer) and changes the flashcard
     * panel background color to indicate the answer side.
     * If the back content is already showing, it switches back to the front content.
     *
     * @param e the MouseEvent triggered by clicking the flashcard panel
     * @author Sara Sheikho
     */
    private void handleShowAnswer(MouseEvent e) {
        if(!frontContent.isEmpty()) {
            if (contentLabel.getText().equals(frontContent.get(currentIndex))) {
                if (!backContent.isEmpty()) {
                    if (backContent.get(currentIndex) != null && !backContent.get(currentIndex).isEmpty()) {
                        contentLabel.setText(backContent.get(currentIndex));
                        flashcardPanel.setBackground(new Color(61, 80, 163));
                    }
                }
            } else {
                contentLabel.setText(frontContent.get(currentIndex));
                flashcardPanel.setBackground(new Color(52, 69, 140));
            }
        }
    }

    /**
     * Displays the previous flashcard by updating the current index
     * to the previous position in the flashcard list.
     * <p>
     * The index wraps around to the last flashcard if currently at the first one.
     * Then updates the displayed content accordingly.
     *
     * @param e the ActionEvent triggered by clicking the "Back" button
     * @author Sara Sheikho
     */
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
     * @author Sara Sheikho
     * @author Salman Warsame
     */
    private void showNextFlashcard(ActionEvent e) {
        currentIndex = (currentIndex + 1) % frontContent.size();
        updateCardDisplay();
    }
}
