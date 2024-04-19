public class User {
    private String username;
    private String password;
    private int score;
    private int creditBalance;

    public User(String username, String password, int score, int creditBalance) {
        this.username = username;
        this.password = password;
        this.score = score;
        this.creditBalance = creditBalance;
    }

    @Override
    public String toString() {
        return (username + password + score + creditBalance);
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(int creditBalance) {
        this.creditBalance = creditBalance;
    }

    public String getPassword() {
        return password;
    }
}
