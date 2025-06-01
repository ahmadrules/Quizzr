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
 */
public class CreateFlashcardFrame extends JFrame {
    private JTextField frontField;
    private JTextField backField;
    private JButton saveButton;
    private MainFrame mainFrame;
    private String selectedCourse;
    private String selectedModule;

    /**
     * Initializes the flashcard creation UI.
     *
     * @param selectedCourse the course name the flashcard belongs to
     * @param selectedModule the module name the flashcard belongs to
     * @param mainFrame reference to the main application frame
     * @author Salman Warsame
     */
    public CreateFlashcardFrame(String selectedCourse, String selectedModule, MainFrame mainFrame) {
        this.selectedCourse = selectedCourse;
        this.selectedModule = selectedModule;
        this.mainFrame = mainFrame;

        setTitle("Create New Flashcard");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Front (Question):"));
        frontField = new JTextField();
        add(frontField);

        add(new JLabel("Back (Answer):"));
        backField = new JTextField();
        add(backField);

        saveButton = new JButton("Save Flashcard");
        add(new JLabel()); // spacer
        add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFlashcard();
            }
        });

        setVisible(true);
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



