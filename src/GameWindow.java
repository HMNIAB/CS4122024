import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {

    private JButton coinFlipButton;
    private JLabel resultText;
    private JLabel scoreText;
    private JLabel usernameText;
    private WagerField wagerField;
    private ButtonGroup buttonGroup;

    public GameWindow() {
        super("Coin Flip Game");

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
        wagerPanel.setLayout(new GridLayout(2, 2));

        JLabel wagerLabel = new JLabel("Call:");
        JLabel amountLabel = new JLabel("Amount:");

        JPanel buttonsPanel = constructButtonsPanel();
        wagerField = new WagerField();

        wagerPanel.add(wagerLabel);
        wagerPanel.add(buttonsPanel);
        wagerPanel.add(amountLabel);
        wagerPanel.add(wagerField);

        return wagerPanel;
    }

    private JPanel constructButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS));

        buttonGroup = new ButtonGroup();
        JRadioButton headsButton = new JRadioButton("Heads");
        headsButton.setActionCommand("HEADS");
        JRadioButton tailsButton = new JRadioButton("Tails");
        tailsButton.setActionCommand("TAILS");
        headsButton.setSelected(true);

        buttonGroup.add(headsButton);
        buttonGroup.add(tailsButton);

        buttonsPanel.add(headsButton);
        buttonsPanel.add(tailsButton);
        return buttonsPanel;
    }

    public ButtonModel getCall() {
        return buttonGroup.getSelection();
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
        scoreText.setText(STR."\{String.valueOf(score)} points");
    }

    public void addCoinFlipActionListener(ActionListener a) {
        coinFlipButton.addActionListener(a);
    }
}
