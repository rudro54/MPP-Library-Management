package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import business.LoginException;
import business.SystemController;


public class LoginWindow extends JFrame implements LibWindow {
    public static final LoginWindow INSTANCE = new LoginWindow();

    private boolean isInitialized = false;

    private JPanel mainPanel;
    private JPanel upperHalf;
    private JPanel middleHalf;

    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private JPanel leftTextPanel;
    private JPanel rightTextPanel;

    private JTextField username;
    private JTextField password;
    private JLabel label;
    private JButton loginButton;

    public boolean isInitialized() {
        return isInitialized;
    }

    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    /* This class is a singleton */
    private LoginWindow() {
    }

    public void init() {

        mainPanel = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        defineUpperHalf();
        defineMiddleHalf();
        BorderLayout bl = new BorderLayout();
        bl.setVgap(30);
        mainPanel.setLayout(bl);

        mainPanel.add(upperHalf, BorderLayout.NORTH);
        mainPanel.add(middleHalf, BorderLayout.CENTER);
        getContentPane().add(mainPanel);
        isInitialized(true);
        pack();
    }

    private void defineUpperHalf() {

        upperHalf = new JPanel();
        upperHalf.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();
        upperHalf.add(topPanel, BorderLayout.NORTH);
        upperHalf.add(middlePanel, BorderLayout.CENTER);
        upperHalf.add(lowerPanel, BorderLayout.SOUTH);
    }

    private void defineMiddleHalf() {

        middleHalf = new JPanel();
        middleHalf.setLayout(new BorderLayout());
        JSeparator s = new JSeparator();
        s.setOrientation(SwingConstants.HORIZONTAL);
        middleHalf.add(s, BorderLayout.SOUTH);
    }

    private void defineTopPanel() {

        topPanel = new JPanel();
        JPanel intPanel = new JPanel(new BorderLayout());
        intPanel.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.NORTH);
        JLabel loginLabel = new JLabel("Enter Login Credentials");
        Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
        intPanel.add(loginLabel, BorderLayout.CENTER);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(intPanel);

    }

    private void defineMiddlePanel() {

        middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        defineLeftTextPanel();
        defineRightTextPanel();
        middlePanel.add(leftTextPanel);
        middlePanel.add(rightTextPanel);
    }

    private void defineLowerPanel() {

        lowerPanel = new JPanel();
        loginButton = new JButton("Login");
        addLoginButtonListener(loginButton);
        lowerPanel.add(loginButton);
    }

    private void defineLeftTextPanel() {

        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        username = new JTextField(15);
        label = new JLabel("User ID");
        label.setFont(Util.makeSmallFont(label.getFont()));
        topText.add(username);
        bottomText.add(label);

        leftTextPanel = new JPanel();
        leftTextPanel.setLayout(new BorderLayout());
        leftTextPanel.add(bottomText, BorderLayout.NORTH);
        leftTextPanel.add(topText, BorderLayout.CENTER);

    }

    private void defineRightTextPanel() {

        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        password = new JPasswordField(15);
        label = new JLabel("Password");
        label.setFont(Util.makeSmallFont(label.getFont()));
        topText.add(password);
        bottomText.add(label);

        rightTextPanel = new JPanel();
        rightTextPanel.setLayout(new BorderLayout());
        rightTextPanel.add(bottomText, BorderLayout.NORTH);
        rightTextPanel.add(topText, BorderLayout.CENTER);

    }

    private void addLoginButtonListener(JButton butn) {

        butn.addActionListener(evt -> {
            try {
                SystemController sc = new SystemController();
                sc.login(username.getText(), password.getText());
                setVisible(false);
				clearFields();
                new Main(SystemController.currentAuth.toString());
            } catch (LoginException le) {
                JOptionPane.showMessageDialog(this, le.getMessage());
            }
        });
    }

	private void clearFields() {
		username.setText("");
		password.setText("");
	}

}
