package view.subPanels.Quiz;

import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JPanel{
    private JPanel pnlPointsPanel;
    private JPanel pnlStatisticsPanel;
    private JLabel lblUserPoints;
    private JLabel lblTotalPoints;
    private JLabel lblStatistics;
    private JLabel lblImageIcon;       //For future use
    private BorderLayout layout;

    public ResultPanel(int userPoints, int totalPoints, double statistics){
        layout = new BorderLayout();
        setLayout(layout);
        createPanels();
        setUpPointsPanel(userPoints, totalPoints);
        setUpStatisticsPanel(statistics);
        add(pnlPointsPanel, layout.NORTH);
        add(pnlStatisticsPanel, layout.SOUTH);
    }

    public void createPanels(){
        pnlPointsPanel = new JPanel();
        pnlPointsPanel.setSize(getWidth(), getHeight()*40/100);
        pnlStatisticsPanel = new JPanel();
        pnlStatisticsPanel.setSize(getWidth(), getHeight()*60/100);

    }

    public void setUpPointsPanel(int userPoints, int totalPoints){
        GridBagLayout gbLayout = new GridBagLayout();
        pnlPointsPanel.setLayout(gbLayout);
        lblUserPoints = new JLabel("Your points:   " + userPoints);
        lblTotalPoints = new JLabel("Total points:   " + totalPoints);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.WEST;
        pnlPointsPanel.add(lblUserPoints, c);

        c.gridx = 0;
        c.gridy = 1;
        pnlPointsPanel.add(lblTotalPoints, c);
        pnlPointsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void setUpStatisticsPanel(double statistics){
        GridBagLayout gbLayout = new GridBagLayout();
        pnlStatisticsPanel.setLayout(gbLayout);
        String formattedStatistics = String.format("%.2f", statistics);
        lblStatistics = new JLabel("You have got " + formattedStatistics + " % correct answers!");

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(20, 10, 10, 10);
        c.anchor = GridBagConstraints.WEST;
        pnlStatisticsPanel.add(lblStatistics, c);
        pnlStatisticsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    }
}
