import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameWindow {
    private User user;

    private JFrame jFrame;
    private JButton coinFlipButton;
    private JLabel resultText;
    private JLabel scoreText;
    private JLabel usernameText;

    public GameWindow(User user) {
        this.user = user;

        jFrame = new JFrame();

        JPanel gamePanel = constructGamePanel();
        JPanel infoPanel = constructInfoPanel();

        jFrame.getContentPane().add(BorderLayout.NORTH, infoPanel);
        jFrame.getContentPane().add(BorderLayout.CENTER, gamePanel);

        jFrame.setSize(600, 500);
        jFrame.setVisible(true);
    }

    private JPanel constructGamePanel() {
        resultText = new JLabel("Flip to play!");
        coinFlipButton = new JButton("FLIP");

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.add(BorderLayout.CENTER, resultText);
        jPanel.add(BorderLayout.SOUTH, coinFlipButton);
        jPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));

        jPanel.setSize(300,300);
        return jPanel;
    }

    private JPanel constructInfoPanel() {
        usernameText = new JLabel(user.getUsername());
        scoreText = new JLabel(String.valueOf(user.getScore()));

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1,2));
        jPanel.add(usernameText);
        jPanel.add(scoreText);

        jPanel.setSize(600, 50);
        return jPanel;
    }

    public void setResultText(String text) {
        resultText.setText(text);
    }

    public void setUsernameText(String text) {
        usernameText.setText(text);
    }

    public void setScoreText(int score) {
        scoreText.setText(STR."{String.valueOf(score)} points");
    }

    public void addCoinFlipActionListener(ActionListener a) {
        coinFlipButton.addActionListener(a);
    }
}
