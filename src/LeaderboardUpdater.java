import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardUpdater {
    private UserDatabase userDatabase;

    // Constructor
    public LeaderboardUpdater() {
        userDatabase = new UserDatabase();
    }

    // Method to update the leaderboard
    public List<User> getLeaderboard(int topN) {
        List<User> leaderboard = new ArrayList<>();
        String selectSQL = "SELECT * FROM users ORDER BY score DESC LIMIT ?";
        try {
            // Prepare statement to select top N users based on score
            PreparedStatement preparedStatement = userDatabase.getConnection().prepareStatement(selectSQL);
            preparedStatement.setInt(1, topN);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // Retrieve users and add them to the leaderboard
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                int score = resultSet.getInt("score");
                User user = new User(username, password, score);
                leaderboard.add(user);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }

    // Method to close the database connection
    public void close() {
        userDatabase.close();
    }
}
