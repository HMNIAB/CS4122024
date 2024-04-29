import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginWindow extends JFrame {
    private UsernameField usernameField;
    private PasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;

    public LoginWindow() {
        super("Login");

        // Initialize UI components
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new UsernameField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new PasswordField();
        loginButton = new JButton("Login");
        createAccountButton = new JButton("Create Account");

        // Add components to login panel
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(createAccountButton);

        // Add login panel to frame
        add(loginPanel);
        
        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    public void addLoginActionListener(ActionListener a) {
        loginButton.addActionListener(a);
    }

    public void addCreateAccountActionListener(ActionListener a) {
        createAccountButton.addActionListener(a);
    }
}
 