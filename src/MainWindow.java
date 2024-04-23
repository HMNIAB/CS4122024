import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private JTabbedPane jTabs;
    private CoinFlipPanel coinFlipPanel;
    private DiceRollPanel diceRollPanel;
    private JLabel scoreText;
    private JLabel usernameText;

    public MainWindow() {
        super("Coin Flip Game");
        JPanel infoPanel = constructInfoPanel();
        coinFlipPanel = new CoinFlipPanel();
        diceRollPanel = new DiceRollPanel();

        jTabs = new JTabbedPane();
        jTabs.add("COIN", coinFlipPanel);
        jTabs.add("DICE", diceRollPanel);

        getContentPane().add(BorderLayout.NORTH, infoPanel);
        getContentPane().add(BorderLayout.CENTER, jTabs);

        setSize(600, 500);
        setVisible(true);
    }

    // TODO: when logout button exists, toggle setEnabled along with jTabs. maybe disable window closing if possible?

    public void disableInput() {
        jTabs.setEnabled(false);
    }

    public void enableInput() {
        jTabs.setEnabled(true);
    }

    public GamePanel getCurrentPanel() {
        return (GamePanel) jTabs.getSelectedComponent();
    }

    public void setUsernameText(String text) {
        usernameText.setText(text);
    }

    public void setScoreText(int score) {
        scoreText.setText(STR."\{String.valueOf(score)} points");
    }

    public void addCoinFlipActionListener(ActionListener a) {
        coinFlipPanel.addCoinFlipActionListener(a);
    }

    public void addDiceRollActionListener(ActionListener a) {
        diceRollPanel.addDiceRollActionListener(a);
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
}
