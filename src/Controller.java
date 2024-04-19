import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Controller {
    private GameWindow gameWindow;
    private LoginWindow loginWindow;
    private ClientNetwork clientNetwork;
    private User user;

    public Controller() {
        clientNetwork = new ClientNetwork();
        createLoginWindow();
    }

    private void createLoginWindow() {
        loginWindow = new LoginWindow();
        loginWindow.addLoginActionListener(new LoginActionListener());
        loginWindow.addCreateAccountActionListener(new CreateAccountActionListener());
    }

    private void createGameWindow() {
        gameWindow = new GameWindow();
        gameWindow.setUsernameText(user.getUsername());
        gameWindow.setScoreText(user.getScore());
        gameWindow.addCoinFlipActionListener(new CoinFlipActionListener());
    }

    public class CoinFlipActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int wager = gameWindow.getWagerAmount();

            if(wager > user.getScore()) {
                JOptionPane.showMessageDialog(gameWindow,
                        "Wager amount cannot be greater than score.");
                return;
            }

            clientNetwork.sendRequest("FLIP");
            String response = clientNetwork.getResponse();
            gameWindow.setResultText(response);

            if(response.equals(gameWindow.getCall().getActionCommand())) {
                String request = String.format("ADD %s %d", user.getUsername(), wager);
                clientNetwork.sendRequest(request);
            } else {
                String request = String.format("LOSE %s %d", user.getUsername(), wager);
                clientNetwork.sendRequest(request);
            }

            response = clientNetwork.getResponse();
            String[] scoreResponse = response.split(" ");
            if(scoreResponse[0].equals("SCORE")) {
                user.setScore(Integer.parseInt(scoreResponse[1]));
                gameWindow.setScoreText(user.getScore());
            }
        }
    }

    public class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (loginWindow.getUsername() == null || loginWindow.getPassword() == null) {
                JOptionPane.showMessageDialog(loginWindow,
                        "Username and password must both be provided.");
            }

            String request = String.format("LOGIN %s %s", loginWindow.getUsername(),
                    Arrays.toString(loginWindow.getPassword()));
            clientNetwork.sendRequest(request);

            String response = clientNetwork.getResponse();
            if(response.equals("FALSE")) {
                JOptionPane.showMessageDialog(loginWindow,
                        "Login failed. Please try again or create a new account.");
            } else {
                String[] userInfo = response.split(" ");
                String username = userInfo[1];
                String password = userInfo[2];
                int score = Integer.parseInt(userInfo[3]);
                user = new User(username, password, score);
                loginWindow.dispose();
                createGameWindow();
            }
        }
    }

    public class CreateAccountActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (loginWindow.getUsername() == null || loginWindow.getPassword() == null) {
                JOptionPane.showMessageDialog(loginWindow,
                        "Username and password must both be provided.");
            }

            String request = String.format("CREATE %s %s", loginWindow.getUsername(),
                    Arrays.toString(loginWindow.getPassword()));
            clientNetwork.sendRequest(request);

            String response = clientNetwork.getResponse();
            if(response.equals("FALSE")) {
                JOptionPane.showMessageDialog(loginWindow,
                        "Account creation failed. Maybe username is already taken?");
            } else {
                JOptionPane.showMessageDialog(loginWindow,
                        "Account creation successful. Please log in.");
            }
        }
    }
}
