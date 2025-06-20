package view.main.CenterPanels;

import model.Hasher;
import view.main.MainFrame;
import view.subPanels.PicturesFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * This class is responsible for displaying account information in centerPanel
 * for the currently logged in account.
 * It is also responsible for giving options of changing email, username and password of the current user.
 *
 * @author Ahmad Maarouf
 */
public class CenterAccountPanel extends JPanel {
    private String currentProfilePicPath;
    private String[] currentUserInfo;
    private MainFrame mainFrame;

    private JPanel accountInformationPanel;
    private JPanel emptyPanel;
    private JPanel mainPanel;
    private JPanel profilePicPanel;

    private JButton editNameButton;
    private JButton editEmailButton;
    private JButton editPasswordButton;
    private JButton editProfilePicButton;

    private JLabel emailLabel;
    private JLabel nameLabel;
    private JLabel passwordLabel;
    private JLabel profilePicLabel;

    /**
     * Constructs the CenterAccountPanel and initializes its components.
     * Retrieves current user information, sets up the layout,
     * adds action listeners, and makes the panel visible.
     *
     * @param mainFrame the main application frame used for accessing user data and context
     * @author Ahmad Maarouf
     * @author Sara Sheikho
     */
    public CenterAccountPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        getCurrentUserInfo();
        setLayout();
        addActionListeners();
        setVisible(true);
    }

    /**
     * Sets up the layout of the panel including a top label,
     * a main panel wrapped in a scroll pane, and an empty panel at the bottom.
     * The main panel uses a vertical BoxLayout and is styled with background color and border.
     *
     * @author Ahmad Maarouf
     * @author Lilas Beirakdar
     * @author Sara Sheikho
     */
    public void setLayout() {
        setLayout(new BorderLayout());
        JLabel topLabel = new JLabel("Account information", SwingConstants.CENTER);
        topLabel.setFont(new Font("Montserrat", Font.PLAIN, 14));
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 249, 163));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setUpMainPanel();
        add(topLabel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);
        add(emptyPanel, BorderLayout.SOUTH);
    }

    /**
     * Configures the main panel by setting up the account information
     * and profile picture panels, arranging them vertically,
     * and adding a border around the main panel.
     * @author Sara Sheikho
     * @author Lilas Beirakdar
     */
    public void setUpMainPanel() {
        accountInformationPanel = new JPanel();
        accountInformationPanel.setBackground(Color.WHITE);
        createAccountInformationPanel();
        profilePicPanel = new JPanel();
        profilePicPanel.setBackground(new Color(255, 249, 163));
        setUpProfilePicPanel();
        mainPanel.add(profilePicPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(accountInformationPanel);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    /**
     * Sets up the profile picture panel by arranging components vertically,
     * defining size constraints, and adding the profile picture and
     * the button to change the picture.
     * <p>
     * If the user has a profile picture path, it is displayed; otherwise, a default
     * profile picture is shown.
     * @author Sara Sheikho
     * @author Lilas Beirakdar
     */
    public void setUpProfilePicPanel() {
        profilePicPanel.setLayout(new BoxLayout(profilePicPanel, BoxLayout.Y_AXIS));
        profilePicPanel.setMaximumSize(new Dimension(130, 200));
        profilePicPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        editProfilePicButton = createStyledButton("Change Picture");
        String profilePicPath = mainFrame.getUserProfilePicturePath();
        if(profilePicPath != null) {
            profilePicLabel = new JLabel("<html><img src='" + profilePicPath + "' width='130' height='120'></html>");
        }else{
            String path = getClass().getResource("/view/pics/default_profile_pic.jpg").toString();
            profilePicLabel = new JLabel("<html><img src='" + path + "' width='130' height='120'></html>");
        }

        editProfilePicButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePicLabel.setPreferredSize(new Dimension(110, 110));
        profilePicLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));

        profilePicPanel.add(Box.createVerticalStrut(10));
        profilePicPanel.add(profilePicLabel);
        profilePicPanel.add(Box.createVerticalStrut(5));
        profilePicPanel.add(editProfilePicButton);

    }

    /**
     * Creates the panel that displays the account information and
     * related components such as labels and buttons.
     * The panel layout is set and border is applied.
     *
     * @author Sara Sheikho
     * @author Ahmad Maarouf
     * @author Lilas Beirakdar
     */
    public void createAccountInformationPanel() {
        //Panel to add space to bottom
        emptyPanel = new JPanel();
        emptyPanel.setBackground(Color.white);
        accountInformationPanel.setLayout(new BoxLayout(accountInformationPanel, BoxLayout.X_AXIS));

        createAccountPanel();
        createAccountButtonsPanel();
        accountInformationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * Creates the account details panel containing labels for username,
     * email, password placeholder, and the user's program.
     * Styles the labels with font, color, and spacing.
     * @author Sara Sheikho
     * @author Ahmad Maarouf
     * @author Lilas Beirakdar
     */
    public void createAccountPanel() {
        Font labelFont= new Font("Segoe UI", Font.BOLD, 14);
        Color fontColor =  new Color(25, 25, 70);
        JPanel accountPanel = new JPanel();
        accountPanel.setBackground(Color.WHITE);
        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.Y_AXIS));

        // Labels
        nameLabel = new JLabel("Username: " + currentUserInfo[0]);
        nameLabel.setAlignmentX(LEFT_ALIGNMENT);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(fontColor);

        emailLabel = new JLabel("Email: " + currentUserInfo[1]);
        emailLabel.setAlignmentX(LEFT_ALIGNMENT);
        emailLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        emailLabel.setFont(labelFont);
        emailLabel.setForeground(fontColor);

        passwordLabel = new JLabel("Password: ●●●●●●●");
        passwordLabel.setAlignmentX(LEFT_ALIGNMENT);
        passwordLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(fontColor);

        JLabel programLabel = new JLabel("Your program: " + mainFrame.getStudentProgramName());
        programLabel.setAlignmentX(LEFT_ALIGNMENT);
        programLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        programLabel.setFont(labelFont);
        programLabel.setForeground(fontColor);

        accountPanel.add(nameLabel);
        accountPanel.add(Box.createVerticalStrut(10));
        accountPanel.add(emailLabel);
        accountPanel.add(Box.createVerticalStrut(10));
        accountPanel.add(passwordLabel);
        accountPanel.add(Box.createVerticalStrut(10));
        accountPanel.add(programLabel);

        accountInformationPanel.add(Box.createHorizontalStrut(5));
        accountInformationPanel.add(accountPanel);
    }

    /**
     * Creates the panel containing buttons for editing account information:
     * username, email, and password.
     * Sets the preferred size for each button and arranges them vertically with spacing.
     * @author Sara Sheikho
     * @author Ahmad Maarouf
     * @author Lilas Beirakdar
     */
    public void createAccountButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        //Buttons
        editNameButton = createStyledButton("Edit Username");
        editEmailButton = createStyledButton("Edit E-mail");
        editPasswordButton = createStyledButton("Change Password");
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

        buttonsPanel.add(Box.createVerticalStrut(15));
        buttonsPanel.add(editNameButton);
        buttonsPanel.add(Box.createVerticalStrut(20));
        buttonsPanel.add(editEmailButton);
        buttonsPanel.add(Box.createVerticalStrut(20));
        buttonsPanel.add(editPasswordButton);
        buttonsPanel.add(Box.createVerticalStrut(20));

        accountInformationPanel.add(Box.createHorizontalStrut(5));
        accountInformationPanel.add(buttonsPanel);
    }

    /**
     * Fetches the current users information from the mainFrame
     * which calls on controller.
     *
     * @author Ahmad Maarouf
     */
    public void getCurrentUserInfo() {
        this.currentUserInfo = mainFrame.getCurrentUserInfo();
    }

    /**
     * Updates the current users' information.
     * Called when email, username or password is edited.
     *
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
     *
     * @author Ahmad Maarouf
     */
    public void changeUsername() {
        String newUsername = JOptionPane.showInputDialog("Please enter new username", currentUserInfo[0]);
        if (newUsername != null) {
            if (newUsername.equals(currentUserInfo[0])) {
                //Do nothing if username not changed
            } else {
                mainFrame.setNewUsername(newUsername);
                updateUserInfo();
            }
        }
    }

    /**
     * Changes the email according to what the user inputs.
     * Also changes the email stored in a data file.
     * New email must be a valid email.
     *
     * @author Ahmad Maarouf
     */
    public void changeEmail() {
        String newEmail = JOptionPane.showInputDialog("Please enter new email", currentUserInfo[1]);
        if (mainFrame.isEmailValid(newEmail)) {
            if (newEmail.equals(currentUserInfo[1])) {
                //Do nothing if email not changed
            } else {
                mainFrame.setNewEmail(newEmail);
                updateUserInfo();
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Please enter a valid email");
        }
    }

    /**
     * Changes the password according to what the user inputs.
     * Also changes the password stored in a data file.
     *
     * @author Ahmad Maarouf
     * @author Lilas Beirakdar
     */
    public void changePassword() {
        String currentPassword = JOptionPane.showInputDialog(mainFrame, "Please enter your current password");
        String hashedPassword = Hasher.hash(currentPassword);

        if (hashedPassword.equals(currentUserInfo[2])) {
            JTextField newPassword = new JPasswordField(10);
            JTextField newConfirmPassword = new JPasswordField(10);
            Object[] options = {"New password:", newPassword, "Confirm new password: ", newConfirmPassword};

            boolean isFinished = false;

            while (!isFinished) {
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
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Incorrect password");
        }
    }

    public void changeProfilePicture() {
        new PicturesFrame(mainFrame, this);
    }

    /**
     * Adds action listeners to the buttons.
     *
     * @author Ahmad Maarouf
     * @author Sara Sheikho
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

        editProfilePicButton.addActionListener(e -> {
            changeProfilePicture();
        });
    }

    /**
     * Updates the profile picture display with the selected image path.
     * <p>
     * Sets the label to show the new profile picture with specified dimensions,
     * adjusts label alignment and border, and refreshes the main panel UI.
     * Also notifies the main frame to update the user's profile picture.
     *
     * @param selectedPicPath the file path or URL of the selected profile picture
     * @author Sara Sheikho
     */
    public void profilePictureSelected(String selectedPicPath){
        currentProfilePicPath = selectedPicPath;
        profilePicLabel.setPreferredSize(new Dimension(110, 110));
        profilePicLabel.setText("<html><img src='" + selectedPicPath + "' width='130' height='120'></html>");
        profilePicLabel.setOpaque(false);
        profilePicLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profilePicLabel.setVerticalAlignment(SwingConstants.CENTER);
        profilePicLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        mainPanel.revalidate();
        mainPanel.repaint();
        mainFrame.changeProfilePicture(selectedPicPath);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial",Font.BOLD,14));

        Color baseColor = new Color(25, 25, 70);
        Color haverColor = new Color(90, 140, 230);
        Color borderColor = baseColor.darker();

        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        //button.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        Border lineBorder= BorderFactory.createLineBorder(borderColor,2);
        Border emptyBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        button.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(haverColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(haverColor.darker(), 2),
                        BorderFactory.createEmptyBorder(2, 2,2, 2)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor, 2),
                        BorderFactory.createEmptyBorder(2, 2, 2, 2)
                ));
            }
        });

        return button;
    }
}
