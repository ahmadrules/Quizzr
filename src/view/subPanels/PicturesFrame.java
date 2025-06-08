package view.subPanels;

import view.main.CenterPanels.CenterAccountPanel;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A JFrame that allows the user to select a profile picture from a set of predefined images.
 * Displays several pictures in a grid layout, lets the user select one,
 * and confirms the selection with an OK button.
 * Updates the profile picture in the associated CenterAccountPanel and MainFrame.
 *
 * @author Sara Sheikho
 */
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
    private JLabel selectedPic;
    private JLabel[] picLabels;
    private JButton okButton;
    private String[] picPaths;

    /**
     * Constructs a PicturesFrame tied to the given MainFrame and CenterAccountPanel.
     * Sets up the UI components including the picture selection grid and OK button.
     * The frame is centered and made visible immediately.
     *
     * @param mainFrame The main application frame, used for context and image path retrieval.
     * @param accountPanel The CenterAccountPanel to update the selected profile picture.
     * @author Sara Sheikho
     * @author Ahmad Maarouf
     */
    public PicturesFrame(MainFrame mainFrame, CenterAccountPanel accountPanel) {
        this.mainFrame = mainFrame;
        this.accountPanel = accountPanel;
        setSize(550, 500);
        setLocationRelativeTo(null);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setUpPanels();
        add(mainPanel);
        addListeners();

        ImageIcon icon = new ImageIcon(getClass().getResource("/view/pics/Quizzr-logo.png"));
        setIconImage(icon.getImage());

        mainPanel.revalidate();
        mainPanel.repaint();
        setVisible(true);
    }

    /**
     * Sets up the main panels in the frame, including an information label,
     * the pictures panel with selectable images, and the OK button panel.
     * @author Sara Sheikho
     */
    public void setUpPanels() {
        JLabel infoLabel = new JLabel("Choose a picture");

        //Center
        setUpPicsPanel();

        okButton = new JButton("OK");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okButton);

        mainPanel.add(infoLabel, BorderLayout.NORTH);
        mainPanel.add(picsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Sets up the pictures panel with a grid layout containing selectable picture labels.
     * Loads the image paths from resources and adds the images as clickable labels.
     * @author Sara Sheikho
     */
    public void setUpPicsPanel() {
        picsPanel = new JPanel();
        picsPanel.setLayout(new GridLayout(2, 2));
        picLabels = new JLabel[6];

        picPaths = new String[]{getClass().getResource("/view/pics/pic1.jpg").toString(),
                getClass().getResource("/view/pics/pic2.jpg").toString(),
                getClass().getResource("/view/pics/pic3.jpg").toString(),
                getClass().getResource("/view/pics/pic4.jpg").toString(),
                getClass().getResource("/view/pics/pic5.jpg").toString(),
                getClass().getResource("/view/pics/pic6.jpg").toString()
        };

        pic1 = new JLabel("<html><img src='" + picPaths[0] + "' width='160' height='190'></html>");
        picLabels[0] = pic1;
        pic2 = new JLabel("<html><img src='" + picPaths[1] + "' width='160' height='190'></html>");
        picLabels[1] = pic2;
        pic3 = new JLabel("<html><img src='" + picPaths[2] + "' width='160' height='190'></html>");
        picLabels[2] = pic3;
        pic4 = new JLabel("<html><img src='" + picPaths[3] + "' width='160' height='190'></html>");
        picLabels[3] = pic4;
        pic5 = new JLabel("<html><img src='" + picPaths[4] + "' width='160' height='190'></html>");
        picLabels[4] = pic5;
        pic6 = new JLabel("<html><img src='" + picPaths[5] + "' width='160' height='190'></html>");
        picLabels[5] = pic6;

        for (int i = 0; i < picLabels.length; i++) {
            picLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            picLabels[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        picsPanel.add(pic1);
        picsPanel.add(pic2);
        picsPanel.add(pic3);
        picsPanel.add(pic4);
        picsPanel.add(pic5);
        picsPanel.add(pic6);

    }

    /**
     * Adds action listeners to the OK button and mouse listeners to each picture label.
     * The OK button confirms the selection and updates the profile picture.
     * Clicking a picture label highlights the selection with a blue border.
     * @author Sara Sheikho
     */
    public void addListeners() {
        okButton.addActionListener(n -> {
            accountPanel.profilePictureSelected(getPicPath());
            this.dispose();
        });

        final JLabel[] chosenLabel = {null};
        for (int i = 0; i < picLabels.length; i++) {
            picLabels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedPic = (JLabel) e.getSource();
                    if (chosenLabel[0] != null) {
                        chosenLabel[0].setBorder(null);
                    }
                    selectedPic.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                    chosenLabel[0] = selectedPic;
                }
            });
        }
    }

    /**
     * Returns the file path of the currently selected picture.
     * If no picture is selected, returns an empty string.
     *
     * @return the String path of the selected picture image.
     * @author Sara Sheikho
     */
    public String getPicPath() {
        String path = "";
        for (int i = 0; i < picLabels.length; i++) {
            if (selectedPic == picLabels[i]) {
                path = picPaths[i];
            }
        }
        return path;
    }
}
