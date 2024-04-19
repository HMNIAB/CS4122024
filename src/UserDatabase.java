import java.sql.*;

public class UserDatabase {
    private Connection connection;

    // Constructor
    public UserDatabase() {
        try {
            // Connect to the SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:accountinfo.db");

            // Create a table (if not exists)
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to create a table
    private void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                                "username TEXT PRIMARY KEY," +
                                "password TEXT NOT NULL," +
                                "score INTEGER DEFAULT 0," +
                                "credit_balance INTEGER DEFAULT 0)";
        try {
            Statement statement = connection.createStatement();
            statement.execute(createTableSQL);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to insert a user
    public void insertUser(String username, String password, int score, int creditBalance) {
        String insertSQL = "INSERT INTO users (username, password, score, credit_balance) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, score);
            preparedStatement.setInt(4, creditBalance);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean userExists(String username) {
        boolean exists = false;

        String selectSQL = "SELECT * FROM users WHERE username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                exists = true;
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    // Method to retrieve a user by username
    public User getUser(String username) {
        User user = null;
        String selectSQL = "SELECT * FROM users WHERE username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String password = resultSet.getString("password");
                int score = resultSet.getInt("score");
                int creditBalance = resultSet.getInt("credit_balance");
                user = new User(username, password, score, creditBalance);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Method to update a user's score
    public void updateScore(String username, int newScore) {
        String updateSQL = "UPDATE users SET score = ? WHERE username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, newScore);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update a user's credit balance
    public void updateCreditBalance(String username, int newCreditBalance) {
        String updateSQL = "UPDATE users SET credit_balance = ? WHERE username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, newCreditBalance);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to close the database connection
    public void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
