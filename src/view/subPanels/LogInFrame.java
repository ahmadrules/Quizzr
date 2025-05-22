package view.subPanels;

import controller.Controller;
import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class is responsible for offering and validating options
 * to log in to and register an account.
 * To validate the login information it calls on mainFrame which calls on controller.
 * @author Ahmad Maarouf
 */
public class LogInFrame extends JFrame {
    private MainFrame mainFrame;
    private JTextField usernameField;
    private JPanel loginPanel;
    private JTextField passwordField;
    private JPanel usernamePanel;
    private JPanel passwordPanel;
    private JPanel loginButtonPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JPanel mainPanel;
    private JLabel topLabel;
    private JPanel registerPanel;
    private JButton createAccountButton;
    private JButton cancelButton;
    private JTextField newNameField;
    private JTextField newPasswordField;
    private JTextField confirmPasswordField;
    private JTextField newEmailField;
    private JPanel newUserNamePanel;
    private JPanel newPasswordPanel;
    private JPanel confirmPasswordPanel;
    private JPanel newEmailPanel;
    private JPanel registerButtonPanel;
    private JTextField programCodeField;
    private JPanel programCodePanel;
    private Controller controller;
    private JButton adminLoginButton;
    private JButton studentLoginButton;
    private JButton goBackButton;
    private JPanel firstPagePanel;
    private JButton testButton;
    private boolean isAdmin;

