package view.main.CenterPanels;

import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;

public class CenterAccountPanel extends JPanel {
    private MainFrame mainFrame;
    private JPanel accountInformationPanel;
    private String[] currentUserInfo;
    private JButton editNameButton;
    private JButton editEmailButton;
    private JLabel nameLabel;
    private JLabel emailLabel;

    public CenterAccountPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        getCurrentUserInfo();
        createAccountInformationPanel();
        addActionListeners();
        setLayout();
        setVisible(true);
    }

    public void setLayout() {
        setLayout(new BorderLayout());
        JLabel topLabel = new JLabel("Account information", SwingConstants.CENTER);
        add(topLabel, BorderLayout.NORTH);
        add(accountInformationPanel, BorderLayout.CENTER);
    }

    public void createAccountInformationPanel() {
        accountInformationPanel = new JPanel();
        accountInformationPanel.setLayout(new BoxLayout(accountInformationPanel, BoxLayout.Y_AXIS));
        accountInformationPanel.setBackground(new Color(230, 230, 230));
        accountInformationPanel.setBorder(BorderFactory.createLoweredBevelBorder());

        nameLabel = new JLabel("Username: " + currentUserInfo[0]);
        nameLabel.setAlignmentX(LEFT_ALIGNMENT);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        emailLabel = new JLabel("Email: " + currentUserInfo[1]);
        emailLabel.setAlignmentX(LEFT_ALIGNMENT);
        emailLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        editNameButton = new JButton("Edit username");
        editNameButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        editEmailButton = new JButton("Edit email");
        editEmailButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        accountInformationPanel.add(nameLabel);
        accountInformationPanel.add(editNameButton);
        accountInformationPanel.add(emailLabel);
        accountInformationPanel.add(editEmailButton);
    }

    public void getCurrentUserInfo() {
        this.currentUserInfo = mainFrame.getCurrentUserInfo();
    }

    public void updateUserInfo() {
        getCurrentUserInfo();
        nameLabel.setText("Username: " + currentUserInfo[0]);
        emailLabel.setText("Email: " + currentUserInfo[1]);
    }

    public void changeUsername() {
        String newUsername = JOptionPane.showInputDialog("Please enter new username", currentUserInfo[0]);
        if (newUsername != null) {
            if (newUsername.equals(currentUserInfo[0])) {
                //Do nothing if username not changed
            }
            else {
                mainFrame.setNewUsername(newUsername);
                updateUserInfo();
            }
        }
    }

    public void changeEmail() {
        String newEmail = JOptionPane.showInputDialog("Please enter new email", currentUserInfo[1]);
        if (mainFrame.isEmailValid(newEmail)) {
            if (newEmail.equals(currentUserInfo[1])) {
                //Do nothing if email not changed
            }
            else {
                mainFrame.setNewEmail(newEmail);
                updateUserInfo();
            }
        }
        else {
            JOptionPane.showMessageDialog(mainFrame, "Please enter a valid email");
        }
    }

    public void addActionListeners() {
        editNameButton.addActionListener(e -> {
            changeUsername();
        });

        editEmailButton.addActionListener(e -> {
            changeEmail();
        });
    }
}
