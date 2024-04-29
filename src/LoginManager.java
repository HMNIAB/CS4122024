import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoginManager {
    private UserDatabase database;
    private static List<String> userList;

    public LoginManager(UserDatabase database) {
        this.database = database;
        userList = Collections.synchronizedList(new ArrayList<String>());
    }

    public User login(String username, String password) {
        if (database.userExists(username) && !userList.contains(username)) {
            User user = database.getUser(username);
            if (password.equals(user.getPassword())) {
                userList.add(username);
                return user;
            }
        }
        return null;
    }

    public void logout(String username) {
        if(userList.contains(username)) userList.remove(username);
    }

    public boolean createAccount(String username, String password) {
        if (database.userExists(username)) return false;
        else {
            database.insertUser(username, password, 10);
            return true;
        }
    }
}

