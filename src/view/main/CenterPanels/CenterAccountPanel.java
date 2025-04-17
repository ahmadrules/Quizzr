package view.main.CenterPanels;

import view.main.MainFrame;
import view.main.RightPanel;

import javax.swing.*;
import java.awt.*;

public class CenterAccountPanel extends JPanel {
    private MainFrame mainFrame;
    private JPanel accountInformationPanel;

    public CenterAccountPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        createAccountInformationPanel();
        setLayout();
        setVisible(true);
    }

    public void setLayout() {
        setLayout(new BorderLayout());
        JLabel topLabel = new JLabel("Account information", SwingConstants.CENTER);
        add(topLabel, BorderLayout.NORTH);
        add(accountInformationPanel, BorderLayout.CENTER);
    }

    public void createAccountInformationPanel() {
        accountInformationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        accountInformationPanel.setBackground(new Color(255, 255, 255));
    }
}
