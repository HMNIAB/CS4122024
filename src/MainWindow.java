import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

import static java.awt.Color.black;
import static java.awt.FlowLayout.LEFT;

public class MainWindow extends JFrame {
    private JTabbedPane jTabs;
    private CoinFlipPanel coinFlipPanel;
    private DiceRollPanel diceRollPanel;
    private JLabel scoreText;
    private JLabel usernameText;
    private JButton helpButton;

    public MainWindow() {
        super("Coin Flip Game");
        JPanel infoPanel = constructInfoPanel();
        JPanel bottomPanel = constructBottomPanel();
        coinFlipPanel = new CoinFlipPanel();
        diceRollPanel = new DiceRollPanel();

        jTabs = new JTabbedPane();
        jTabs.add("COIN", coinFlipPanel);
        jTabs.add("DICE", diceRollPanel);
        jTabs.setSize(200,200);

        getContentPane().add(BorderLayout.NORTH, infoPanel);
        getContentPane().add(BorderLayout.CENTER, jTabs);
        getContentPane().add(BorderLayout.SOUTH, bottomPanel);

        setSize(600, 500);
        getRootPane().setBorder(new EmptyBorder(10,10,10,10));
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

    public void addHelpButtonActionListener(ActionListener a) {
        helpButton.addActionListener(a);
    }

    private JPanel constructInfoPanel() {
        usernameText = new JLabel();
        scoreText = new JLabel();

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1,2));
        jPanel.add(usernameText);
        jPanel.add(scoreText);

        Border lineBorder = BorderFactory.createLineBorder(black);
        Border emptyBorder = BorderFactory.createEmptyBorder(5,5,5,5);

        jPanel.setSize(600, 50);
        jPanel.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
        return jPanel;
    }

    private JPanel constructBottomPanel() {
        JPanel jPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(LEFT);
        jPanel.setLayout(flowLayout);

        helpButton = new JButton("?");
        helpButton.setSize(10,10);
        jPanel.add(helpButton);

        return jPanel;
    }
}