    public LogInFrame( Controller controller ) {
     this.controller = controller;

     this.mainFrame = new MainFrame(controller);
     setResizable(true);
     topLabel = new JLabel("", SwingConstants.CENTER);
     mainPanel = new JPanel();
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
     * @author Ahmad Maarouf
     */
    public void createRegisterLayout() {
        registerPanel = new JPanel();
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.PAGE_AXIS));
        createdNestedRegisterPanels();
        createRegisterButtons();
        registerPanel.add(topLabel, BorderLayout.NORTH);
        registerPanel.add(registerButtonPanel, BorderLayout.SOUTH);
     /**   registerPanel.add(newEmailPanel);
        registerPanel.add(newUserNamePanel);
        registerPanel.add(newPasswordPanel);
        registerPanel.add(confirmPasswordPanel);
        registerPanel.add(programCodePanel);
        registerPanel.add(registerButtonPanel);*/
    }

    public void createFirstPage() {
        firstPagePanel = new JPanel();
        firstPagePanel.setLayout(new BorderLayout());

        JLabel firstPageLabel = new JLabel("<html>Welcome to <font color=orange> Quizzr! </font></html>", SwingConstants.CENTER);
        firstPageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        firstPagePanel.add(firstPageLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        adminLoginButton = new JButton("Admin Login");
        studentLoginButton = new JButton("Student Login");
        testButton = new JButton("Test login");
        registerButton=new JButton("Register new student");

        Dimension buttonSize = new Dimension(250, 50);
        int FontSize = 16;
        Font buttonFont = new Font("Arial", Font.BOLD, FontSize);

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

        buttonPanel.add(Box.createVerticalStrut(60));
        buttonPanel.add(adminLoginButton);
        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(studentLoginButton);
        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(testButton);
        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(registerButton);

        firstPagePanel.add(buttonPanel, BorderLayout.CENTER);
    }

    public void showFirstPage() {
        mainPanel.removeAll();
        mainPanel.add(firstPagePanel);
        registerButton.setEnabled(true);
        mainPanel.revalidate();
        mainPanel.repaint();
        setSize(600,500);
        setLocationRelativeTo(null);
       // revalidate();
       // repaint();
    }

    /**
     * Creates the layout for the "Login" panel.
     * @author Ahmad Maarouf
     */
    public void createLoginLayout() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.PAGE_AXIS));
        createNestedLoginPanels();
        createLoginButtons();

        loginPanel.add(usernamePanel);
        loginPanel.add(passwordPanel);
        loginPanel.add(loginButtonPanel);
    }

    /**
     * Sets the layout to the "Register" layout.
     * Called when the user presses the "Register" button.
     * @author Ahmad Maarouf
     */
    public void setRegisterLayout() {
        setSize(600,500);
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
        programCodeField.setText("");
    }

    /**
     * Sets the layout to the "Login" layout.
     * Called when the user first opens the program or
     * if the users presses "Cancel" while on the "Register" layout.
     * @author Ahmad Maarouf
     */
    public void setLoginLayout() {
        setSize(250,170);
        setTitle("Login");
        mainPanel.removeAll();
        topLabel.setText("Enter login information below");
        mainPanel.add(topLabel);
        mainPanel.add(loginPanel);

        revalidate();
        repaint();
    }

    /**
     * Creates the panels required to display the register layout correctly.
     * The main panel uses flowlayout and the panels created here each account for
     * one row in the main panel.
     * @author Ahmad Maarouf
     */
    public void createdNestedRegisterPanels() {
        Font font= new Font("Arial", Font.PLAIN, 16);
        topLabel.setText("Register a new user");
        topLabel.setFont(font);

        JLabel newEmailLabel = new JLabel("Enter email:");
        JLabel newUsernameLabel = new JLabel("Enter username:");
        JLabel newPasswordLabel = new JLabel("Enter password:");
        JLabel confirmPasswordLabel = new JLabel("Confirm password:");
        JLabel programCodeLabel = new JLabel("Enter program code:");

        newEmailLabel.setFont(font);
        newUsernameLabel.setFont(font);
        newPasswordLabel.setFont(font);
        confirmPasswordLabel.setFont(font);
        programCodeLabel.setFont(font);

        newNameField = new JTextField(10);
        newPasswordField = new JPasswordField(10);
        confirmPasswordField = new JPasswordField(10);
        newEmailField = new JTextField(10);
        programCodeField = new JTextField(10);

        newNameField.setFont(font);
        newPasswordField.setFont(font);
        confirmPasswordField.setFont(font);
        programCodeField.setFont(font);
        newEmailField.setFont(font);
        programCodeField.setFont(font);

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
        programCodeField.setPreferredSize(fieldSize);

        newUserNamePanel = new JPanel();
        newPasswordPanel = new JPanel();
        confirmPasswordPanel = new JPanel();
        newEmailPanel = new JPanel();
        programCodePanel = new JPanel();
        registerButtonPanel = new JPanel(new FlowLayout());

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

        newEmailPanel.setLayout(new BoxLayout(newEmailPanel, BoxLayout.Y_AXIS));
        newEmailPanel.add(newEmailLabel);
        newEmailPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        newEmailPanel.add(newEmailField);

        programCodePanel.setLayout(new BoxLayout(programCodePanel, BoxLayout.Y_AXIS));
        programCodePanel.add(programCodeLabel);
        programCodePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        programCodePanel.add(programCodeField);

        JPanel mainRegisterPanel = new JPanel();
        mainRegisterPanel.setLayout(new BoxLayout(mainRegisterPanel, BoxLayout.Y_AXIS));
        mainRegisterPanel.setBorder(BorderFactory.createEmptyBorder(20,50,20,50));

        mainRegisterPanel.add(newUserNamePanel);
        mainRegisterPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainRegisterPanel.add(newPasswordPanel);
        mainRegisterPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainRegisterPanel.add(confirmPasswordPanel);
        mainRegisterPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainRegisterPanel.add(newEmailPanel);
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
     * @author Ahmad Maarouf
     */
    public void createNestedLoginPanels() {
        usernamePanel = new JPanel(new FlowLayout());
        passwordPanel = new JPanel(new FlowLayout());
        loginButtonPanel = new JPanel(new FlowLayout());

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);

        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
    }

    /**
     * Creates the buttons required to display the login layout correctly.
     * @author Ahmad Maarouf
     */
    public void createLoginButtons() {
        loginButton = new JButton("Login");
       //registerButton = new JButton("Register");
        goBackButton = new JButton("Go back");
        loginButtonPanel.add(loginButton);
    //    loginButtonPanel.add(registerButton);
        loginButtonPanel.add(goBackButton);
    }

    /**
     * Creates the buttons required to display the register layout correctly.
     * @author Ahmad Maarouf
     */
    public void createRegisterButtons() {
        createAccountButton = new JButton("Create");
        cancelButton = new JButton("Cancel");

        registerButtonPanel=new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        registerButtonPanel.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));
        registerButtonPanel.add(createAccountButton);
        registerButtonPanel.add(cancelButton);
    }

    /**
     * Adds action listeners to the buttons pressed when the user
     * wants to log in to an account or create a new one.
     * Here we validate the input before sending it to mainFrame which sends it to controller.
     * @author Ahmad Maarouf
     */
    public void addActionListeners() {
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            System.out.println("Trying to log in with: " + username + ", " + password);
            if (!username.isEmpty() && !password.isEmpty()) {
                boolean success= controller.loginUser(username, password);
                if (success) {
                    JOptionPane.showMessageDialog(mainFrame, "Logged in successfully");
                    mainFrame.createAndShowGUI(isAdmin);
                    controller.setMainFrame(mainFrame);
                    setVisible(false);
                }
                else {
                    JOptionPane.showMessageDialog(mainFrame, "User does not exist");
                }
            }
            else {
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
                            String programCode = programCodeField.getText();
                            List<String> programCodes=controller.programCodes();
                            if(programCode != null && !programCode.isEmpty() && programCodes.contains(programCode) ) {
                            boolean success= mainFrame.registerNewUser(newUsername,newPassword,newEmail,programCode);
                            if (success) {
                                JOptionPane.showMessageDialog(mainPanel, "User successfully registered");
                                clearFields();
                                setLoginLayout();
                            }else{
                                JOptionPane.showMessageDialog(mainPanel, "User already exists");
                            }}else {
                                JOptionPane.showMessageDialog(mainPanel, "Please enter a valid Program Code");

                            }                        } else {
                            JOptionPane.showMessageDialog(mainFrame, "Passwords do not match");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(mainFrame, "Your password cannot be empty");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(mainFrame, "Please enter a valid username");
                }
            }
            else {
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
