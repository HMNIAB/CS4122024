import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Thread.sleep;

public class Controller {
    private MainWindow mainWindow;
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
        mainWindow = new MainWindow();
        mainWindow.setUsernameText(user.getUsername());
        mainWindow.setScoreText(user.getScore());
        mainWindow.addCoinFlipActionListener(new GameButtonActionListener("FLIP"));
        mainWindow.addDiceRollActionListener(new GameButtonActionListener("ROLL"));
    }

    public class GameButtonActionListener implements ActionListener {
        private String command;
        private String response;
        private int wager;
        private GamePanel gamePanel;

        public GameButtonActionListener(String command) {
            super();
            this.command = command;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            gamePanel = mainWindow.getCurrentPanel();
            wager = gamePanel.getWagerAmount();

            if(wager > user.getScore()) {
                JOptionPane.showMessageDialog(mainWindow,
                        "Wager amount cannot be greater than score.");
                return;
            }

            gameRequest();
        }

        private void gameRequest() {
            clientNetwork.sendRequest(command);
            response = clientNetwork.getResponse();

            mainWindow.disableInput();
            gamePanel.disableInput();

            AnimationPlayer animationPlayer = new AnimationPlayer(gamePanel, this);
            switch(response) {
                case "HEADS":
                    animationPlayer.play(CoinImage.HEADS);
                    break;
                case "TAILS":
                    animationPlayer.play(CoinImage.TAILS);
                    break;
            }
        }

        public void resumeAfterAnimation() {
            gamePanel.setResultText(response);

            requestScoreUpdate(response);
            response = clientNetwork.getResponse();

            updateLocalScore(response);

            mainWindow.enableInput();
            gamePanel.enableInput();
        }

        private void requestScoreUpdate(String s) {
            if(s.equals(gamePanel.getCall().getActionCommand())) {
                String request = String.format("ADD %s %d", user.getUsername(), wager);
                clientNetwork.sendRequest(request);
            } else {
                String request = String.format("LOSE %s %d", user.getUsername(), wager);
                clientNetwork.sendRequest(request);
            }
        }

        private void updateLocalScore(String s) {
            String[] scoreResponse = s.split(" ");
            if(scoreResponse[0].equals("SCORE")) {
                user.setScore(Integer.parseInt(scoreResponse[1]));
                mainWindow.setScoreText(user.getScore());
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
                    new String(loginWindow.getPassword()));
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
                    new String(loginWindow.getPassword()));
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
