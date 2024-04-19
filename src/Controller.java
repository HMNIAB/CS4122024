import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Controller {
    private GameWindow gameWindow;
    private LoginWindow loginWindow;
    private ClientNetwork clientNetwork;

    public Controller() {
        clientNetwork = new ClientNetwork();
        createLoginWindow();
    }

    private void createLoginWindow() {
        loginWindow = new LoginWindow();
        loginWindow.addLoginActionListener(new LoginActionListener());
        loginWindow.addCreateAccountActionListener(new CreateAccountActionListener());
    }

    private void createGameWindow(User user) {
        gameWindow = new GameWindow(user);
        gameWindow.addCoinFlipActionListener(new CoinFlipActionListener());
    }

    public class CoinFlipActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clientNetwork.sendRequest("FLIP");
            gameWindow.setResultText(clientNetwork.getResponse());
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
                User user = new User(username, password, score);
                loginWindow.dispose();
                createGameWindow(user);
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
