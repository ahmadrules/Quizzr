package view.main.CenterPanels;

import javax.swing.*;
import java.awt.*;

public class TopCenterPanel extends JPanel {
    private JLabel topLabel;

    public TopCenterPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(230, 230, 230));
        topLabel = new JLabel();
        topLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        //Module is default page
        setModuleTopLabel();
        add(new JSeparator(), BorderLayout.SOUTH);
    }

    public void setModuleTopLabel() {
        topLabel.setText("Select a program from the left to start");
        add(topLabel, BorderLayout.WEST);
    }

    public void setAccountTopLabel() {
        remove(topLabel);
        topLabel.setText("View account information below");
        add(topLabel, BorderLayout.WEST);
    }
}
