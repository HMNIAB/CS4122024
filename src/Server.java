import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static UserDatabase database;
    static LoginManager loginManager;

    public static void main(String[] args) {
        database = new UserDatabase();
        loginManager = new LoginManager(database);

        try {
            //need to close socket somewhere - resource leak otherwise
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Server established on port 5000");
            while(true) {
                System.out.println("Waiting for client to connect...");
                Socket clientSocket = server.accept();

                Thread thread = new Thread(new ServerThread(clientSocket));
                thread.start();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String login(String[] input) {
        String username = input[1];
        String password = input[2];
        User user = loginManager.login(username, password);
        if(user != null) {
            return ("TRUE " + user);
        } else return "FALSE";
    }

    public static String createAccount(String[] input) {
        String username = input[1];
        String password = input[2];
        if(loginManager.createAccount(username, password)) {
            return "TRUE";
        } else return "FALSE";
    }

    public static String updateScore(String username, int change) {
        User user = database.getUser(username);
        int newScore = user.getScore() + change;
        database.updateScore(username, newScore);
        String response = String.format(STR."SCORE \{newScore}");
        return response;
    }
}
