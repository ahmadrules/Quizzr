package view.subPanels;

import controller.Controller;
import model.Quiz;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class QuizPanel extends JPanel {    
    private Controller controller;
    private String moduleName;
    private JList<String> quizList;
    private DefaultListModel<String> quizListModel;


    public QuizPanel(String moduleName, Controller controller) {
        this.moduleName = moduleName;
        this.controller = controller;

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Quizzes for module: " + moduleName, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        // === Lista med quiz-namn ===
        quizListModel = new DefaultListModel<>();
        quizList = new JList<>(quizListModel);
        quizList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(quizList);
        add(scrollPane, BorderLayout.CENTER);

        /*
        // === Hämta quizfrågor via controller ===
        ArrayList<Question> questions = controller.getQuestionsForModule(moduleName);
        for (int i = 0; i < questions.size(); i++) {
            quizListModel.addElement(questions.get(i).getQuestion());
}

        
    }

        // === Knappar längst ner ===
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton startButton = new JButton("Start Quiz");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // === Klick för att starta quiz ===
        startButton.addActionListener(e -> {
            String selectedQuizName = quizList.getSelectedValue();
            if (selectedQuizName != null) {
                controller.startQuizForModule(moduleName, selectedQuizName);
            }
        });

         */

        // TODO: Add, Edit och Delete-funktioner kan implementeras vid behov
    }
}


/**
    Metoder för controller:

public ArrayList<Quiz> getQuizzesForModule(String moduleName) {
    for (int i = 0; i < programList.size(); i++) {
        Program program = programList.get(i);
        List<Course> courses = program.getCourses();
        for (int j = 0; j < courses.size(); j++) {
            Course course = courses.get(j);
            List<Module> modules = course.getModules();
            for (int k = 0; k < modules.size(); k++) {
                Module module = modules.get(k);
                if (module.getName().equals(moduleName)) {
                    ArrayList<Quiz> quizzes = new ArrayList<>();
                    quizzes.add(module.getCurrentQuiz()); // Om fler quiz ska stödjas, ändra detta
                    return quizzes;
                }
            }
        }
    }
    return new ArrayList<>();
}


public void startQuizForModule(String moduleName, String quizName) {
    // Här kan du visa ny vy för att genomföra quiz
    System.out.println("Starting quiz: " + quizName + " in module: " + moduleName);
}

*/


