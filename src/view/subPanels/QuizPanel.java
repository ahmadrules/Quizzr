package view.subPanels;

import model.Module;
import model.Question;
import model.Quiz;

import javax.swing.*;
import java.util.List;

public class QuizPanel {
    private JFrame quizFrame;
    private Module selectedModule;

    public QuizPanel(Module selectedModule) {
        this.selectedModule = selectedModule;
        createFrame();
    }

    public void createFrame() {
        quizFrame = new JFrame("Quiz for " + selectedModule.getName());
        quizFrame.setSize(400, 300);
        quizFrame.setLocationRelativeTo(null);

        // Laddar quiz från fil genom modulen
        selectedModule.loadQuizFromFile();
        /*
        Quiz quiz = selectedModule.currentQuiz;

        if (quiz == null) {
            quizFrame.add(new JLabel("No quiz found for this module.", SwingConstants.CENTER));
        } else {
            // Hämtar frågorna från quizet det (getQuestions() läggas till i quiz?)
            List<Question> questions = quiz.getQuestions();

            // Skapar array med frågetexter
            String[] questionTexts = new String[questions.size()];
            for (int i = 0; i < questions.size(); i++) {
                questionTexts[i] = questions.get(i).getQuestion();
            }

            // Visar frågorna i en JList
            JList<String> quizList = new JList<>(questionTexts);
            quizList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(quizList);

            quizFrame.add(scrollPane);
        }

        quizFrame.setVisible(true);

         */
    }
}
