package view.main.CenterPanels;

import model.Hasher;
import view.main.MainFrame;
import view.subPanels.PicturesFrame;

import javax.swing.*;
import java.awt.*;

/**
 * This class is responsible for displaying account information in centerPanel
 * for the currently logged in account.
 * It is also responsible for giving options of changing email, username and password of the current user.
 * @author Ahmad Maarouf
 */
public class CenterAccountPanel extends JPanel {
    private String[] currentUserInfo;
    private MainFrame mainFrame;

    private JPanel accountInformationPanel;
    private JPanel emptyPanel;
    private JPanel mainPanel;
    private JPanel profilePicPanel;

    private JButton editNameButton;
    private JButton editEmailButton;
    private JButton editPasswordButton;
    private JButton editProfilePic;

    private JLabel emailLabel;
    private JLabel nameLabel;
    private JLabel passwordLabel;
    private JLabel profilePicLabel;

    public CenterAccountPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        getCurrentUserInfo();
        setLayout();
        addActionListeners();
        setVisible(true);
    }

    /**
     * Sets up the layout of the panel.
     * @author Ahmad Maarouf
     */
    public void setLayout() {
        setLayout(new BorderLayout());
        JLabel topLabel = new JLabel("Account information", SwingConstants.CENTER);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setUpMainPanel();
        add(topLabel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(emptyPanel, BorderLayout.SOUTH);
    }

    public void setUpMainPanel(){
        accountInformationPanel = new JPanel();
        createAccountInformationPanel();
        profilePicPanel = new JPanel();
        setUpProfilePicPanel();
        mainPanel.add(profilePicPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(accountInformationPanel);
    }

    public void setUpProfilePicPanel(){
        profilePicPanel.setLayout(new BoxLayout(profilePicPanel, BoxLayout.Y_AXIS));
        profilePicPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        profilePicPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        editProfilePic = new JButton("Change Picture");
        profilePicLabel = new JLabel("Test");

        editProfilePic.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //profilePicPanel.add(Box.createVerticalStrut(30));
        profilePicPanel.add(profilePicLabel);
        profilePicPanel.add(Box.createVerticalStrut(100));
        profilePicPanel.add(editProfilePic);
        profilePicPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    }

    /**
     * Creates the panel displaying the information and all related
     * components required.
     * @author Ahmad Maarouf
     */
    public void createAccountInformationPanel() {
        //Panel to add space to bottom
        emptyPanel = new JPanel();
        accountInformationPanel.setLayout(new BoxLayout(accountInformationPanel, BoxLayout.X_AXIS));

        createAccountPanel();
        createAccountButtonsPanel();
        accountInformationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void createAccountPanel(){

        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.Y_AXIS));

        // Labels
        nameLabel = new JLabel("Username: " + currentUserInfo[0]);
        nameLabel.setAlignmentX(LEFT_ALIGNMENT);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        emailLabel = new JLabel("Email: " + currentUserInfo[1]);
        emailLabel.setAlignmentX(LEFT_ALIGNMENT);
        emailLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        passwordLabel = new JLabel("Password: ●●●●●●●");
        passwordLabel.setAlignmentX(LEFT_ALIGNMENT);
        passwordLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        accountPanel.add(nameLabel);
        accountPanel.add(Box.createVerticalStrut(10));
        accountPanel.add(emailLabel);
        accountPanel.add(Box.createVerticalStrut(10));
        accountPanel.add(passwordLabel);

        accountInformationPanel.add(Box.createHorizontalStrut(5));
        accountInformationPanel.add(accountPanel);
    }

    public void createAccountButtonsPanel(){
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        //Buttons
        editNameButton = new JButton("Edit Username");
        editEmailButton = new JButton("Edit E-mail");
        editPasswordButton = new JButton("Change Password");
        Dimension buttonSize = new Dimension(140, 30);

        editNameButton.setPreferredSize(buttonSize);
        editNameButton.setMaximumSize(buttonSize);
        editNameButton.setMinimumSize(buttonSize);

        editEmailButton.setPreferredSize(buttonSize);
        editEmailButton.setMaximumSize(buttonSize);
        editEmailButton.setMinimumSize(buttonSize);

        editPasswordButton.setPreferredSize(buttonSize);
        editPasswordButton.setMaximumSize(buttonSize);
        editPasswordButton.setMinimumSize(buttonSize);

        buttonsPanel.add(Box.createVerticalStrut(20));
        buttonsPanel.add(editNameButton);
        buttonsPanel.add(Box.createVerticalStrut(20));
        buttonsPanel.add(editEmailButton);
        buttonsPanel.add(Box.createVerticalStrut(20));
        buttonsPanel.add(editPasswordButton);

        accountInformationPanel.add(Box.createHorizontalStrut(5));
        accountInformationPanel.add(buttonsPanel);
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
        String hashedPassword= Hasher.hash(currentPassword);

        if (hashedPassword.equals(currentUserInfo[2])) {
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
                    isFinished = true;
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
    public void changeProfilePicture(){
        PicturesFrame picturesFrame = new PicturesFrame(mainFrame, this);

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

        editProfilePic.addActionListener(e->{
            changeProfilePicture();
        });
    }
}
