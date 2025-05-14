package view.main;

import view.main.CenterPanels.CenterAccountPanel;
import view.main.CenterPanels.CenterModulePanel;
import view.main.CenterPanels.TopCenterPanel;

import javax.swing.*;
import java.awt.*;

/**
 * This class is a container for all different panels that may be displayed in the center of the
 * main window.
 * @author Ahmad Maarouf
 */
public class CenterPanel extends JPanel {
    private MainFrame mainFrame;
    private TopCenterPanel topPanel;
    private CenterModulePanel centerModulePanel;
    private CenterAccountPanel centerAccountPanel;
    private boolean isAdmin;

    public CenterPanel(MainFrame mainFrame, boolean isAdmin) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        topPanel = new TopCenterPanel();
        centerModulePanel = new CenterModulePanel(mainFrame, isAdmin);
        centerAccountPanel = new CenterAccountPanel(mainFrame);
        this.isAdmin = isAdmin;

        //Default view is module tab
        setModuleLayout();
    }

    /**
     * This method is called by RightPanel when the "Modules" tab is chosen.
     * It sets the panel in the middle to CenterModulePanel
     * Also changes topPanel accordingly.
     * @author Ahmad Maarouf
     */
    public void setModuleLayout() {
        removeAll();
        topPanel.setModuleTopLabel();
        add(topPanel, BorderLayout.NORTH);
        add(centerModulePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * This method is called by RightPanel when the "Account" tab is chosen.
     * It sets the panel in the middle to CenterAccountPanel
     * Also changes topPanel accordingly.
     * @author Ahmad Maarouf
     */
    public void setAccountLayout() {
        removeAll();
        topPanel.setAccountTopLabel();
        add(topPanel, BorderLayout.NORTH);
        add(centerAccountPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Returns the CenterModulePanel used for the session.
     * Used by LeftPanel to change the list displayed according to which course is selected.
     * @return panel used in the center to display modules
     */
    public CenterModulePanel getModulePanel() {
        return centerModulePanel;
    }

}
