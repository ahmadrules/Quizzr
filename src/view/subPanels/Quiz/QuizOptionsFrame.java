package view.subPanels.Quiz;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.util.List;

public class QuizOptionsFrame extends JFrame {

    private MainQuizFrame mainQuizFrame;
    private JPanel typePanel;
    private JPanel namePanel;
    private JPanel amountPanel;
    private JPanel timerPanel;
    private JPanel buttonsPanel;
    private JButton generateButton;
    private JButton goBackButton;
    private String amountOfQuestions;
    private JTextField nameField;
    private JComboBox<String> amountBox;
    private JComboBox<String> timerBox;
    private JComboBox<String> typeBox;

    public QuizOptionsFrame(MainQuizFrame mainQuizFrame) {
        this.mainQuizFrame = mainQuizFrame;
        setupLayout();
        createCenterPanel();
        addListeners();

        ImageIcon icon = new ImageIcon(getClass().getResource("/view/pics/Quizzr-logo.png"));
        setIconImage(icon.getImage());

        setSize(600, 400);
        setLocationRelativeTo(mainQuizFrame);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setupLayout() {
        setLayout(new BorderLayout());
        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        generateButton = createStyledButton("Generate");
        goBackButton = createStyledButton("Go Back");

        buttonsPanel.add(generateButton);
        buttonsPanel.add(goBackButton);

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        buttonsPanel.setBackground(new Color(255, 249, 163));

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(255, 249, 163));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        createCenterLinePanels();

        centerPanel.add(typePanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(namePanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(amountPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        centerPanel.add(timerPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 35));
        button.setMaximumSize(new Dimension(100, 35));
        button.setMinimumSize(new Dimension(100, 35));

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial",Font.BOLD,13));

        Color baseColor = new Color(25, 25, 70);
        Color haverColor = new Color(90, 140, 230);
        Color borderColor = baseColor.darker();

        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        Border lineBorder= BorderFactory.createLineBorder(borderColor,1);
        Border emptyBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
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

    public void createCenterLinePanels() {
        int labelWidth=140;
        int labelHeight= 30;
        Color labelFontColor = new Color(25, 25, 70);
        Font labelFont= new Font("Segoe UI",Font.BOLD,16);

        typePanel = new JPanel();
        typePanel.setLayout(new BoxLayout(typePanel,BoxLayout.Y_AXIS));
        typePanel.setBackground(new Color(255, 249, 163));
        typePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel typeLabel = new JLabel("Type of quiz:");
        typeLabel.setFont(labelFont);
        typeLabel.setForeground(labelFontColor);
        typeLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        typeBox = new JComboBox<>();
        createBordersAndColorsJComponents(typeBox);
        typeBox.setForeground(labelFontColor);
        typeBox.addItem("Multiple choice");
        typeBox.addItem("True/False");
        typeBox.addItem("Matching");
        typeBox.setMaximumSize(new Dimension(250, 30));
        typeBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        typePanel.add(typeLabel);
        typePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        typePanel.add(typeBox);


        namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.setBackground(new Color(255, 249, 163));
        namePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nameLabel = new JLabel("Quiz name:");
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(labelFontColor);
        nameLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        nameLabel.setMaximumSize(new Dimension(labelWidth, labelHeight));
        nameLabel.setMinimumSize(new Dimension(labelWidth, labelHeight));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        nameField = new JTextField();
        nameField.setBorder(BorderFactory.createLineBorder(labelFontColor, 2));
        nameField.setPreferredSize(new Dimension(250, 30));
        nameField.setMaximumSize(new Dimension(250, 30));
        nameField.setMinimumSize(new Dimension(250, 30));
        nameField.setAlignmentX(Component.LEFT_ALIGNMENT);

        namePanel.add(nameLabel);
        namePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        namePanel.add(nameField);

        amountPanel = new JPanel();
        amountPanel.setLayout(new BoxLayout(amountPanel, BoxLayout.Y_AXIS));
        amountPanel.setBackground(new Color(255, 249, 163));
        amountPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel amountLabel = new JLabel("Amount of questions:");
        amountLabel.setForeground(labelFontColor);
        amountLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        amountLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        amountBox = new JComboBox<>();
        createBordersAndColorsJComponents(amountBox);

        for (int i = 5; i < 31; i++) {
            amountBox.addItem(Integer.toString(i));
        }
        amountBox.setMaximumSize(new Dimension(250, 30));
        amountBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        amountPanel.add(amountLabel);
        amountPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        amountPanel.add(amountBox);

        timerPanel = new JPanel();
        timerPanel.setLayout(new BoxLayout(timerPanel, BoxLayout.Y_AXIS));
        timerPanel.setBackground(new Color(255, 249, 163));
        timerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel timerLabel = new JLabel("<html>Timer (<span style='color:red;'>minutes</span>):</html>");
        timerLabel.setFont(labelFont);
        timerLabel.setForeground(labelFontColor);
        timerLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        timerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        timerBox = new JComboBox<>();
        createBordersAndColorsJComponents(timerBox);
        timerBox.setMaximumSize(new Dimension(250, 30));
        timerBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        timerBox.addItem("No timer");

        for (int i = 1; i < 16; i++) {
            timerBox.addItem(Integer.toString(i));
        }

        timerPanel.add(timerLabel);
        timerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        timerPanel.add(timerBox);
    }
    public void createBordersAndColorsJComponents(JComboBox comboBox) {
        Color customColor = new Color(25, 25, 70);
        Font labelFont= new Font("Segoe UI",Font.PLAIN,14);
        comboBox.setFont(labelFont);
        comboBox.setBorder(BorderFactory.createLineBorder(customColor,2));
        comboBox.setUI(new BasicComboBoxUI(){
            @Override
            protected JButton createArrowButton() {
                JButton arrowButton = new BasicArrowButton(
                        BasicArrowButton.SOUTH,
                        Color.WHITE,
                        customColor,
                        customColor,
                        customColor
                );
                arrowButton.setBorder(BorderFactory.createLineBorder(customColor,1));
                arrowButton.setBackground(Color.WHITE);
                return arrowButton;
            }
        }
        );
    }

    /**
     *
     */
    private void addListeners() {
        generateButton.addActionListener(e -> {
            long timerSeconds = 0;

            if (timerBox.getSelectedItem() != "No timer") {
                timerSeconds = Integer.parseInt((String) timerBox.getSelectedItem()) * 60L;
            }

            amountOfQuestions = (String) amountBox.getSelectedItem();

            String typeOfQuiz = (String) typeBox.getSelectedItem();

            String newQuizName = nameField.getText();
            if (newQuizName != null && !newQuizName.isEmpty()) {
                List<String> quizNames = mainQuizFrame.getRelatedQuizNames();
                if (quizNames.contains(newQuizName)) {
                    JOptionPane.showMessageDialog(null, "Name already in use");
                } else {
                    mainQuizFrame.generateQuiz(Integer.parseInt(amountOfQuestions), newQuizName, typeOfQuiz, timerSeconds);
                    dispose();
                    mainQuizFrame.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a valid quiz name");
            }
        });
        goBackButton.addActionListener(e -> {
            mainQuizFrame.setVisible(true);
            dispose();
        });
    }
}
