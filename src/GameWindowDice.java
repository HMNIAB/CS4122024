import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {

    private JButton rollButton;
    private JLabel resultText;
    private JLabel scoreText;
    private JLabel usernameText;
    private WagerField wagerField;

    public GameWindow() {
        super("Die Roll Game");

        JPanel gamePanel = constructGamePanel();
        JPanel infoPanel = constructInfoPanel();
        JPanel wagerPanel = constructWagerPanel();

        getContentPane().add(BorderLayout.NORTH, infoPanel);
        getContentPane().add(BorderLayout.CENTER, gamePanel);
        getContentPane().add(BorderLayout.WEST, wagerPanel);

        setSize(600, 500);
        setVisible(true);
    }

    private JPanel constructGamePanel() {
        resultText = new JLabel("Roll to play!");
        rollButton = new JButton("ROLL");

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.add(BorderLayout.CENTER, resultText);
        jPanel.add(BorderLayout.SOUTH, rollButton);
        jPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));

        jPanel.setSize(300,300);
        return jPanel;
    }

    private JPanel constructInfoPanel() {
        usernameText = new JLabel();
        scoreText = new JLabel();

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1,2));
        jPanel.add(usernameText);
        jPanel.add(scoreText);

        jPanel.setSize(600, 50);
        return jPanel;
    }

    private JPanel constructWagerPanel() {
        JPanel wagerPanel = new JPanel();
        wagerPanel.setBorder(new TitledBorder("Wager"));
        wagerPanel.setLayout(new GridLayout(2, 1));

        JLabel wagerLabel = new JLabel("Amount:");
        wagerField = new WagerField();

        wagerPanel.add(wagerLabel);
        wagerPanel.add(wagerField);

        return wagerPanel;
    }

    public int getWagerAmount() {
        return Integer.parseInt(wagerField.getText());
    }

    public void setResultText(String text) {
        resultText.setText(text);
    }

    public void setUsernameText(String text) {
        usernameText.setText(text);
    }

    public void setScoreText(int score) {
        scoreText.setText(String.valueOf(score) + " points");
    }

    public void addRollActionListener(ActionListener a) {
        rollButton.addActionListener(a);
    }
}
