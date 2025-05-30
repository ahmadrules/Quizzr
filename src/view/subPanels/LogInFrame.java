package view.subPanels;

import controller.Controller;
import view.main.MainFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.security.DigestException;
import java.util.List;

/**
 * This class is responsible for offering and validating options
 * to log in to and register an account.
 * To validate the login information it calls on mainFrame which calls on controller.
 *
 * @author Ahmad Maarouf
 */
public class LogInFrame extends JFrame {
    private MainFrame mainFrame;
    private JTextField usernameField;
    private JPanel loginPanel;
    private JTextField passwordField;
    private JPanel loginButtonPanel;
    private JPanel mainPanel;
    private JLabel topLabel;
    private JPanel registerPanel;
    private JTextField newNameField;
    private JTextField newPasswordField;
    private JTextField confirmPasswordField;
    private JTextField newEmailField;
    private JPanel registerButtonPanel;
    private JComboBox<String> programCodeBox;
    private JPanel topPanel;
    private JPanel firstPagePanel;
    private Controller controller;
    private JButton adminLoginButton;
    private JButton studentLoginButton;
    private JButton goBackButton;
    private JButton loginButton;
    private JButton registerButton;
    private JButton createAccountButton;
    private JButton cancelButton;
    private JButton testButton;
    private boolean isAdmin;

