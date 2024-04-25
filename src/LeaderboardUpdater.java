import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardUpdater {
    private UserDatabase userDatabase;

    public LeaderboardUpdater() {
        userDatabase = new UserDatabase();
    }

    public ArrayList<User> getLeaderboard(int topN, String username) {
        ArrayList<User> leaderboard = new ArrayList<>();
        try {
            int userScore = userDatabase.getUser(username).getScore();
            int rank = 0;
            String selectSQL = "SELECT * FROM users ORDER BY score DESC LIMIT ?";
            PreparedStatement preparedStatement = userDatabase.getConnection().prepareStatement(selectSQL);
            preparedStatement.setInt(1, topN);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String fetchedUsername = resultSet.getString("username");
                int fetchedScore = resultSet.getInt("score");
                User user = new User(fetchedUsername, "", fetchedScore);
                leaderboard.add(user);
                if (fetchedUsername.equals(username)) {
                    rank = leaderboard.size(); // Rank of the current user
                }
            }
            resultSet.close();
            preparedStatement.close();

            int aboveIndex = Math.max(rank - 2, 0);
            int belowIndex = Math.min(rank, leaderboard.size() - 1);

            ArrayList<User> nearbyUsers = new ArrayList<>();
            if (aboveIndex >= 0 && belowIndex < leaderboard.size()) {
                nearbyUsers.add(leaderboard.get(aboveIndex)); // User above
                nearbyUsers.add(leaderboard.get(belowIndex)); // User below
            }

            return nearbyUsers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }

    public void close() {
        userDatabase.close();
    }
}

