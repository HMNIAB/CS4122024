public class LoginManager {
    private UserDatabase database;

    public LoginManager(UserDatabase database) {
        this.database = database;
    }

    public User login(String username, String password) {
        if (database.userExists(username)) {
            User user = database.getUser(username);
            if (password.equals(user.getPassword())) return user;
        }
        return null;
    }

    public boolean createAccount(String username, String password) {
        if (database.userExists(username)) return false;
        else {
            database.insertUser(username, password, 0, 0);
            return true;
        }
    }
}
