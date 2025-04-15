package view.main.CenterPanels;

import view.main.MainFrame;
import view.main.RightPanel;

import javax.swing.*;
import java.awt.*;

public class CenterAccountPanel extends JPanel {
    private MainFrame mainFrame;

    public CenterAccountPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout();
        setVisible(true);
    }

    public void setLayout() {
        setLayout(new BorderLayout());
        JLabel topLabel = new JLabel("Account information", SwingConstants.CENTER);
        add(topLabel, BorderLayout.NORTH);
    }
}
