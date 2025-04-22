package view.main.CenterPanels;

import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * This class is responsible for displaying account information in centerPanel
 * for the currently logged in account.
 * It is also responsible for giving options of changing email, username and password of the current user.
 * @author Ahmad Maarouf
 */
public class CenterAccountPanel extends JPanel {
    private MainFrame mainFrame;
    private JPanel accountInformationPanel;
    private String[] currentUserInfo;
    private JButton editNameButton;
    private JButton editEmailButton;
    private JButton editPasswordButton;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JPanel emptyPanel;

    public CenterAccountPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        getCurrentUserInfo();
        createAccountInformationPanel();
        addActionListeners();
        setLayout();
        setVisible(true);
    }

    /**
     * Sets up the layout of the panel.
     * @author Ahmad Maarouf
     */
    public void setLayout() {
        setLayout(new BorderLayout());
        JLabel topLabel = new JLabel("Account information", SwingConstants.CENTER);
        add(topLabel, BorderLayout.NORTH);
        add(accountInformationPanel, BorderLayout.CENTER);
        add(emptyPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the panel displaying the information and all related
     * components required.
     * @author Ahmad Maarouf
     */
    public void createAccountInformationPanel() {
        //Panel to add space to bottom
        emptyPanel = new JPanel();
        emptyPanel.setBackground(new Color(238, 238, 238));

        //Border displaying information
        accountInformationPanel = new JPanel();
        accountInformationPanel.setLayout(new BoxLayout(accountInformationPanel, BoxLayout.Y_AXIS));
        accountInformationPanel.setBackground(new Color(255, 255, 255));
        accountInformationPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));


        nameLabel = new JLabel("Username: " + currentUserInfo[0]);
        nameLabel.setAlignmentX(LEFT_ALIGNMENT);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        emailLabel = new JLabel("Email: " + currentUserInfo[1]);
        emailLabel.setAlignmentX(LEFT_ALIGNMENT);
        emailLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        passwordLabel = new JLabel("Password: ●●●●●●●");
        passwordLabel.setAlignmentX(LEFT_ALIGNMENT);
        passwordLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        editNameButton = new JButton("Edit username");
        editNameButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        editEmailButton = new JButton("Edit email");
        editEmailButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        editPasswordButton = new JButton("Change password");
        editPasswordButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        accountInformationPanel.add(nameLabel);
        accountInformationPanel.add(editNameButton);
        accountInformationPanel.add(emailLabel);
        accountInformationPanel.add(editEmailButton);
        accountInformationPanel.add(passwordLabel);
        accountInformationPanel.add(editPasswordButton);
    }

    /**
     * Fetches the current users information from the mainFrame
     * which calls on controller.
     * @author Ahmad Maarouf
     */
    public void getCurrentUserInfo() {
        this.currentUserInfo = mainFrame.getCurrentUserInfo();
    }

    /**
     * Updates the current users' information.
     * Called when email, username or password is edited.
     * @author Ahmad Maarouf
     */
    public void updateUserInfo() {
        getCurrentUserInfo();
        nameLabel.setText("Username: " + currentUserInfo[0]);
        emailLabel.setText("Email: " + currentUserInfo[1]);
    }

    /**
     * Changes the username according to what the user inputs.
     * Also changes the username stored in a data file.
     * @author Ahmad Maarouf
     */
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

    /**
     * Changes the email according to what the user inputs.
     * Also changes the email stored in a data file.
     * New email must be a valid email.
     * @author Ahmad Maarouf
     */
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

    /**
     * Changes the password according to what the user inputs.
     * Also changes the password stored in a data file.
     * @author Ahmad Maarouf
     */
    public void changePassword() {
        String currentPassword = JOptionPane.showInputDialog(mainFrame, "Please enter your current password");

        if (currentPassword.equals(currentUserInfo[2])) {
            JTextField newPassword = new JPasswordField(10);
            JTextField newConfirmPassword = new JPasswordField(10);
            Object[] options = {"New password:", newPassword, "Confirm new password: ", newConfirmPassword};

            boolean isFinished = false;

            while(!isFinished) {
                int choice = JOptionPane.showConfirmDialog(null, options, "Change password", JOptionPane.OK_CANCEL_OPTION);

                if (choice == JOptionPane.OK_OPTION && newPassword.getText().equals(newConfirmPassword.getText())) {
                    mainFrame.setNewPassword(newPassword.getText());
                    isFinished = true;
                    JOptionPane.showMessageDialog(mainFrame, "Password changed successfully");
                    updateUserInfo();
                } else if (choice == JOptionPane.CANCEL_OPTION) {
                    //If cancel button clicked do nothing
                    isFinished = true;
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Passwords do not match. Try again.");
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(mainFrame, "Incorrect password");
        }
    }

    /**
     * Adds action listeners to the buttons.
     * @author Ahmad Maarouf
     */
    public void addActionListeners() {
        editNameButton.addActionListener(e -> {
            changeUsername();
        });

        editEmailButton.addActionListener(e -> {
            changeEmail();
        });

        editPasswordButton.addActionListener(e -> {
            changePassword();
        });
    }
}
