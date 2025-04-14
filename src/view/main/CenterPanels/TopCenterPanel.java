package view.main.CenterPanels;

import javax.swing.*;
import java.awt.*;

public class TopCenterPanel extends JPanel {

    public TopCenterPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(230, 230, 230));
        JLabel topLabel = new JLabel("Select a program from the left", SwingConstants.LEFT);
        topLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        this.add(topLabel, BorderLayout.WEST);
        this.add(new JSeparator(), BorderLayout.SOUTH);
    }
}
