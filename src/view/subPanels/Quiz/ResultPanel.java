package view.subPanels.Quiz;

import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JPanel{
    private JPanel pnlNorth;
    private JPanel pnlSouth;
    private JPanel pnlCenter;
    private JPanel pnlResultInfo;
    private JLabel lblUserPoints;
    private JLabel lblTotalPoints;
    private JLabel lblStatistics;
    private JLabel lblImageIcon;
    private BorderLayout layout;

    public ResultPanel(int userPoints, int totalPoints, double statistics){
        layout = new BorderLayout();
        setLayout(layout);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        createPanels();
        setUpCenterPanel(userPoints, totalPoints, statistics);
        add(pnlNorth, layout.NORTH);
        add(pnlCenter, layout.CENTER);
        add(pnlSouth, layout.SOUTH);
    }

    public void createPanels(){
        pnlNorth = new JPanel();
        pnlNorth.setSize(getWidth(), getHeight()*30/100);


        pnlCenter = new JPanel();
        pnlCenter.setSize(getWidth(), getHeight()*40/100);
        pnlCenter.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pnlCenter.setBackground(new Color(250, 221, 92));

        pnlSouth = new JPanel();
        pnlSouth.setSize(getWidth(), getHeight()*30/100);

    }

    public void setUpCenterPanel(int userPoints, int totalPoints, double statistics){
        GridBagLayout gbLayout = new GridBagLayout();
        pnlCenter.setLayout(gbLayout);

        //Creates image icon
        String path = getClass().getResource("/view/picsGIF/goodJob.gif").toString();
        lblImageIcon = new JLabel("<html><img src='" + path + "' width='300' height='300'></html>");
        lblImageIcon.setHorizontalAlignment(SwingConstants.CENTER);

        pnlResultInfo = new JPanel();
        pnlResultInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pnlResultInfo.setBackground(new Color(50, 87, 173));


        GridBagConstraints c = new GridBagConstraints();

        setUpResultInfoPanel(userPoints, totalPoints, statistics);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(50, 10, 50, 20);
        c.anchor = GridBagConstraints.CENTER;
        pnlCenter.add(pnlResultInfo, c);

        c.gridx = 1;
        c.gridy = 0;
        pnlCenter.add(lblImageIcon, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(50, 10, 50, 10);

    }

    public void setUpResultInfoPanel(int userPoints, int totalPoints, double statistics){

        //Points
        GridBagLayout gbLayout = new GridBagLayout();
        pnlResultInfo.setLayout(gbLayout);
        lblUserPoints = new JLabel("Your points:   " + userPoints);
        lblUserPoints.setFont(new Font("Arial", Font.BOLD, 16));
        lblUserPoints.setForeground(Color.WHITE);

        lblTotalPoints = new JLabel("Total points:   " + totalPoints);
        lblTotalPoints.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotalPoints.setForeground(Color.WHITE);

        lblStatistics = new JLabel("You have got " + statistics + "% right answers");
        lblStatistics.setFont(new Font("Arial", Font.BOLD, 16));
        lblStatistics.setForeground(Color.WHITE);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 30, 10);
        c.anchor = GridBagConstraints.CENTER;
        pnlResultInfo.add(lblUserPoints, c);

        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(10, 10, 30, 10);
        pnlResultInfo.add(lblTotalPoints, c);

        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(10, 10, 30, 10);
        pnlResultInfo.add(lblStatistics, c);
    }
}
