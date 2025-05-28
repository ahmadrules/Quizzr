package view.main.CenterPanels;

import javax.swing.*;
import java.awt.*;

/**
 * This class is responsible for giving simple instructions
 * to aid the user in using the program.
 * It is located at the top of centerPanel.
 *
 * @author Ahmad Maarouf
 */
public class TopCenterPanel extends JPanel {
    private JLabel topLabel;
    private boolean isAdmin;

    public TopCenterPanel(boolean isAdmin) {
        this.isAdmin = isAdmin;
        setLayout(new BorderLayout());
        setBackground(new Color(230, 230, 230));
        topLabel = new JLabel();
        topLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        //Module is default page
        setModuleTopLabel();

        add(new JSeparator(), BorderLayout.SOUTH);
    }

    /**
     * Sets the top label text according to the "Modules" tab.
     *
     * @author Ahmad Maarouf
     */
    public void setModuleTopLabel() {
        remove(topLabel);
        if (isAdmin) {
            topLabel.setText("Select a program from the left to start");
        } else {
            topLabel.setText("Select a course from the left to start");
        }
        add(topLabel, BorderLayout.WEST);
    }

    /**
     * Sets the top label text according to the "Account" tab.
     *
     * @author Ahmad Maarouf
     */
    public void setAccountTopLabel() {
        remove(topLabel);
        topLabel.setText("View account information below");
        add(topLabel, BorderLayout.WEST);
    }
}
