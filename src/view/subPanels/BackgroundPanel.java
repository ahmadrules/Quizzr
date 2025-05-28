package view.subPanels;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {

    private Image background;
    public BackgroundPanel(String imagePath) {
        background = new ImageIcon(imagePath).getImage();
        setLayout(new BorderLayout());
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0,getWidth(),getHeight(), this);

    }

}
