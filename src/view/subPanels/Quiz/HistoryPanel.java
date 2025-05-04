package view.subPanels.Quiz;

import javax.swing.*;
import java.awt.*;

public class HistoryPanel extends JPanel {

    public HistoryPanel() {
        setLayout();
    }

    public void setLayout () {
        setLayout(new BorderLayout());
        JLabel northLabel = new JLabel("History of completed quizzes");
        northLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(northLabel, BorderLayout.NORTH);
    }
}
