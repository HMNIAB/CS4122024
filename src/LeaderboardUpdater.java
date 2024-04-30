import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LeaderboardUpdater {
    private UserDatabase userDatabase;

    public LeaderboardUpdater(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public ArrayList<User> getTopThreePlayers() {
        ArrayList<User> topThreePlayers = new ArrayList<>();
        try {
            String selectSQL = "SELECT username, score FROM users ORDER BY score DESC LIMIT 3";
            PreparedStatement preparedStatement = userDatabase.getConnection().prepareStatement(selectSQL);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String fetchedUsername = resultSet.getString("username");
                int fetchedScore = resultSet.getInt("score");
                User user = new User(fetchedUsername, "", fetchedScore);
                topThreePlayers.add(user);
            }

            resultSet.close();
            preparedStatement.close();

            return topThreePlayers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topThreePlayers;
    }
}

