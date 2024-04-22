import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public abstract class GamePanel extends JPanel {
    protected JLabel resultText;
    protected ButtonGroup buttonGroup;
    private JPanel gamePanel;
    private JPanel wagerPanel;
    private WagerField wagerField;

    public GamePanel() {
        gamePanel = constructGamePanel();
        wagerPanel = constructWagerPanel();

        add(wagerPanel);
        add(gamePanel);
    }

    public ButtonModel getCall() {
        return buttonGroup.getSelection();
    }

    public int getWagerAmount() {
        if(wagerField.getText() != null) {
            return Integer.parseInt(wagerField.getText());
        } else return 0;
    }

    public void setResultText(String text) {
        resultText.setText(text);
    }

    protected abstract JPanel constructGamePanel();

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

    protected abstract JPanel constructButtonsPanel();
}
