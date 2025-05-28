package view.subPanels;

import view.main.CenterPanels.CenterAccountPanel;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;

public class PicturesFrame extends JFrame {
    private MainFrame mainFrame;
    private CenterAccountPanel accountPanel;
    private JPanel mainPanel;
    private JPanel picsPanel;
    private JLabel pic1;
    private JLabel pic2;
    private JLabel pic3;
    private JLabel pic4;
    private JLabel pic5;
    private JLabel pic6;

    public PicturesFrame(MainFrame mainFrame, CenterAccountPanel accountPanel){
        this.mainFrame = mainFrame;
        this.accountPanel = accountPanel;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setUpPanels();
        add(mainPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
        setVisible(true);
    }

    public void setUpPanels(){
        JLabel infoLabel = new JLabel("Choose a picture");

        //Center
        setUpPicsPanel();

        JButton okButton = new JButton("OK");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okButton);

        mainPanel.add(infoLabel, BorderLayout.NORTH);
        mainPanel.add(picsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setUpPicsPanel(){
        picsPanel = new JPanel();
        picsPanel.setLayout(new GridLayout(2, 2));

        String[] paths = {getClass().getResource("/view/pics/pic1.jpg").toString(),
                getClass().getResource("/view/pics/pic2.jpg").toString(),
                getClass().getResource("/view/pics/pic3.jpg").toString(),
                getClass().getResource("/view/pics/pic4.jpg").toString(),
                getClass().getResource("/view/pics/pic5.jpg").toString(),
                getClass().getResource("/view/pics/pic6.jpg").toString()
        };

        pic1 = new JLabel("<html><img src='" + paths[0] + "' width='170' height='200'></html>");
        pic2 = new JLabel("<html><img src='" + paths[1] + "' width='170' height='200'></html>");
        pic3 = new JLabel("<html><img src='" + paths[2] + "' width='170' height='200'></html>");
        pic4 = new JLabel("<html><img src='" + paths[3] + "' width='170' height='200'></html>");
        pic5 = new JLabel("<html><img src='" + paths[4] + "' width='170' height='200'></html>");
        pic6 = new JLabel("<html><img src='" + paths[5] + "' width='170' height='200'></html>");

        pic1.setHorizontalAlignment(SwingConstants.CENTER);
        pic2.setHorizontalAlignment(SwingConstants.CENTER);
        pic3.setHorizontalAlignment(SwingConstants.CENTER);
        pic4.setHorizontalAlignment(SwingConstants.CENTER);
        pic5.setHorizontalAlignment(SwingConstants.CENTER);
        pic6.setHorizontalAlignment(SwingConstants.CENTER);

        picsPanel.add(pic1);
        picsPanel.add(pic2);
        picsPanel.add(pic3);
        picsPanel.add(pic4);
        picsPanel.add(pic5);
        picsPanel.add(pic6);

    }
}
