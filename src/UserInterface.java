import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserInterface extends JFrame {
    private UserDatabase db;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public UserInterface(UserDatabase db) {
        super("User Login");
        this.db = db;

        // Initialize UI components
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton createAccountButton = new JButton("Create Account");

        // Add components to login panel
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(createAccountButton);

        // Add action listeners
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCreateAccountDialog();
            }
        });

        // Add login panel to frame
        add(loginPanel);
        
        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to handle login button click
    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Check if username and password are not empty
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password are required", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Authenticate user
        authenticateUser(username, password);
    }

    // Method to authenticate user
    private void authenticateUser(String username, String password) {
        // Check if user exists in the database
        // For simplicity, we're just checking if the username and password match any existing record
        // In a real-world scenario, you'd want to hash passwords and compare hashed values
        // Also, you'd typically want to handle authentication failures more gracefully
        db.getUserByUsername(username);
    }

    // Method to show create account dialog
    private void showCreateAccountDialog() {
        JFrame createAccountFrame = new JFrame("Create Account");
        JPanel createAccountPanel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField newUsernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField newPasswordField = new JPasswordField();
        JButton createButton = new JButton("Create");

        // Add components to create account panel
        createAccountPanel.add(usernameLabel);
        createAccountPanel.add(newUsernameField);
        createAccountPanel.add(passwordLabel);
        createAccountPanel.add(newPasswordField);
        createAccountPanel.add(createButton);

        // Add action listener for create button
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newUsername = newUsernameField.getText();
                String newPassword = new String(newPasswordField.getPassword());

                // Check if username and password are not empty
                if (newUsername.isEmpty() || newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(createAccountFrame, "Username and password are required", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Insert new user into the database
                db.insertUser(newUsername, newPassword, 0, 0);
                JOptionPane.showMessageDialog(createAccountFrame, "Account created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear fields
                newUsernameField.setText("");
                newPasswordField.setText("");

                // Close create account dialog
                createAccountFrame.dispose();
            }
        });

        // Add create account panel to frame
        createAccountFrame.add(createAccountPanel);

        // Set frame properties
        createAccountFrame.setSize(300, 150);
        createAccountFrame.setLocationRelativeTo(this);
        createAccountFrame.setVisible(true);
    }

    public static void main(String[] args) {
        UserDatabase db = new UserDatabase("user_db.db");
        new UserInterface(db);
    }
}
 