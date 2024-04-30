import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.lang.System.exit;

public class Controller {
    private MainWindow mainWindow;
    private LoginWindow loginWindow;
    private ClientNetwork clientNetwork;
    private User user;

    public Controller() {
        clientNetwork = new ClientNetwork(this);
        createLoginWindow();
    }

    private void createLoginWindow() {
        loginWindow = new LoginWindow();
        loginWindow.addLoginActionListener(new LoginActionListener());
        loginWindow.addCreateAccountActionListener(new CreateAccountActionListener());
        loginWindow.addWindowListener(new WindowCloseListener());
    }

    private void createGameWindow() {
        mainWindow = new MainWindow();
        mainWindow.setUsernameText(user.getUsername());
        mainWindow.setScoreText(user.getScore());
        mainWindow.addCoinFlipActionListener(new GameButtonActionListener("FLIP"));
        mainWindow.addDiceRollActionListener(new GameButtonActionListener("ROLL"));
        mainWindow.addHelpButtonActionListener(new HelpButtonListener());
        mainWindow.addLogoutButtonActionListener(new LogoutActionListener());
        mainWindow.addTabChangeListener(new TabChangeListener());
        requestLeaderboardUpdate();
        mainWindow.getCurrentPanel().updateWagerSpinner(user.getScore());
        mainWindow.addWindowListener(new WindowCloseListener());
    }

    private void requestLeaderboardUpdate() {
        clientNetwork.sendRequest("LEADERBOARD");
        String response = clientNetwork.getResponse();
        mainWindow.updateLeaderboard(response);
    }

    public void connectionClosed() {
        JOptionPane.showMessageDialog(null, "The connection to the server has been lost. " +
                "The game will now close.");
        exit(1);
    }

    private void showErrorDialog() {
        JOptionPane.showMessageDialog(null, "An unexpected error occurred. " +
                "Please try your request again.");
    }

    private void triggerAnimation(String command, AnimationPlayer animationPlayer) {
        switch(command) {
            case "HEADS":
                animationPlayer.play(CoinImage.HEADS);
                break;
            case "TAILS":
                animationPlayer.play(CoinImage.TAILS);
                break;
            case "1":
                animationPlayer.play(DiceImage.ONE);
                break;
            case "2":
                animationPlayer.play(DiceImage.TWO);
                break;
            case "3":
                animationPlayer.play(DiceImage.THREE);
                break;
            case "4":
                animationPlayer.play(DiceImage.FOUR);
                break;
            case "5":
                animationPlayer.play(DiceImage.FIVE);
                break;
            case "6":
                animationPlayer.play(DiceImage.SIX);
                break;
            default:
                showErrorDialog();
        }
    }

    public class GameButtonActionListener implements ActionListener {
        private String command;
        private String response;
        private int wager;
        private ButtonModel call;
        private GamePanel gamePanel;
        private AnimationPlayer animationPlayer;

        public GameButtonActionListener(String command) {
            super();
            this.command = command;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            gamePanel = mainWindow.getCurrentPanel();
            animationPlayer = new AnimationPlayer(gamePanel, this);
            wager = gamePanel.getWagerAmount();
            call = gamePanel.getCall();

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

            triggerAnimation(response, animationPlayer);
        }

        public void resumeAfterAnimation() {
            gamePanel.setResultText(response);

            requestScoreUpdate(response);
            response = clientNetwork.getResponse();
            updateLocalScore(response);

            requestLeaderboardUpdate();
            mainWindow.enableInput();
            gamePanel.enableInput();
        }

        private void requestScoreUpdate(String s) {
            if(s.equals(call.getActionCommand())) {
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
                gamePanel.updateWagerSpinner(user.getScore());
            } else {
                showErrorDialog();
            }
        }
    }

    public class HelpButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String helpString = String.format("HOW TO PLAY:\n" +
                    "Use the wager panel on the left to bet points from your\n" +
                    "current score (shown at top) on the outcome of the game.\n" +
                    "You cannot bet more points than you have. When you're happy\n" +
                    "with your wager, press the FLIP or ROLL button to play. Winning\n" +
                    "will earn you your wager amount, +1 bonus point for winning.");
            JOptionPane.showMessageDialog(mainWindow, helpString);
        }
    }

    public class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (loginWindow.getUsername() == null || loginWindow.getPassword() == null) {
                JOptionPane.showMessageDialog(loginWindow,
                        "Username and password must both be provided.");
                return; // Added to prevent further execution if username or password is null
            }

            String request = String.format("LOGIN %s %s", loginWindow.getUsername(),
                    new String(loginWindow.getPassword()));
            clientNetwork.sendRequest(request);

            String response = clientNetwork.getResponse();
            if(response.equals("FALSE")) {
                JOptionPane.showMessageDialog(loginWindow,
                        "Login failed. Please try again, log out elsewhere, or create a new account.");
            } else {
                String[] userInfo = response.split(" ");
                if(userInfo[0].equals("TRUE")) {
                    String username = userInfo[1];
                    int score = Integer.parseInt(userInfo[2]);
                    user = new User(username, null, score);
                    loginWindow.setVisible(false);
                    createGameWindow();
                } else {
                    showErrorDialog();
                }
            }
        }
    }

    public class LogoutActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clientNetwork.sendRequest("LOGOUT " + user.getUsername());
            String response = clientNetwork.getResponse();
            if(response.equals("LOGOUT_SUCCESS")) {
                mainWindow.dispose();
            } else {
                showErrorDialog();
            }
        }
    }

    public class CreateAccountActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (loginWindow.getUsername() == null || loginWindow.getPassword() == null) {
                JOptionPane.showMessageDialog(loginWindow,
                        "Username and password must both be provided.");
                return; // Added to prevent further execution if username or password is null
            }

            String request = String.format("CREATE %s %s", loginWindow.getUsername(),
                    new String(loginWindow.getPassword()));
            clientNetwork.sendRequest(request);

            String response = clientNetwork.getResponse();
            if(response.equals("FALSE")) {
                JOptionPane.showMessageDialog(loginWindow,
                        "Account creation failed. Maybe username is already taken?");
            } else if(response.equals("TRUE")) {
                JOptionPane.showMessageDialog(loginWindow,
                        "Account creation successful. Please log in.");
            } else {
                showErrorDialog();
            }
        }
    }

    public class TabChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            GamePanel gamePanel = mainWindow.getCurrentPanel();
            gamePanel.updateWagerSpinner(user.getScore());
        }
    }

    public class WindowCloseListener extends WindowAdapter {
        @Override
        public void windowClosed(WindowEvent e) {
            clientNetwork.closeConnection();
            exit(0);
        }
    }
}

