package view.subPanels.Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ResultPanel extends JPanel {
    private JPanel pnlNorth;
    private JPanel pnlSouth;
    private JPanel pnlCenter;
    private JPanel pnlResultInfo;
    private JLabel lblUserPoints;
    private JLabel lblTotalPoints;
    private JLabel lblStatistics;
    private JLabel lblImageIcon;
    private BorderLayout layout;
    private QuestionFrame questionFrame;

    /**
     * Constructs the ResultPanel to display the user's quiz results.
     *
     * @param userPoints the points scored by the user
     * @param totalPoints the total possible points
     * @param statistics the percentage of correct answers
     * @param questionFrame the parent frame containing this panel
     * @author Sara Sheikho
     * @author Ahmad Maarouf
     */
    public ResultPanel(int userPoints, int totalPoints, double statistics, QuestionFrame questionFrame) {
        layout = new BorderLayout();
        this.questionFrame = questionFrame;
        setLayout(layout);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        createPanels();
        setUpCenterPanel(userPoints, totalPoints, statistics);
        setUpSouthPanel();
        add(pnlNorth, layout.NORTH);
        add(pnlCenter, layout.CENTER);
        add(pnlSouth, layout.SOUTH);
    }

    /**
     * Initializes the north, center, and south panels with appropriate sizes,
     * colors, and borders for the result display.
     * @author Sara Sheikho
     */
    public void createPanels() {
        pnlNorth = new JPanel();
        pnlNorth.setSize(getWidth(), getHeight() * 30 / 100);

        pnlCenter = new JPanel();
        pnlCenter.setSize(getWidth(), getHeight() * 40 / 100);
        pnlCenter.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pnlCenter.setBackground(new Color(250, 221, 92));

        pnlSouth = new JPanel();
        pnlSouth.setSize(getWidth(), getHeight() * 30 / 100);
    }

    /**
     * Sets up the center panel layout to show the user's results and a congratulatory image.
     *
     * @param userPoints the points scored by the user
     * @param totalPoints the total possible points
     * @param statistics the percentage of correct answers
     * @author Sara Sheikho
     */
    public void setUpCenterPanel(int userPoints, int totalPoints, double statistics) {
        GridBagLayout gbLayout = new GridBagLayout();
        pnlCenter.setLayout(gbLayout);

        //Creates image icon
        String path = getClass().getResource("/view/pics/goodJob.gif").toString();
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

    /**
     * Configures the panel displaying the user's score details.
     *
     * @param userPoints the points scored by the user
     * @param totalPoints the total possible points
     * @param statistics the percentage of correct answers
     * @author Sara Sheikho
     * @author Ahmad Maarouf
     */
    public void setUpResultInfoPanel(int userPoints, int totalPoints, double statistics) {

        //Points
        GridBagLayout gbLayout = new GridBagLayout();
        pnlResultInfo.setLayout(gbLayout);
        lblUserPoints = new JLabel("Your points:   " + userPoints);
        lblUserPoints.setFont(new Font("Montserrat", Font.BOLD, 16));
        lblUserPoints.setForeground(Color.WHITE);

        lblTotalPoints = new JLabel("Total points:   " + totalPoints);
        lblTotalPoints.setFont(new Font("Montserrat", Font.BOLD, 16));
        lblTotalPoints.setForeground(Color.WHITE);
        lblStatistics = new JLabel("You have got " + String.format("%.1f", statistics) + "% right answers");
        lblStatistics.setFont(new Font("Montserrat", Font.BOLD, 16));
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

    public void setUpSouthPanel() {
        JPanel buttonPanel = new JPanel();
        JButton resultButton = new JButton("Show result");
        buttonPanel.add(resultButton);
        pnlSouth.add(buttonPanel);

        resultButton.addActionListener(e -> {
            questionFrame.viewResult();
        });
    }
}
