package view.subPanels;

import model.Course;
import model.FlashCard;
import model.Module;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI window that allows a user to create a new flashcard for a selected course and module.
 * Flashcard content is saved via MainFrame → Controller.
 *
 * @author Salman Warsame
 * @author Sara Sheikho
 */
public class CreateFlashcardFrame extends JFrame {
    private MainFrame mainFrame;

    private JTextField frontField;
    private JTextField backField;

    private JPanel mainPanel;
    private JPanel frontPanel;
    private JPanel backPanel;

    private JButton saveButton;

    private String selectedCourse;
    private String selectedModule;

    /**
     * Constructs the CreateFlashcardFrame to allow the user to create a new flashcard.
     * Initializes the frame size, title, layout, and components including text fields
     * for front and back content, and a save button.
     * The frame is centered on the screen and not resizable.
     *
     * @param selectedCourse the course name selected for which the flashcard is created
     * @param selectedModule the module name selected for which the flashcard is created
     * @param mainFrame the main application frame used for context and data access
     * @author Sara Sheikho
     * @author Salman Warsame
     */
    public CreateFlashcardFrame(String selectedCourse, String selectedModule, MainFrame mainFrame) {
        this.selectedCourse = selectedCourse;
        this.selectedModule = selectedModule;
        this.mainFrame = mainFrame;

        setTitle("Create New Flashcard");
        setSize(700, 490);
        setResizable(false);
        setLocationRelativeTo(null);

        createPanels();

        JLabel frontLabel = new JLabel("Front content: ");
        frontLabel.setForeground(Color.WHITE);
        frontLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        frontLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        frontLabel.setAlignmentX(SwingConstants.CENTER);

        JLabel backLabel = new JLabel("Back content: ");
        backLabel.setForeground(Color.WHITE);
        backLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        backLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        backLabel.setAlignmentX(SwingConstants.CENTER);

        createTextFields();

        frontPanel.add(frontLabel);
        backPanel.add(backLabel);

        createButton();

        JPanel frontWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        frontWrapper.setBackground(mainPanel.getBackground());
        frontWrapper.setMaximumSize(new Dimension(700, 50));
        frontWrapper.add(frontPanel);

        JPanel backWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        backWrapper.setBackground(mainPanel.getBackground());
        backWrapper.setMaximumSize(new Dimension(700, 50));
        backWrapper.add(backPanel);


        mainPanel.add(Box.createVerticalStrut(45));
        mainPanel.add(frontWrapper);
        mainPanel.add(Box.createVerticalStrut(2));
        mainPanel.add(frontField);
        mainPanel.add(Box.createVerticalStrut(55));
        mainPanel.add(backWrapper);
        mainPanel.add(Box.createVerticalStrut(2));
        mainPanel.add(backField);
        mainPanel.add(Box.createVerticalStrut(65));
        mainPanel.add(saveButton);
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapper.setBackground(new Color(255, 249, 163));
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        wrapper.add(mainPanel);

        add(wrapper);
        setVisible(true);
    }

    /**
     * Creates and configures the main, front, and back panels used
     * to layout the components inside the frame.
     * Sets background colors, borders, and layout managers.
     * @author Sara Sheikho
     */
    public void createPanels(){
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(255, 249, 163));

        frontPanel = new JPanel();
        frontPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        backPanel = new JPanel();
        backPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        frontPanel.setMaximumSize(new Dimension(160, 50));
        frontPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

        backPanel.setMaximumSize(new Dimension(165, 50));
        backPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

        frontPanel.setBackground(new Color(52, 69,140));
        backPanel.setBackground(new Color(52, 69,140));

    }

    /**
     * Creates and configures the text fields for entering the front and back
     * content of the flashcard.
     * Sets their preferred size and font style.
     * @author Sara Sheikho
     */
    public void createTextFields(){
        frontField = new JTextField();
        frontField.setPreferredSize(new Dimension(600, 60));
        frontField.setMaximumSize(new Dimension(600, 60));
        frontField.setMaximumSize(new Dimension(600, 60));
        frontField.setFont(new Font("Montserrat", Font.PLAIN, 18));

        backField = new JTextField();
        backField.setPreferredSize(new Dimension(600, 60));
        backField.setMaximumSize(new Dimension(600, 60));
        backField.setMaximumSize(new Dimension(600, 60));
        backField.setFont(new Font("Montserrat", Font.PLAIN, 18));
    }

    /**
     * Creates and configures the "Save Flashcard" button.
     * Sets its size, colors, font, and adds event listeners for
     * clicking and mouse hover effects.
     * @author Sara Sheikho
     * @author Lilas Beirakdar
     */
    public void createButton(){
        saveButton = new JButton("Save Flashcard");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setBackground(new Color(52, 69,140));
        saveButton.setForeground(Color.WHITE);
        saveButton.setPreferredSize(new Dimension(170, 50));
        saveButton.setMaximumSize(new Dimension(170, 50));
        saveButton.setMinimumSize(new Dimension(170, 50));
        saveButton.setFont(new Font("Montserrat", Font.BOLD, 16));


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFlashcard();
            }
        });
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(saveButton.isEnabled()) {
                    saveButton.setBackground(new Color(90, 140, 230));
                    saveButton.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(90, 140, 230).darker(), 2),
                            BorderFactory.createEmptyBorder(10, 20, 10, 20)
                    ));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                saveButton.setBackground(new Color(52, 69,140));
                saveButton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(52, 69,140), 2),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
        });
    }

    /**
     * Validates inputs and saves the flashcard to the current user's account.
     * Displays a success message if saved successfully.
     *
     * @author Salman Warsame
     */
    private void saveFlashcard() {
        String front = frontField.getText().trim();
        String back = backField.getText().trim();

        if (front.isEmpty() || back.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both front and back must be filled.");
            return;
        }

        String programName = mainFrame.getCurrentStudentProgramName();
        Course course = mainFrame.getCourseByName(programName, selectedCourse);
        Module module = mainFrame.getCurrentModule(programName, selectedCourse, selectedModule);

        FlashCard flashCard = new FlashCard(front, back, course, module);
        mainFrame.saveUserFlashCard(flashCard);

        JOptionPane.showMessageDialog(this, "Flashcard saved!");
        dispose();
    }
}



