package view.main;

import view.main.CenterPanels.CenterAccountPanel;
import view.main.CenterPanels.CenterModulePanel;
import view.main.CenterPanels.TopCenterPanel;

import javax.swing.*;
import java.awt.*;

public class CenterPanel extends JPanel {
    MainFrame mainFrame;
    TopCenterPanel topPanel;
    CenterModulePanel centerModulePanel;
    CenterAccountPanel centerAccountPanel;

    public CenterPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        topPanel = new TopCenterPanel();
        centerModulePanel = new CenterModulePanel(mainFrame);
        centerAccountPanel = new CenterAccountPanel(mainFrame);

        //Default view is module tab
        setModuleLayout();
    }

    public void setModuleLayout() {
        removeAll();
        revalidate();
        repaint();
        add(topPanel, BorderLayout.NORTH);
        add(centerModulePanel, BorderLayout.CENTER);
    }

    public void setAccountLayout() {
        removeAll();
        revalidate();
        repaint();
        add(centerAccountPanel, BorderLayout.CENTER);
    }

    public CenterModulePanel getModulePanel() {
        return centerModulePanel;
    }

}
