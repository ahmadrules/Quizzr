package view.subPanels;

import view.main.MainFrame;

import javax.swing.*;
import java.awt.*;

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
    private JTextField newEmailField;
    private JPanel newUserNamePanel;
    private JPanel newPasswordPanel;
    private JPanel newEmailPanel;
    private JPanel registerButtonPanel;
    private boolean registerValuesValid;

    public LogInFrame(MainFrame mainFrame) {
     this.mainFrame = mainFrame;
     setResizable(false);
     topLabel = new JLabel("", SwingConstants.CENTER);
     mainPanel = new JPanel();
     add(mainPanel);

     createLoginLayout();
     createRegisterLayout();
     addActionListeners();

     //Login layout is default layout
     setLoginLayout();
     setVisible(true);
    }

    public void createRegisterLayout() {
        registerPanel = new JPanel();
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.PAGE_AXIS));
        createdNestedRegisterPanels();
        createRegisterButtons();

        registerPanel.add(newEmailPanel);
        registerPanel.add(newUserNamePanel);
        registerPanel.add(newPasswordPanel);
        registerPanel.add(registerButtonPanel);
    }

    public void createLoginLayout() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.PAGE_AXIS));
        createNestedLoginPanels();
        createLoginButtons();

        loginPanel.add(usernamePanel);
        loginPanel.add(passwordPanel);
        loginPanel.add(loginButtonPanel);
    }

    public void setRegisterLayout() {
        setSize(250,200);
        mainPanel.removeAll();
        topLabel.setText("Register a new account");
        mainPanel.add(topLabel);
        mainPanel.add(registerPanel);

        revalidate();
        repaint();
    }

    public void setLoginLayout() {
        setSize(250,170);
        mainPanel.removeAll();
        topLabel.setText("Enter login information below");
        mainPanel.add(topLabel);
        mainPanel.add(loginPanel);

        revalidate();
        repaint();
    }

    public void createdNestedRegisterPanels() {
        topLabel.setText("Register a new user");
        JLabel newEmailLabel = new JLabel("Enter email:");
        JLabel newUsernameLabel = new JLabel("Enter username:");
        JLabel newPasswordLabel = new JLabel("Enter password:");

        newNameField = new JTextField(10);
        newPasswordField = new JTextField(10);
        newEmailField = new JTextField(10);

        newUserNamePanel = new JPanel(new FlowLayout());
        newPasswordPanel = new JPanel(new FlowLayout());
        newEmailPanel = new JPanel(new FlowLayout());
        registerButtonPanel = new JPanel(new FlowLayout());

        newUserNamePanel.add(newUsernameLabel);
        newUserNamePanel.add(newNameField);

        newPasswordPanel.add(newPasswordLabel);
        newPasswordPanel.add(newPasswordField);

        newEmailPanel.add(newEmailLabel);
        newEmailPanel.add(newEmailField);
    }

    public void createNestedLoginPanels() {
        usernamePanel = new JPanel(new FlowLayout());
        passwordPanel = new JPanel(new FlowLayout());
        loginButtonPanel = new JPanel(new FlowLayout());

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(10);
        passwordField = new JTextField(10);

        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
    }

    public void createLoginButtons() {
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        loginButtonPanel.add(loginButton);
        loginButtonPanel.add(registerButton);
    }

    public void createRegisterButtons() {
        createAccountButton = new JButton("Create");
        cancelButton = new JButton("Cancel");
        registerButtonPanel.add(createAccountButton);
        registerButtonPanel.add(cancelButton);
    }

    public void addActionListeners() {
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
                //@TODO Add function for logging in
            }
            else {
                JOptionPane.showMessageDialog(mainFrame, "Username and password required");
            }
        });

        createAccountButton.addActionListener(e -> {
            String newEmail = newEmailField.getText();
            if (newEmail != null && !newEmail.isEmpty()) {
                String newUsername = newNameField.getText();
                if (newUsername != null && !newUsername.isEmpty()) {
                    String newPassword = newPasswordField.getText();
                    if (newPassword != null && !newPassword.isEmpty()) {
                        //@TODO Add function for creating new user
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
            setLoginLayout();
        });

    }

    public boolean logIn() {
       return true;
    }
}
