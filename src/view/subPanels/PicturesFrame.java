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
        mainPanel.setSize(getWidth(), getHeight());
        mainPanel.setLayout(new BorderLayout());
        setUpPanels();
        add(mainPanel);
        setVisible(true);
    }

    public void setUpPanels(){
        JLabel infoLabel = new JLabel("Choose a picture");

        //Center
        picsPanel = new JPanel();
        setUpPicsPanel();

        JButton okButton = new JButton("OK");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okButton);

        mainPanel.add(infoLabel, BorderLayout.NORTH);
        mainPanel.add(picsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setUpPicsPanel(){

    }
}
