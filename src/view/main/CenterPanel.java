package view.main;

import view.main.CenterPanels.CenterModulePanel;
import view.main.CenterPanels.TopCenterPanel;

import javax.swing.*;
import java.awt.*;

public class CenterPanel extends JPanel {
    MainFrame mainFrame;
    TopCenterPanel topPanel;
    CenterModulePanel centerModulePanel;

    public CenterPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(new BorderLayout());
        topPanel = new TopCenterPanel();
        centerModulePanel = new CenterModulePanel(mainFrame);

        //Default view is module tab
        setModuleLayout();
    }

    public void setModuleLayout() {
        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerModulePanel, BorderLayout.CENTER);
    }

    public CenterModulePanel getModulePanel() {
        return centerModulePanel;
    }
}
