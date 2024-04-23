import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CoinFlipPanel extends GamePanel {
    private JButton coinFlipButton;

    public CoinFlipPanel() {
        super();
        setImage(new CoinImage());
    }

    public void addCoinFlipActionListener(ActionListener a) {
        coinFlipButton.addActionListener(a);
    }

    @Override
    public void disableInput() {
        coinFlipButton.setEnabled(false);
    }

    @Override
    public void enableInput() {
        coinFlipButton.setEnabled(true);
    }

    @Override
    protected JPanel constructGamePanel() {
        resultText = new JLabel("Flip to play!");
        coinFlipButton = new JButton("FLIP");
        imagePanel = constructImagePanel();

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.add(BorderLayout.NORTH, resultText);
        jPanel.add(BorderLayout.CENTER, imagePanel);
        jPanel.add(BorderLayout.SOUTH, coinFlipButton);
        jPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

        // jPanel.setSize(300,300);
        return jPanel;
    }

    @Override
    protected JPanel constructButtonsPanel() {
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
}
