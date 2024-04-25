public class LoginManager {
    private UserDatabase database;

    public LoginManager(UserDatabase database) {
        this.database = database;
    }

    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Username and password must be provided.");
            return null;
        }

        if (database.userExists(username)) {
            User user = database.getUser(username);
            if (password.equals(user.getPassword())) return user;
            else {
                JOptionPane.showMessageDialog(null,
                        "Incorrect password. Please try again.");
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    "User does not exist. Please create an account.");
        }
        return null;
    }

    public boolean createAccount(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Username and password must be provided.");
            return false;
        }

        if (database.userExists(username)) {
            JOptionPane.showMessageDialog(null,
                    "Username already exists. Please choose a different username.");
            return false;
        } else {
            database.insertUser(username, password, 10);
            JOptionPane.showMessageDialog(null,
                    "Account created successfully. You can now log in.");
            return true;
        }
    }
}

