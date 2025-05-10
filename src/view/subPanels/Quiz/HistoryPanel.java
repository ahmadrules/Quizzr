package view.subPanels.Quiz;

import javax.swing.*;
import java.awt.*;

public class HistoryPanel extends JPanel {
    private DefaultListModel<String> quizModel;

    public HistoryPanel() {
        setLayout();
    }

    public void setLayout () {
        setLayout(new BorderLayout());
        JLabel northLabel = new JLabel("History of completed quizzes");
        northLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(northLabel, BorderLayout.NORTH);
    }

    public void createLists() {
        quizModel = new DefaultListModel<>();
        JList quizList = new JList(quizModel);
        add(quizList, BorderLayout.CENTER);
    }
}
