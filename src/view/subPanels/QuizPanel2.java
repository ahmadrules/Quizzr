package view.subPanels;

import model.Module;
import model.Question;
import model.Quiz;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class QuizPanel2 extends JFrame {
    private Module currentModule;
    private String selectedModule;
    private String selectedCourse;
    private String selectedProgram;
    private MainFrame mainFrame;
    private Quiz currentQuiz;

    public QuizPanel2(String selectedProgram, String selectedCourse, String selectedModule, MainFrame mainFrame) {
        this.selectedProgram = selectedProgram;
        this.selectedCourse = selectedCourse;
        this.selectedModule = selectedModule;
        this.mainFrame = mainFrame;
    }

    public void setLayout() {
        setLayout(new BorderLayout());
        setSize(400, 400);

        JLabel quizLabel = new JLabel("Available quiz");
        add(quizLabel, BorderLayout.NORTH);
    }

    public void fetchModule() {
        currentModule = mainFrame.getCurrentModule(selectedProgram, selectedCourse, selectedModule);
    }

    public void generateRandomQuiz() {
        currentQuiz = new Quiz("True or False");
        ArrayList<Question> questions = new ArrayList<Question>();
        questions = currentModule.generateTrueOrFalseQuiz("C:\\Users\\ahmad\\Documents\\GitHub\\Quizzr\\src\\model\\files\\course2\\module1\\trueFalse_questions.txt",
                10);

        for (Question question : questions) {
            System.out.println(question);
        }
    }
}