    public LogInFrame(Controller controller) {
        this.controller = controller;

        this.mainFrame = new MainFrame(controller);

        ImageIcon icon = new ImageIcon(getClass().getResource("/view/pics/Quizzr-logo.png"));
        setIconImage(icon.getImage());

        setResizable(false);
        topLabel = new JLabel("", SwingConstants.CENTER);
        mainPanel = new BackgroundPanel("src/background1.jpg");
        mainPanel.setBackground(new Color(255, 249, 163));
        add(mainPanel);

        createLoginLayout();
        createRegisterLayout();
        createFirstPage();

        //Login layout is default layout
        showFirstPage();

        addActionListeners();

        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Creates the layout for the "Register" panel.
     *
     * @author Ahmad Maarouf
     */
    public void createRegisterLayout() {
        registerPanel = new BackgroundPanel("src/background1.jpg");
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.PAGE_AXIS));
        createdNestedRegisterPanels();
        createRegisterButtons();
        registerPanel.add(topLabel, BorderLayout.NORTH);
        registerPanel.add(registerButtonPanel, BorderLayout.SOUTH);
    }

    public void createFirstPage() {
        firstPagePanel = new BackgroundPanel("src/background1.jpg");
        firstPagePanel.setLayout(new BorderLayout());
        firstPagePanel.setBackground(new Color(255, 249, 163));
        firstPagePanel.setBorder(BorderFactory.createLineBorder(new Color(255, 249, 163)));

        JLabel firstPageLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<span style='color: #1A237E;'>Welcome to </span>"
                + "<span style='color: rgb(250,155,22); font-weight: bold;'>Quizzr</span>"
                + "</div></html", SwingConstants.CENTER);
        firstPageLabel.setFont(new Font("Georgia", Font.BOLD, 32));
        firstPagePanel.setForeground(Color.WHITE);
        firstPageLabel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        firstPagePanel.add(firstPageLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(255, 249, 163));
        buttonPanel.setOpaque(false);

        adminLoginButton = createStyledButton("Admin Login", 260, 50);
        studentLoginButton = createStyledButton("Student Login", 260, 50);
        testButton = createStyledButton("Test login", 260, 50);
        registerButton = createStyledButton("Register new student", 260, 50);

        Dimension buttonSize = new Dimension(250, 50);
        int FontSize = 16;
        Font buttonFont = new Font("Montserrat", Font.BOLD, FontSize);

        adminLoginButton.setMaximumSize(buttonSize);
        adminLoginButton.setPreferredSize(buttonSize);
        adminLoginButton.setMinimumSize(buttonSize);
        adminLoginButton.setFont(buttonFont);

        studentLoginButton.setMaximumSize(buttonSize);
        studentLoginButton.setPreferredSize(buttonSize);
        studentLoginButton.setMinimumSize(buttonSize);
        studentLoginButton.setFont(buttonFont);

        testButton.setMaximumSize(buttonSize);
        testButton.setPreferredSize(buttonSize);
        testButton.setMinimumSize(buttonSize);
        testButton.setFont(buttonFont);

        registerButton.setMaximumSize(buttonSize);
        registerButton.setPreferredSize(buttonSize);
        registerButton.setMinimumSize(buttonSize);
        registerButton.setFont(buttonFont);

        adminLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        studentLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        testButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(adminLoginButton);
        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(studentLoginButton);
        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(testButton);
        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(registerButton);

        firstPagePanel.add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(width, height));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Montserrat", Font.BOLD, 16));

        Color baseColor = new Color(25, 25, 70);
        Color haverColor = new Color(90, 140, 230);
        Color borderColor = baseColor.darker();

        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        //button.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        Border lineBorder = BorderFactory.createLineBorder(borderColor, 4);
        Border emptyBorder = BorderFactory.createEmptyBorder(10, 20, 10, 20);
        button.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(haverColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(haverColor.darker(), 2),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor, 2),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
        });

        return button;
    }


    public void showFirstPage() {
        mainPanel.removeAll();
        mainPanel.add(firstPagePanel);
        registerButton.setEnabled(true);
        mainPanel.revalidate();
        mainPanel.repaint();
        setSize(600, 500);
        setLocationRelativeTo(null);
    }

    /**
     * Creates the layout for the "Login" panel.
     *
     * @author Ahmad Maarouf
     */
    public void createLoginLayout() {
        loginPanel = new BackgroundPanel("src/background1.jpg");
        loginPanel.setLayout(new BorderLayout());
        createNestedLoginPanels();
        createLoginButtons();
        loginPanel.add(topPanel, BorderLayout.CENTER);
        loginPanel.add(loginButtonPanel, BorderLayout.SOUTH);
    }

    /**
     * Sets the layout to the "Register" layout.
     * Called when the user presses the "Register" button.
     *
     * @author Ahmad Maarouf
     */
    public void setRegisterLayout() {
        setSize(600, 500);
        setTitle("Register");
        mainPanel.removeAll();
        topLabel.setText("Register a new account");
        mainPanel.add(topLabel);
        mainPanel.add(registerPanel);

        revalidate();
        repaint();
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        newNameField.setText("");
        newPasswordField.setText("");
        confirmPasswordField.setText("");
        newEmailField.setText("");
        programCodeBox.setSelectedIndex(-1);
    }

    /**
     * Sets the layout to the "Login" layout.
     * Called when the user first opens the program or
     * if the users presses "Cancel" while on the "Register" layout.
     *
     * @author Ahmad Maarouf
     */
    public void setLoginLayout() {
        setSize(600, 500);
        setTitle("Login");
        mainPanel.removeAll();
        // mainPanel.add(topLabel);
        mainPanel.add(loginPanel);

        revalidate();
        repaint();
    }

    /**
     * Creates the panels required to display the register layout correctly.
     * The main panel uses flowlayout and the panels created here each account for
     * one row in the main panel.
     *
     * @author Ahmad Maarouf
     */
    public void createdNestedRegisterPanels() {
        Font font = new Font("Montserrat", Font.PLAIN, 16);
        topLabel.setText("Register a new user");
        topLabel.setFont(new Font("Montserrat", Font.BOLD, 16));

        JLabel newEmailLabel = new JLabel("Enter email:");
        JLabel newUsernameLabel = new JLabel("Enter username:");
        JLabel newPasswordLabel = new JLabel("Enter password:");
        JLabel confirmPasswordLabel = new JLabel("Confirm password:");
        JLabel programCodeLabel = new JLabel("Choose program code:");

        newEmailLabel.setFont(font);
        newUsernameLabel.setFont(font);
        newPasswordLabel.setFont(font);
        confirmPasswordLabel.setFont(font);
        programCodeLabel.setFont(font);

        newEmailLabel.setForeground(new Color(10, 10, 40));
        newUsernameLabel.setForeground(new Color(10, 10, 40));
        newPasswordLabel.setForeground(new Color(10, 10, 40));
        confirmPasswordLabel.setForeground(new Color(10, 10, 40));
        programCodeLabel.setForeground(new Color(10, 10, 40));

        newNameField = new JTextField(10);
        newPasswordField = new JPasswordField(10);
        confirmPasswordField = new JPasswordField(10);
        newEmailField = new JTextField(10);
        programCodeBox = new JComboBox<String>();

        List<String> programCodes = mainFrame.getProgramCodes();
        for (String programName : programCodes) {
            programCodeBox.addItem(programName);
        }
        programCodeBox.setSelectedIndex(-1);

        newNameField.setFont(font);
        newPasswordField.setFont(font);
        confirmPasswordField.setFont(font);
        newEmailField.setFont(font);
        programCodeBox.setFont(font);

        Dimension labelSize = new Dimension(200, 30);
        newEmailLabel.setPreferredSize(labelSize);
        newUsernameLabel.setPreferredSize(labelSize);
        newPasswordLabel.setPreferredSize(labelSize);
        confirmPasswordLabel.setPreferredSize(labelSize);
        programCodeLabel.setPreferredSize(labelSize);

        Dimension fieldSize = new Dimension(200, 30);
        newNameField.setPreferredSize(fieldSize);
        newPasswordField.setPreferredSize(fieldSize);
        confirmPasswordField.setPreferredSize(fieldSize);
        newEmailField.setPreferredSize(fieldSize);
        programCodeBox.setPreferredSize(fieldSize);

        JPanel newUserNamePanel = new JPanel();
        JPanel newPasswordPanel = new JPanel();
        JPanel confirmPasswordPanel = new JPanel();
        JPanel newEmailPanel = new JPanel();
        JPanel programCodePanel = new JPanel();
        registerButtonPanel = new JPanel(new FlowLayout());

        newUserNamePanel.setBackground(new Color(255, 249, 163));
        newPasswordPanel.setBackground(new Color(255, 249, 163));
        confirmPasswordPanel.setBackground(new Color(255, 249, 163));
        newEmailPanel.setBackground(new Color(255, 249, 163));
        programCodePanel.setBackground(new Color(255, 249, 163));

        newEmailPanel.setLayout(new BoxLayout(newEmailPanel, BoxLayout.Y_AXIS));
        newEmailPanel.add(newEmailLabel);
        newEmailPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        newEmailPanel.add(newEmailField);

        newUserNamePanel.setLayout(new BoxLayout(newUserNamePanel, BoxLayout.Y_AXIS));
        newUserNamePanel.add(newUsernameLabel);
        newUserNamePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        newUserNamePanel.add(newNameField);

        newPasswordPanel.setLayout(new BoxLayout(newPasswordPanel, BoxLayout.Y_AXIS));
        newPasswordPanel.add(newPasswordLabel);
        newPasswordPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        newPasswordPanel.add(newPasswordField);

        confirmPasswordPanel.setLayout(new BoxLayout(confirmPasswordPanel, BoxLayout.Y_AXIS));
        confirmPasswordPanel.add(confirmPasswordLabel);
        confirmPasswordPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        confirmPasswordPanel.add(confirmPasswordField);


        programCodePanel.setLayout(new BoxLayout(programCodePanel, BoxLayout.Y_AXIS));
        programCodePanel.add(programCodeLabel);
        programCodePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        programCodePanel.add(programCodeBox);

        JPanel mainRegisterPanel = new JPanel();
        mainRegisterPanel.setLayout(new BoxLayout(mainRegisterPanel, BoxLayout.Y_AXIS));
        mainRegisterPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        mainRegisterPanel.setBackground(new Color(255, 249, 163));

        mainRegisterPanel.add(newEmailPanel);
        mainRegisterPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainRegisterPanel.add(newUserNamePanel);
        mainRegisterPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainRegisterPanel.add(newPasswordPanel);
        mainRegisterPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainRegisterPanel.add(confirmPasswordPanel);
        mainRegisterPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        mainRegisterPanel.add(programCodePanel);

        registerPanel.removeAll();
        registerPanel.setLayout(new BorderLayout());
        registerPanel.add(mainRegisterPanel, BorderLayout.CENTER);
    }

    /**
     * Creates the panels required to display the login layout correctly.
     * The main panel uses flowlayout and the panels created here each account for
     * one row in the main panel.
     *
     * @author Ahmad Maarouf
     */
    public void createNestedLoginPanels() {
        Font font = new Font("Segoe UI", Font.BOLD, 18);

        topPanel = new JPanel();
        JPanel usernamePanel = new JPanel();
        JPanel passwordPanel = new JPanel();
        loginButtonPanel = new JPanel();

        passwordPanel.setBackground(new Color(255, 249, 163));
        topPanel.setBackground(new Color(255, 249, 163));
        usernamePanel.setBackground(new Color(255, 249, 163));
        loginButtonPanel.setBackground(new Color(255, 249, 163));

        Dimension labelSize = new Dimension(300, 30);
        Dimension fieldSize = new Dimension(300, 30);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel insructionsLabel = new JLabel("Please enter your username as a name not email");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);

        usernameLabel.setPreferredSize(labelSize);
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordLabel.setPreferredSize(labelSize);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        insructionsLabel.setPreferredSize(labelSize);


        usernameField.setPreferredSize(fieldSize);
        usernameField.setMaximumSize(fieldSize);
        usernameField.setMinimumSize(fieldSize);
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        usernameLabel.setFont(font);
        usernameLabel.setForeground(new Color(10, 10, 40));


        insructionsLabel.setFont(new Font("Montserrat", Font.PLAIN, 12));
        insructionsLabel.setForeground(new Color(10, 10, 40));

        passwordField.setPreferredSize(fieldSize);
        passwordField.setMaximumSize(fieldSize);
        passwordField.setMinimumSize(fieldSize);
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordLabel.setFont(font);
        passwordLabel.setForeground(new Color(10, 10, 40));

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        JLabel titleLabel = new JLabel("Please enter you Login Information");
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        titleLabel.setForeground(new Color(10, 10, 40));
        titleLabel.setPreferredSize(labelSize);
        topPanel.add(titleLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.Y_AXIS));
        usernamePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        usernamePanel.add(insructionsLabel);

        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        passwordPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        // usernamePanel.add(passwordPanel);
        topPanel.add(usernamePanel);
        topPanel.add(passwordPanel);
    }

    /**
     * Creates the buttons required to display the login layout correctly.
     *
     * @author Ahmad Maarouf
     */
    public void createLoginButtons() {
        loginButton = createStyledButton("Login", 70, 50);
        goBackButton = createStyledButton("Go back", 70, 50);

        loginButton.setFont(new Font("Montserrat", Font.PLAIN, 18));
        goBackButton.setFont(new Font("Montserrat", Font.PLAIN, 18));

        Dimension labelSize = new Dimension(150, 30);
        // loginButton.setPreferredSize(labelSize);
        //goBackButton.setPreferredSize(labelSize);

        loginButtonPanel.add(loginButton);
        loginButtonPanel.add(goBackButton);
    }

    /**
     * Creates the buttons required to display the register layout correctly.
     *
     * @author Ahmad Maarouf
     */
    public void createRegisterButtons() {
        createAccountButton = createStyledButton("Create", 70, 50);
        cancelButton = createStyledButton("Cancel", 70, 30);

        registerButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        registerButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        registerButtonPanel.setBackground(new Color(255, 249, 163));
        registerButtonPanel.add(createAccountButton);
        registerButtonPanel.add(cancelButton);
    }

    /**
     * Adds action listeners to the buttons pressed when the user
     * wants to log in to an account or create a new one.
     * Here we validate the input before sending it to mainFrame which sends it to controller.
     *
     * @author Ahmad Maarouf
     */
    public void addActionListeners() {
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            System.out.println("Trying to log in with: " + username + ", " + password);
            if (!username.isEmpty() && !password.isEmpty()) {
                boolean success = controller.loginUser(username, password);
                if (success) {
                    JOptionPane.showMessageDialog(mainFrame, "Logged in successfully");
                    mainFrame.createAndShowGUI(isAdmin);
                    controller.setMainFrame(mainFrame);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "User does not exist");
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Username and password required");
            }
        });

        createAccountButton.addActionListener(e -> {
            String newEmail = newEmailField.getText();
            if (mainFrame.isEmailValid(newEmail)) {
                String newUsername = newNameField.getText();
                if (newUsername != null && !newUsername.isEmpty()) {
                    String newPassword = newPasswordField.getText();
                    String confirmedPassword = confirmPasswordField.getText();
                    if (newPassword != null && !newPassword.isEmpty()) {
                        if (newPassword.equals(confirmedPassword)) {
                            String programCode = (String) programCodeBox.getSelectedItem();
                            if (programCode == null) {
                                JOptionPane.showMessageDialog(mainFrame, "Please select a program code");
                            } else {
                                boolean success = mainFrame.registerNewUser(newUsername, newPassword, newEmail, programCode);
                                if (success) {
                                    JOptionPane.showMessageDialog(mainPanel, "User successfully registered");
                                    clearFields();
                                    setLoginLayout();
                                    isAdmin = false;
                                } else {
                                    JOptionPane.showMessageDialog(mainPanel, "User already exists");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(mainFrame, "Passwords do not match");
                        }
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Your password cannot be empty");
                    }
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Please enter a valid username");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid email");
            }
        });

        registerButton.addActionListener(e -> {
            setRegisterLayout();
        });

        cancelButton.addActionListener(e -> {
            showFirstPage();
            clearFields();
        });

        studentLoginButton.addActionListener(e -> {
            setLoginLayout();
            isAdmin = false;
            registerButton.setEnabled(true);
        });

        adminLoginButton.addActionListener(e -> {
            setLoginLayout();
            isAdmin = true;
            registerButton.setEnabled(false);
        });

        goBackButton.addActionListener(e -> {
            showFirstPage();
            clearFields();
        });

        testButton.addActionListener(e -> {
            controller.loginUser("test", "123");
            mainFrame.createAndShowGUI(true);

            controller.setMainFrame(mainFrame);
            setVisible(false);
        });

    }
}
